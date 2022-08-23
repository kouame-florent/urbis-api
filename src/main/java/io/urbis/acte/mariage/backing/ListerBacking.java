/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.acte.mariage.backing;

import io.urbis.acte.mariage.domain.StatutActeMariage;
import io.urbis.acte.mariage.dto.ActeMariageDto;
import io.urbis.acte.mariage.service.ActeMariageService;
import io.urbis.acte.mariage.domain.Operation;
import io.urbis.acte.mariage.service.ActeMariageEtatService;
import io.urbis.common.util.BaseBacking;
import io.urbis.registre.domain.StatutRegistre;
import java.io.Serializable;
import java.util.logging.Logger;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import io.urbis.registre.dto.RegistreDto;
import io.urbis.registre.service.RegistreService;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.ws.rs.WebApplicationException;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.StreamedContent;

import java.util.List;
import java.util.Map;
import org.primefaces.PrimeFaces;


import javax.validation.constraints.NotBlank;
import javax.ws.rs.core.Response;
import net.sf.jasperreports.engine.JRException;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.primefaces.model.DefaultStreamedContent;

/**
 *
 * @author florent
 */
@Named(value = "acteMariageListerBacking")
@ViewScoped
public class ListerBacking extends BaseBacking implements Serializable{
    
    private static final Logger LOG = Logger.getLogger(ListerBacking.class.getName());
    
    @Inject
    LazyActeMariageDataModel lazyActeMariageDataModele;
     
    @Inject 
    RegistreService registreService;  
    
    
    @Inject 
    ActeMariageService acteMariageService;
    
    @Inject
    ActeMariageEtatService acteMariageEtatService;
    
    @Inject
    @ConfigProperty(name = "URBIS_TENANT", defaultValue = "standard")
    String tenant;
    
    private String registreID;
    private RegistreDto registreDto;
    
    private String selectedActeID;
    
    public void onload(){
        LOG.log(Level.INFO,"---- URBIS TENANT: {0}",tenant);
        LOG.log(Level.INFO,"---- REGISTRE ID: {0}",registreID);
        
        if(registreID == null || registreID.isBlank()){
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        
        registreDto = registreService.findById(registreID);
        lazyActeMariageDataModele.setRegistreID(registreID);
    }
    
    private ActeMariageDto selectedActe;
    
    @PostConstruct
    public void init(){
        
        
    }
    
    public StreamedContent download(){
        LOG.log(Level.INFO, "-- SLECTED ACTE ID: {0}", selectedActeID);
       
        StreamedContent content = null;
        Path path = null;
        try {
            String pathString = acteMariageEtatService.print(selectedActeID);
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
            String pathString = acteMariageEtatService.printCopie(selectedActeID);
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
    
    public void openModifierActeView(ActeMariageDto dto){
        var ids = List.of(registreID);
        var operations = List.of(Operation.MODIFICATION.name());
        var acteIds = List.of(dto.getId());
        Map<String, List<String>> params = Map.of("reg-id", ids,"acte-id",acteIds,"operation",operations);
        PrimeFaces.current().dialog().openDynamic("/acte/mariage/editer", getDialogOptions(98,98,false), params);
    }
    
    public void openUpdateTextView(ActeMariageDto dto){
        LOG.log(Level.INFO, "ACTE ID: {0}", dto.getId());
        
        var acteIds = List.of(dto.getId());
        Map<String, List<String>> params = Map.of("acte-id",acteIds);
        PrimeFaces.current().dialog().openDynamic("/acte/mariage/editer-etat", getDialogOptions(98,98,false), params);
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
   
    
    public boolean disableMenuValiderActe(ActeMariageDto dto){
       return !dto.getStatut().equals(StatutActeMariage.PROJET.name()); 
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
        var operations = List.of(Operation.DECLARATION.name()); 
        Map<String, List<String>> params = Map.of("reg-id", ids,"operation",operations);
        Map<String,Object> options = getDialogOptions(100, 100, false);
        options.put("resizable", false);
        PrimeFaces.current().dialog().openDynamic("editer", options, params);
    }
    
    public void openValiderActeView(ActeMariageDto dto){
        LOG.log(Level.INFO, "ACTE ID: {0}", dto.getId());
        var ids = List.of(registreID);
        var operations = List.of(Operation.VALIDATION.name());
        var acteIds = List.of(dto.getId());
        Map<String, List<String>> params = Map.of("reg-id", ids,"acte-id",acteIds,"operation",operations);
        PrimeFaces.current().dialog().openDynamic("/acte/mariage/editer", getDialogOptions(98,98,true), params);
    }
    
    public void openConsulterActeView(ActeMariageDto dto){
        LOG.log(Level.INFO, "ACTE ID: {0}", dto.getId());
        var ids = List.of(registreID);
        var operations = List.of(Operation.CONSULTATION.name());
        var acteIds = List.of(dto.getId());
        Map<String, List<String>> params = Map.of("reg-id", ids,"acte-id",acteIds,"operation",operations);
        PrimeFaces.current().dialog().openDynamic("/acte/mariage/editer", getDialogOptions(98,98,true), params);
    }

    public boolean disableButtonsOpenNew(){
        LOG.log(Level.INFO, "REGISTRE DTO STATUT: {0}",registreDto.getStatut());
        if(registreDto != null){
            return !registreDto.getStatut().equals(StatutRegistre.VALIDE.name());
          
        }
        return true;
    }
    
     public void supprimer(@NotBlank String id){
       boolean result = acteMariageService.supprimer(id);
       if(!result){
           addGlobalMessage("L'acte ne peut être supprimé!", FacesMessage.SEVERITY_ERROR);
       }
    }
    
    public String returnToRegistresList(){
        return "/registre/lister.xhtml?faces-redirect=true";
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

    public LazyActeMariageDataModel getLazyActeMariageDataModele() {
        return lazyActeMariageDataModele;
    }

    public void setLazyActeMariageDataModele(LazyActeMariageDataModel lazyActeMariageDataModele) {
        this.lazyActeMariageDataModele = lazyActeMariageDataModele;
    }

    public ActeMariageDto getSelectedActe() {
        return selectedActe;
    }

    public void setSelectedActe(ActeMariageDto selectedActe) {
        this.selectedActe = selectedActe;
    }
    
    
}
