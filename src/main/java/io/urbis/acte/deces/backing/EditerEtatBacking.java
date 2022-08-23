/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.urbis.acte.deces.backing;

import io.urbis.acte.deces.domain.ActeDeces;
import io.urbis.acte.deces.dto.ActeDecesEtatDto;
import io.urbis.acte.deces.service.ActeDecesEtatService;
import io.urbis.common.util.BaseBacking;
import java.io.Serializable;
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
@Named(value = "acteDecesEditerEtatBacking")
@ViewScoped
public class EditerEtatBacking extends BaseBacking implements Serializable{

    private static final Logger LOG = Logger.getLogger(EditerEtatBacking.class.getName());
    
    
    @Inject
    ActeDecesEtatService acteDecesEtatService;
    
        
    private String acteID;
    private ActeDecesEtatDto acteDecesEtatDto ;
    
    @PostConstruct
    public void init(){
        
        
    }
    
    public void onload(){
        if(acteID == null || acteID.isBlank()){
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
       
        ActeDeces acte = ActeDeces.findById(acteID);
        acteDecesEtatDto = acteDecesEtatService.findByActeMariage(acte);
        
    }
    
    
    public void modifier(){
        acteDecesEtatService.patcher(acteDecesEtatDto);
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

    public ActeDecesEtatDto getActeDecesEtatDto() {
        return acteDecesEtatDto;
    }

    public void setActeDecesEtatDto(ActeDecesEtatDto acteDecesEtatDto) {
        this.acteDecesEtatDto = acteDecesEtatDto;
    }

    
}
