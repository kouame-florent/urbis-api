/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.acte.divers.backing;

import io.urbis.common.util.BaseBacking;

import io.urbis.common.dto.SexeDto;

import io.urbis.param.dto.OfficierEtatCivilDto;

import io.urbis.registre.dto.RegistreDto;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ValidationException;
import org.primefaces.PrimeFaces;
import io.urbis.param.service.OfficierService;
import io.urbis.registre.service.RegistreService;
import io.urbis.acte.divers.service.ActeRecEnfantNaturelService;
import io.urbis.acte.divers.domain.Operation;
import io.urbis.acte.divers.domain.StatutActeDivers;
import io.urbis.acte.divers.dto.ActeRecEnfantNaturelDto;
import io.urbis.common.service.SexeService;

/**
 *
 * @author florent
 */
@Named(value = "acteRecEnfantNaturelEditerBacking")
@ViewScoped
public class EditerRecEnfNaturelBacking extends BaseBacking implements Serializable{
    
    private static final Logger LOG = Logger.getLogger(EditerRecEnfNaturelBacking.class.getName());
    
    @Inject
    SexeService sexeService;
    
    @Inject 
    OfficierService officierService;
    
    @Inject 
    RegistreService registreService;
    
    @Inject
    ActeRecEnfantNaturelService acteRecEnfNaturelService;
    
    @Inject
    LazyRecEnfNaturelDataModel lazyRecEnfNaturelDataModel;
    
    private String registreID;
    private RegistreDto registreDto;
    private String operationParam;
    private Operation operation;
    private String acteID;
    
    private ActeRecEnfantNaturelDto acteDto;
    
    private List<SexeDto> sexes;
    private List<OfficierEtatCivilDto> officiers;
    
    
    @PostConstruct
    public void init(){
        LOG.log(Level.INFO,"--- INIT EditerRecEnfNaturelBacking ---");
        sexes = sexeService.findAll();
        officiers = officierService.findAll();
        
    }
    
    public void onload(){
        LOG.log(Level.INFO,"--- ON LOAD REGISTRE ID: {0}",registreID);
        registreDto = registreService.findById(registreID);
        LOG.log(Level.INFO,"-- ON LOAD REGISTRE LIBELLE: {0}",registreDto.getLibelle());
        
        operation = Operation.fromString(operationParam);
        LOG.log(Level.INFO,"---ON LOAD CURRENT OPERATION : {0}",operation.name());
        initActeDto();
        
    }
    
    public void creer(){
        try{
            acteRecEnfNaturelService.creer(acteDto);
            initActeDto();
            addGlobalMessage("Déclaration enregistrée avec succès", FacesMessage.SEVERITY_INFO);
        }catch(ValidationException ex){
            LOG.log(Level.SEVERE,ex.getMessage());
            addGlobalMessage(ex.getLocalizedMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }
    
    public void modifier(){
        
        LOG.log(Level.INFO,"Updating acte naissance...");
        acteDto.setOperation(Operation.MODIFICATION.name());
          
        try{
            acteRecEnfNaturelService.modifier(acteDto.getId(),acteDto);
            acteDto = acteRecEnfNaturelService.findById(acteDto.getId());
            addGlobalMessage("L'acte a été modifié avec succès", FacesMessage.SEVERITY_INFO);
        }catch(ValidationException ex){
           LOG.log(Level.SEVERE,ex.getMessage());
           addGlobalMessage(ex.getLocalizedMessage(), FacesMessage.SEVERITY_ERROR);
        }
        
        
    }
    
    public void valider(){
        try{
            acteDto.setStatut(StatutActeDivers.VALIDE.name());
            acteRecEnfNaturelService.modifier(acteDto.getId(), acteDto);
            addGlobalMessage("Acte validé avec succès", FacesMessage.SEVERITY_INFO);
            PrimeFaces.current().dialog().closeDynamic(null);
        }catch(ValidationException ex){
            LOG.log(Level.SEVERE,ex.getMessage());
            addGlobalMessage(ex.getLocalizedMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }
    
    private void initActeDto(){
        switch(operation){
            case DECLARATION:
                acteDto = new ActeRecEnfantNaturelDto();
                //acteDto.setStatut(StatutActeDivers.PROJET.name());
                acteDto.setRegistreID(registreID); 
                int numeroActe = acteRecEnfNaturelService.numeroActe(registreID);
                acteDto.setNumero(numeroActe);
                
                break;
            case SAISIE_ACTE_EXISTANT:
                acteDto = new ActeRecEnfantNaturelDto();
               // acteDto.setStatut(StatutActeDivers.PROJET.name());
                acteDto.setRegistreID(registreID);
                break;
            case MODIFICATION:
                acteDto = acteRecEnfNaturelService.findById(acteID);
                break;
           case VALIDATION:
                acteDto = acteRecEnfNaturelService.findById(acteID);
                break;
                
        }
        
        acteDto.setOperation(operation.name());
        lazyRecEnfNaturelDataModel.setRegistreID(registreID);
    
    }
    
    public boolean renderedValiderButton(){
        if(operation != null){
            return operation == Operation.VALIDATION && 
                    acteDto.getStatut().equals(StatutActeDivers.PROJET.name());
        }
        
        return false;
    }
    
    public boolean renderedModifierButton(){
        if(operation != null){
            return operation == Operation.MODIFICATION ;
        }
        
        return false;
    }
    
    public boolean renderedCreerButton(){
        if(operation != null){
            return operation == Operation.SAISIE_ACTE_EXISTANT || operation == Operation.DECLARATION;
        }
        
        return false;
    }
    
    public void closeView(){
        PrimeFaces.current().dialog().closeDynamic(null);
    }

    public String getRegistreID() {
        return registreID;
    }

    public void setRegistreID(String registreID) {
        this.registreID = registreID;
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

    
    
    public ActeRecEnfantNaturelDto getActeDto() {
        return acteDto;
    }

    public void setActeDto(ActeRecEnfantNaturelDto acteDto) {
        this.acteDto = acteDto;
    }

    public List<SexeDto> getSexes() {
        return sexes;
    }

    public String getOperationParam() {
        return operationParam;
    }

    public void setOperationParam(String operationParam) {
        this.operationParam = operationParam;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public List<OfficierEtatCivilDto> getOfficiers() {
        return officiers;
    }

    public void setOfficiers(List<OfficierEtatCivilDto> officiers) {
        this.officiers = officiers;
    }
    
    
    
}
