/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.acte.deces.backing;



import io.urbis.acte.deces.domain.Operation;
import io.urbis.acte.deces.domain.StatutActeDeces;
import io.urbis.acte.deces.dto.ActeDecesDto;
import io.urbis.acte.deces.dto.LienDeclarantDto;
import io.urbis.acte.deces.service.ActeDecesService;
import io.urbis.acte.deces.service.LienDeclarantService;
import io.urbis.common.dto.NationaliteDto;
import io.urbis.common.util.BaseBacking;

import io.urbis.common.dto.SexeDto;
import io.urbis.common.dto.SituationMatrimonialeDto;
import io.urbis.common.dto.TypePieceDto;
import io.urbis.common.service.NationaliteService;
import io.urbis.common.service.SexeService;
import io.urbis.common.service.SituationMatrimonialeService;
import io.urbis.common.service.TypePieceService;

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
import org.primefaces.event.FlowEvent;
import io.urbis.param.service.OfficierService;
import io.urbis.registre.service.RegistreService;
import java.sql.SQLException;

/**
 *
 * @author florent
 */
@Named(value = "acteDecesEditerBacking")
@ViewScoped
public class EditerBacking extends BaseBacking implements Serializable{
    
    private static final Logger LOG = Logger.getLogger(EditerBacking.class.getName());
     
    @Inject 
    RegistreService registreService;
    
    @Inject 
    ActeDecesService acteDecesService;
    
    @Inject 
    SituationMatrimonialeService situationMatrimonialeService;
    
    @Inject
    SexeService sexeService;
    
    @Inject
    LienDeclarantService lienDeclarantService;
    
    @Inject
    TypePieceService typePieceService;
    
    
    @Inject 
    OfficierService officierService;
    
    @Inject
    NationaliteService nationaliteService;
    
    private String registreID;
    private RegistreDto registreDto;
    
    private String acteID;
    
    private ActeDecesDto acteDto;
    
    private String operationParam;
    private Operation operation; 
    
    private List<SituationMatrimonialeDto> situations;
        
    private List<OfficierEtatCivilDto> officiers;
    private List<SexeDto> sexes;
    private List<LienDeclarantDto> liens;
    private List<TypePieceDto> typesPieces;
    private List<NationaliteDto> nationalites;
    
    
    @Inject
    LazyActeDecesDataModel lazyActeDecesDataModel;
    
    @PostConstruct
    public void init(){
        officiers = officierService.findAll();
        liens = lienDeclarantService.findAll();
        typesPieces = typePieceService.findAll();
        nationalites = nationaliteService.findAll();
    }
    
    
    public void onload(){
        LOG.log(Level.INFO,"--- ON LOAD REGISTRE ID: {0}",registreID);
        registreDto = registreService.findById(registreID);
        LOG.log(Level.INFO,"-- ON LOAD REGISTRE LIBELLE: {0}",registreDto.getLibelle());
        
        operation = Operation.fromString(operationParam);
        LOG.log(Level.INFO,"---ON LOAD CURRENT OPERATION : {0}",operation.name());
        
        situations = situationMatrimonialeService.findAll();
        sexes = sexeService.findAll();
        
        switch(operation){
            case DECLARATION_JUGEMENT:
                LOG.log(Level.INFO,"---SWITCH CURRENT OPERATION : {0}",operation.name());
                acteDto = new ActeDecesDto();
                acteDto.setRegistreID(registreID);
                int numeroActe = acteDecesService.numeroActe(registreID);
                acteDto.setNumero(numeroActe);
                break;
            case SAISIE_ACTE_EXISTANT:
                LOG.log(Level.INFO,"---SWITCH CURRENT OPERATION : {0}",operation.name());
                acteDto = new ActeDecesDto();
                acteDto.setRegistreID(registreID);
                break;
            case MODIFICATION:
                acteDto = acteDecesService.findById(acteID);
                break;
            case VALIDATION:
                acteDto = acteDecesService.findById(acteID);
                break;
        }
        
        acteDto.setOperation(operation.name());
        lazyActeDecesDataModel.setRegistreID(registreID);
        LOG.log(Level.INFO,"---END SWITCH CURRENT OPERATION : {0}",operation.name());
        
    }
    
    public void creer(){
        try{ 
            acteDecesService.creer(acteDto);
            resetActeDto();
            addGlobalMessage("Déclaration enregistrée avec succès", FacesMessage.SEVERITY_INFO);
        }catch(ValidationException | SQLException ex){
            LOG.log(Level.SEVERE,ex.getMessage());
            addGlobalMessage(ex.getLocalizedMessage(), FacesMessage.SEVERITY_ERROR);
        } 
    }
    
    public void modifier(){
        
        LOG.log(Level.INFO,"Updating acte naissance...");
        acteDto.setOperation(Operation.MODIFICATION.name());
          
        try{
           // acteDecesService.update(acteDto.getId(),acteDto);
            acteDecesService.modifier(acteDto.getId(),acteDto);
            acteDto = acteDecesService.findById(acteDto.getId());
            addGlobalMessage("L'acte a été modifié avec succès", FacesMessage.SEVERITY_INFO);
        }catch(ValidationException | SQLException ex){
           LOG.log(Level.SEVERE,ex.getMessage());
           addGlobalMessage(ex.getLocalizedMessage(), FacesMessage.SEVERITY_ERROR);
        } 
        
        
    }
    
    public void valider(){
        
        try{
            acteDto.setStatut(StatutActeDeces.VALIDE.name());
           // acteDecesService.update(acteDto.getId(), acteDto);
            acteDecesService.modifier(acteDto.getId(), acteDto);
            addGlobalMessage("Acte validé avec succès", FacesMessage.SEVERITY_INFO);
            PrimeFaces.current().dialog().closeDynamic(null);
        }catch(ValidationException | SQLException ex){
            Logger.getLogger(EditerBacking.class.getName()).log(Level.SEVERE, null, ex);
            addGlobalMessage(ex.getLocalizedMessage(), FacesMessage.SEVERITY_ERROR);
        } 

    }
    
    public boolean renderedValiderButton(){
       
        if(operation != null){
            return operation == Operation.VALIDATION && 
                    acteDto.getStatut().equals(StatutActeDeces.PROJET.name());
        }
        
        return false;
    }
    
    public boolean renderedCreerButton(){
        
        if(operation != null){
            return operation == Operation.SAISIE_ACTE_EXISTANT || operation == Operation.DECLARATION_JUGEMENT;
        }
        
        return false;
    }
    
    public boolean renderedModifierButton(){
        
        if(operation != null){
            return operation == Operation.MODIFICATION;
        }
        
        return false;
    }
    
    private void resetActeDto(){
        acteDto = new ActeDecesDto();
        if(operation == Operation.DECLARATION_JUGEMENT){
            int numeroActe = acteDecesService.numeroActe(registreID);
            acteDto.setNumero(numeroActe);
        }
       // selectedActe = null;
    
    }
    
    public String onFlowProcess(FlowEvent event) {
       LOG.log(Level.INFO, "--- EVENT NEW STEP: {0}", event.getNewStep());
       LOG.log(Level.INFO, "--- EVENT OLD STEP: {0}", event.getOldStep());
       return event.getNewStep();
       
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

    public String getActeMariageID() {
        return acteID;
    }

    public void setActeMariageID(String acteMariageID) {
        this.acteID = acteMariageID;
    }

    public String getOperationParam() {
        return operationParam;
    }

    public void setOperationParam(String operationParam) {
        this.operationParam = operationParam;
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

        
    public List<SituationMatrimonialeDto> getSituations() {
        return situations;
    }

    public String getActeID() {
        return acteID;
    }

    public void setActeID(String acteID) {
        this.acteID = acteID;
    }

    public List<OfficierEtatCivilDto> getOfficiers() {
        return officiers;
    }

    public List<SexeDto> getSexes() {
        return sexes;
    }

    public List<LienDeclarantDto> getLiens() {
        return liens;
    }

    public List<TypePieceDto> getTypesPieces() {
        return typesPieces;
    }

    public List<NationaliteDto> getNationalites() {
        return nationalites;
    }

    
    
}
