/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.acte.deces.backing;

import io.urbis.acte.deces.domain.Operation;
import io.urbis.acte.deces.domain.StatutActeDeces;
import io.urbis.acte.deces.dto.ActeDecesDto;
import io.urbis.acte.deces.service.ActeDecesEtatService;
import io.urbis.acte.deces.service.ActeDecesService;
import io.urbis.common.backing.ImageBacking;
import io.urbis.common.util.BaseBacking;
import io.urbis.registre.domain.StatutRegistre;

import io.urbis.registre.dto.RegistreDto;
import io.urbis.registre.service.RegistreService;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotBlank;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import net.sf.jasperreports.engine.JRException;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author florent
 */
@Named(value = "acteDecesListerBacking")
@ViewScoped
public class ListerBacking extends BaseBacking implements Serializable{
    
    private static final Logger LOG = Logger.getLogger(ListerBacking.class.getName());
    
    @Inject
    LazyActeDecesDataModel lazyActeDecesDataModel;
     
    @Inject 
    RegistreService registreService;  
   
    @Inject 
    ActeDecesService acteDecesService;
    
    @Inject
    ActeDecesEtatService acteDecesEtatService;
    
    private String registreID;
    private RegistreDto registreDto;
    
    private String acteID;
    
    private String selectedActeID;
    
    public void onload(){
        LOG.log(Level.INFO,"REGISTRE ID: {0}",registreID);
        
        if(registreID == null || registreID.isBlank()){
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        
        registreDto = registreService.findById(registreID);
        lazyActeDecesDataModel.setRegistreID(registreID);
    }
    
    private ActeDecesDto acteDto;
    
    @PostConstruct
    public void init(){
        
        
    }
    
    @Inject
    ImageBacking imageBacking;
    
    public StreamedContent download(){
        LOG.log(Level.INFO, "-- SLECTED ACTE ID: {0}", selectedActeID);
         
        StreamedContent content = null;
        Path path = null;
        try {
            String pathString = acteDecesEtatService.print(selectedActeID,imageBacking.logoURI());
            path = Paths.get(pathString);
            InputStream input = Files.newInputStream(path);
            content = DefaultStreamedContent.builder() 
                .name(path.getFileName().toString())
                .contentType("application/pdf")
                .stream(() -> input).build();
                
        } catch (FileNotFoundException | SQLException | JRException ex) {
            Logger.getLogger(ListerBacking.class.getName()).log(Level.SEVERE, null, ex);
            
        } catch (IOException ex) {
            Logger.getLogger(ListerBacking.class.getName()).log(Level.SEVERE, null, ex);
            
        }finally{
           if(path != null){
                try {
                    Files.deleteIfExists(path);
                } catch (IOException ex) {
                    Logger.getLogger(ListerBacking.class.getName()).log(Level.SEVERE, null, ex);
                }
           }
           
        }
       
       return content;
    }
    
     public StreamedContent downloadCopie(){ 
       LOG.log(Level.INFO, "-- SLECTED ACTE ID: {0}", selectedActeID);
         
        StreamedContent content = null;
        Path path = null;
        try {
            String pathString = acteDecesEtatService.printCopie(selectedActeID,imageBacking.logoURI());
            path = Paths.get(pathString);
            InputStream input = Files.newInputStream(path);
            content = DefaultStreamedContent.builder() 
                .name(path.getFileName().toString())
                .contentType("application/pdf")
                .stream(() -> input).build();
                
        } catch (FileNotFoundException | SQLException | JRException ex) {
            Logger.getLogger(ListerBacking.class.getName()).log(Level.SEVERE, null, ex);
            
        } catch (IOException ex) {
            Logger.getLogger(ListerBacking.class.getName()).log(Level.SEVERE, null, ex);
            
        }finally{
           if(path != null){
                try {
                    Files.deleteIfExists(path);
                } catch (IOException ex) {
                    Logger.getLogger(ListerBacking.class.getName()).log(Level.SEVERE, null, ex);
                }
           }
           
        }
       
       return content;
    }
    
    public void onActeValidated(SelectEvent event){
        if(event.getObject() != null){
            WebApplicationException ex = (WebApplicationException)event.getObject();
            addGlobalMessage(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
        addGlobalMessage("L'acte de naissance a été validé avec succès", FacesMessage.SEVERITY_INFO);
    }
    
    public void openModifierActeView(ActeDecesDto dto){
        var ids = List.of(registreID);
        var operations = List.of(Operation.MODIFICATION.name());
        var acteIds = List.of(dto.getId());
        Map<String, List<String>> params = Map.of("reg-id", ids,"acte-id",acteIds,"operation",operations);
        PrimeFaces.current().dialog().openDynamic("editer", getDialogOptions(98,98,false), params);
    }
    
    public String statutSeverity(String statut){
        
        if(statut.equalsIgnoreCase("PROJET")){
            return "warning";
        }
         
        if(statut.equalsIgnoreCase("VALIDE")){
            return "success";
        }
        
        if(statut.equalsIgnoreCase("ANNULE")){
            return "danger";
        }
        
        return "";
    }
    
    public void onNewActeReturn(SelectEvent event){
        LOG.log(Level.INFO, "RETURN FROM NEW ACTE...");
    }
   
    
    public boolean disableMenuValiderActe(ActeDecesDto dto){
       return !dto.getStatut().equals(StatutActeDeces.PROJET.name()); 
    }
    
    
    public void openNewActeExistant(){
        var ids = List.of(registreID);
        var operations = List.of(Operation.SAISIE_ACTE_EXISTANT.name());
        Map<String, List<String>> params = Map.of("reg-id", ids,"operation",operations);
        Map<String,Object> options = getDialogOptions(100, 100, false);
        options.put("resizable", false);
        PrimeFaces.current().dialog().openDynamic("editer", options, params);
    }
    

    public void openNewDeclaration(){
        
        var ids = List.of(registreID);
        var operations = List.of(Operation.DECLARATION_JUGEMENT.name());
        Map<String, List<String>> params = Map.of("reg-id", ids,"operation",operations);
        Map<String,Object> options = getDialogOptions(100, 100, false);
        options.put("resizable", false);
        PrimeFaces.current().dialog().openDynamic("editer", options, params);
    }
    
    public void openValiderActeView(ActeDecesDto dto){
        LOG.log(Level.INFO, "ACTE ID: {0}", dto.getId());
        var ids = List.of(registreID);
        var operations = List.of(Operation.VALIDATION.name());
        var acteIds = List.of(dto.getId());
        Map<String, List<String>> params = Map.of("reg-id", ids,"acte-id",acteIds,"operation",operations);
        PrimeFaces.current().dialog().openDynamic("editer", getDialogOptions(98,98,true), params);
    }

    public boolean disableButtonsOpenNew(){
        LOG.log(Level.INFO, "REGISTRE DTO STATUT: {0}",registreDto.getStatut());
        if(registreDto != null){
            return !registreDto.getStatut().equals(StatutRegistre.VALIDE.name());
          
        }
        return true;
    }
    
     public void supprimer(@NotBlank String id){
       boolean result = acteDecesService.supprimer(id);
       if(!result){
           addGlobalMessage("L'acte ne peut être supprimé!", FacesMessage.SEVERITY_ERROR);
       }
    }
    
    public String returnToRegistresList(){
        return "/registre/lister.xhtml?faces-redirect=true";
    }
    
    public void openUpdateTextView(ActeDecesDto dto){
        LOG.log(Level.INFO, "ACTE ID: {0}", dto.getId());
        
        var acteIds = List.of(dto.getId());
        Map<String, List<String>> params = Map.of("acte-id",acteIds);
        PrimeFaces.current().dialog().openDynamic("/acte/deces/editer-etat", getDialogOptions(98,98,false), params);
    }
    

    public String getRegistreID() {
        return registreID;
    }

    public void setRegistreID(String registreID) {
        this.registreID = registreID;
    }

    public String getSelectedActeID() {
        return selectedActeID;
    }

    public void setSelectedActeID(String selectedActeID) {
        this.selectedActeID = selectedActeID;
    }


    public RegistreDto getRegistreDto() {
        return registreDto;
    }

    public void setRegistreDto(RegistreDto registreDto) {
        this.registreDto = registreDto;
    }

    

    public String getActeID() {
        return acteID;
    }

    public void setActeID(String acteID) {
        this.acteID = acteID;
    }
    
   

    public ActeDecesDto getActeDto() {
        return acteDto;
    }

    public void setActeDto(ActeDecesDto acteDto) {
        this.acteDto = acteDto;
    }

    public LazyActeDecesDataModel getLazyActeDecesDataModel() {
        return lazyActeDecesDataModel;
    }

    public void setLazyActeDecesDataModel(LazyActeDecesDataModel lazyActeDecesDataModel) {
        this.lazyActeDecesDataModel = lazyActeDecesDataModel;
    }

    
   
}
