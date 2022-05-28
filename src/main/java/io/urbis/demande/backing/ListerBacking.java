/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.demande.backing;

import io.urbis.acte.naissance.dto.ActeNaissanceDto;
import io.urbis.common.util.BaseBacking;
import io.urbis.demande.domain.Operation;
import io.urbis.demande.dto.DemandeDto;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author florent
 */
@Named(value = "demandeListerBacking")
@ViewScoped
public class ListerBacking extends BaseBacking implements Serializable{
    
    @Inject
    LazyDemandeDataModel lazyDemandeDataModel;
    
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

   
    
    
}
