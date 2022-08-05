/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.urbis.acte.mariage.backing;

import io.urbis.acte.mariage.domain.ActeMariage;
import io.urbis.acte.mariage.dto.ActeMariageEtatDto;
import io.urbis.acte.mariage.service.ActeMariageEtatService;
import io.urbis.common.util.BaseBacking;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import org.primefaces.PrimeFaces;

/**
 *
 * @author florent
 */
@Named(value = "acteMariageEditerEtatBacking")
@ViewScoped
public class EditerEtatBacking extends BaseBacking implements Serializable{

    private static final Logger LOG = Logger.getLogger(EditerEtatBacking.class.getName());
    
    @Inject
    ActeMariageEtatService acteMariageEtatService;
    
        
    private String acteID;
    private ActeMariageEtatDto acteMariageEtatDto ;
    
    @PostConstruct
    public void init(){
        
        
    }
    
    public void onload(){
        if(acteID == null || acteID.isBlank()){
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
       
        ActeMariage acte = ActeMariage.findById(acteID);
        acteMariageEtatDto = acteMariageEtatService.findByActeMariage(acte);
        
    }
    
    
    public void modifier(){
        acteMariageEtatService.patcher(acteMariageEtatDto);
        addGlobalMessage("Les textes ont été modifiés avec succès", FacesMessage.SEVERITY_INFO);
    }
    
    public void closeView(){
        PrimeFaces.current().dialog().closeDynamic("");
    }

    public String getActeID() {
        return acteID;
    }

    public void setActeID(String acteID) {
        this.acteID = acteID;
    }

    public ActeMariageEtatDto getActeMariageEtatDto() {
        return acteMariageEtatDto;
    }

    public void setActeMariageEtatDto(ActeMariageEtatDto acteMariageEtatDto) {
        this.acteMariageEtatDto = acteMariageEtatDto;
    }
    
    
    
}
