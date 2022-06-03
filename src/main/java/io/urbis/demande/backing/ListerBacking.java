/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.demande.backing;

import io.urbis.acte.naissance.dto.ActeNaissanceDto;
import io.urbis.acte.naissance.service.ActeNaissanceRestClient;
import io.urbis.common.util.BaseBacking;
import io.urbis.demande.domain.Operation;
import io.urbis.demande.dto.DemandeDto;
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
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author florent
 */
@Named(value = "demandeListerBacking")
@ViewScoped
public class ListerBacking extends BaseBacking implements Serializable{
    
    private static final Logger LOG = Logger.getLogger(ListerBacking.class.getName());
    
    @Inject
    LazyDemandeDataModel lazyDemandeDataModel;
    
    @Inject 
    @RestClient
    ActeNaissanceRestClient acteNaissanceRestClient;
    
    @Inject
    @ConfigProperty(name = "URBIS_TENANT", defaultValue = "standard")
    String tenant;
    
    private String selectedActeID;
    
    private DemandeDto selectedDemande;
    
    public void onload(){
       
        
    }
    
   
    
    
    @PostConstruct
    public void init(){
        
        
    }
    
     public void openCreerView(){
        List<String> ids = List.of();
        var operations = List.of(Operation.CREATION.name());
        Map<String, List<String>> params = Map.of("operation",operations);
        Map<String,Object> options = getDialogOptions(98, 98, true);
        options.put("resizable", false);
        PrimeFaces.current().dialog().openDynamic("editer", options, params);
    }
    
    
    
    public void onDemandeCreated(SelectEvent event){
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Création de demande", "Demande créee avec succès");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
    public StreamedContent download(){
       File file = acteNaissanceRestClient.downloadActeNaissance(tenant, selectedActeID);
       LOG.log(Level.INFO, "TENANT: {0}", tenant);
       LOG.log(Level.INFO, "FILE NAME: {0}", file.getName());
       LOG.log(Level.INFO, "FILE ABSOLUTE PATH: {0}", file.getAbsolutePath());
       LOG.log(Level.INFO, "FILE LENGHT: {0}", file.length());
       
       StreamedContent content = null;
        try {
            InputStream input = new FileInputStream(file);
            content = DefaultStreamedContent.builder() 
                .name("acte_naissance.pdf")
                .contentType("application/pdf")
                .stream(() -> input).build();
                
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ListerBacking.class.getName()).log(Level.SEVERE, null, ex);
        }
       
       return content;
    }
    
     public String returnToRegistresList(){
        return "/registre/lister.xhtml?faces-redirect=true";
    }
    

    public LazyDemandeDataModel getLazyDemandeDataModel() {
        return lazyDemandeDataModel;
    }

    public void setLazyDemandeDataModel(LazyDemandeDataModel lazyDemandeDataModel) {
        this.lazyDemandeDataModel = lazyDemandeDataModel;
    }

    public DemandeDto getSelectedDemande() {
        return selectedDemande;
    }

    public void setSelectedDemande(DemandeDto selectedDemande) {
        this.selectedDemande = selectedDemande;
    }

    public String getSelectedActeID() {
        return selectedActeID;
    }

    public void setSelectedActeID(String selectedActeID) {
        this.selectedActeID = selectedActeID;
    }

   
    
    
}
