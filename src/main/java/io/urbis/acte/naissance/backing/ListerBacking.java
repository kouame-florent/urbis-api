/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.acte.naissance.backing;

import io.urbis.acte.naissance.domain.Operation;
import io.urbis.acte.naissance.domain.StatutActeNaissance;
import io.urbis.acte.naissance.dto.ActeNaissanceDto;
import io.urbis.acte.naissance.service.ActeNaissanceEtatService;
import io.urbis.acte.naissance.service.ActeNaissanceRestClient;
import io.urbis.acte.naissance.service.ActeNaissanceService;
import io.urbis.common.util.BaseBacking;

import io.urbis.registre.dto.RegistreDto;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import javax.ws.rs.WebApplicationException;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import io.urbis.registre.domain.StatutRegistre;
import io.urbis.registre.service.RegistreService;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import javax.ws.rs.core.Response;
import net.sf.jasperreports.engine.JRException;

/**
 *
 * @author florent
 */
@Named(value = "acteNaissanceListerBacking")
@ViewScoped
public class ListerBacking extends BaseBacking implements Serializable{
    
    private static final Logger LOG = Logger.getLogger(ListerBacking.class.getName());
    
    @Inject
    LazyActeNaissanceDataModel lazyActeNaissanceDataModel;
     
    @Inject 
    RegistreService registreService;
    
    @Inject
    ActeNaissanceService acteNaissanceService;
    
    @Inject
    ActeNaissanceEtatService acteNaissanceEtatService;
    
    /*
    @Inject 
    @RestClient
    ActeNaissanceRestClient acteNaissanceRestClient;
*/
    
    @Inject
    @ConfigProperty(name = "URBIS_TENANT", defaultValue = "standard")
    String tenant;
    
    private String selectedActeID;
    
    private String registreID;
    private RegistreDto registreDto;
    
    public String pageTitle(){
        return "Naissances";
    }
    
    public void onload(){
        LOG.log(Level.INFO,"----- URBIS TENANT: {0}",tenant);
        LOG.log(Level.INFO,"----- REGISTRE ID: {0}",registreID);
        
        if(registreID == null || registreID.isBlank()){
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        
        registreDto = registreService.findById(registreID);
        lazyActeNaissanceDataModel.setRegistreID(registreID);
    }
    
    private ActeNaissanceDto selectedActe;
    
    @PostConstruct
    public void init(){
        
        
    }
    
   
    public StreamedContent downloadCopie(){ 
       LOG.log(Level.INFO, "-- SLECTED ACTE ID: {0}", selectedActeID);
      // File file = acteNaissanceRestClient.downloadActeNaissance(tenant, selectedActeID);
      // LOG.log(Level.INFO, "TENANT: {0}", tenant);
      // LOG.log(Level.INFO, "FILE NAME: {0}", file.getName());
      // LOG.log(Level.INFO, "FILE ABSOLUTE PATH: {0}", file.getAbsolutePath());
      // LOG.log(Level.INFO, "FILE LENGHT: {0}", file.length());
      
       
       
        StreamedContent content = null;
        Path path = null;
        try {
            String pathString = acteNaissanceService.printCopie(selectedActeID);
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
    
    public StreamedContent download(){
       LOG.log(Level.INFO, "-- SLECTED ACTE ID: {0}", selectedActeID);
      // File file = acteNaissanceRestClient.downloadActeNaissance(tenant, selectedActeID);
      // LOG.log(Level.INFO, "TENANT: {0}", tenant);
      // LOG.log(Level.INFO, "FILE NAME: {0}", file.getName());
      // LOG.log(Level.INFO, "FILE ABSOLUTE PATH: {0}", file.getAbsolutePath());
      // LOG.log(Level.INFO, "FILE LENGHT: {0}", file.length());
      
       
       
        StreamedContent content = null;
        Path path = null;
        try {
            String pathString = acteNaissanceService.print(selectedActeID);
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
    
    public void openModifierActeView(ActeNaissanceDto dto){
        var ids = List.of(registreID);
        var operations = List.of(Operation.MODIFICATION.name());
        var acteIds = List.of(dto.getId());
        Map<String, List<String>> params = Map.of("reg-id", ids,"acte-id",acteIds,"operation",operations);
        PrimeFaces.current().dialog().openDynamic("/acte/naissance/editer", getDialogOptions(98,98,false), params);
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
    
    public void openValiderActeView(ActeNaissanceDto dto){
        LOG.log(Level.INFO, "ACTE ID: {0}", dto.getId());
        var ids = List.of(registreID);
        var operations = List.of(Operation.VALIDATION.name());
        var acteIds = List.of(dto.getId());
        Map<String, List<String>> params = Map.of("reg-id", ids,"acte-id",acteIds,"operation",operations);
        PrimeFaces.current().dialog().openDynamic("/acte/naissance/editer", getDialogOptions(98,98,false), params);
    }
    
    public void openUpdateTextView(ActeNaissanceDto dto){
        LOG.log(Level.INFO, "ACTE ID: {0}", dto.getId());
        
        var acteIds = List.of(dto.getId());
        Map<String, List<String>> params = Map.of("acte-id",acteIds);
        PrimeFaces.current().dialog().openDynamic("/acte/naissance/editer-etat", getDialogOptions(98,98,false), params);
    }
    
    public void openConsulterActeView(ActeNaissanceDto dto){
        LOG.log(Level.INFO, "ACTE ID: {0}", dto.getId());
        var ids = List.of(registreID);
        var operations = List.of(Operation.CONULTATION.name());
        var acteIds = List.of(dto.getId());
        Map<String, List<String>> params = Map.of("reg-id", ids,"acte-id",acteIds,"operation",operations);
        PrimeFaces.current().dialog().openDynamic("/acte/naissance/editer", getDialogOptions(98,98,true), params);
    }
    
    public void supprimer(@NotNull String id){
       boolean result = acteNaissanceService.supprimer(id);
       if(!result){
           addGlobalMessage("L'acte ne peut être supprimé!", FacesMessage.SEVERITY_ERROR);
       }
    }
    
    public boolean disableMenuValiderActe(ActeNaissanceDto dto){
       return !dto.getStatut().equals(StatutActeNaissance.PROJET.name());
    }
    
    public boolean disableMenuConsulterActe(ActeNaissanceDto dto){
       return dto.getStatut().equals(StatutActeNaissance.PROJET.name());
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
        Map<String,Object> options = getDialogOptions(100, 100, true);
        options.put("resizable", false);
        PrimeFaces.current().dialog().openDynamic("editer", options, params);
    }
    
    public String returnToRegistresList(){
        return "/registre/lister.xhtml?faces-redirect=true";
    }
    
    public boolean disableButtonsOpenNew(){
        
        if(registreDto != null){
            return !registreDto.getStatut().equals(StatutRegistre.VALIDE.name());
          
        }
        return true;
    }

    public LazyActeNaissanceDataModel getLazyActeNaissanceDataModel() {
        return lazyActeNaissanceDataModel;
    }

    public void setLazyActeNaissanceDataModel(LazyActeNaissanceDataModel lazyActeNaissanceDataModel) {
        this.lazyActeNaissanceDataModel = lazyActeNaissanceDataModel;
    }

   

    public ActeNaissanceDto getSelectedActe() {
        return selectedActe;
    }

    public void setSelectedActe(ActeNaissanceDto selectedActe) {
        this.selectedActe = selectedActe;
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
    
    
}
