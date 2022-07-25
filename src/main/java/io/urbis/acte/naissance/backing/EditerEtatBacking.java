/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.acte.naissance.backing;

import io.urbis.acte.naissance.domain.ActeNaissance;
import io.urbis.acte.naissance.dto.ActeNaissanceEtatDto;
import io.urbis.acte.naissance.service.ActeNaissanceEtatService;
import io.urbis.common.util.BaseBacking;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.logging.Level;
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
@Named(value = "acteNaissanceEditerEtatBacking")
@ViewScoped
public class EditerEtatBacking extends BaseBacking implements Serializable{

    private static final Logger LOG = Logger.getLogger(EditerEtatBacking.class.getName());
    
    @Inject
    ActeNaissanceEtatService acteNaissanceEtatService;
    
        
    private String acteID;
    private ActeNaissanceEtatDto acteNaissanceEtatDto ;
    
    @PostConstruct
    public void init(){
        
        
    }
    
    public void onload(){
        if(acteID == null || acteID.isBlank()){
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        
        try {
            ActeNaissance acte = ActeNaissance.findById(acteID);
            acteNaissanceEtatDto = acteNaissanceEtatService.findByActeNaissance(acte);
        } catch (SQLException | IOException ex) {
            Logger.getLogger(EditerEtatBacking.class.getName()).log(Level.SEVERE, null, ex);
        }
       
       
    }
    
    
    public void modifier(){
        acteNaissanceEtatService.patcher(acteNaissanceEtatDto);
        addGlobalMessage("Les textex ont été modifiés avec succès", FacesMessage.SEVERITY_INFO);
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

    public ActeNaissanceEtatDto getActeNaissanceEtatDto() {
        return acteNaissanceEtatDto;
    }

    public void setActeNaissanceEtatDto(ActeNaissanceEtatDto acteNaissanceEtatDto) {
        this.acteNaissanceEtatDto = acteNaissanceEtatDto;
    }
    
    
    
}
