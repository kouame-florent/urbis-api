/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.acte.mariage.backing;

import io.urbis.acte.mariage.domain.MentionOrdonRetranscription;
import io.urbis.acte.mariage.domain.Operation;
import static io.urbis.acte.mariage.domain.Operation.DECLARATION;
import static io.urbis.acte.mariage.domain.Operation.SAISIE_ACTE_EXISTANT;
import io.urbis.acte.mariage.domain.StatutActeMariage;
import io.urbis.acte.mariage.dto.ActeMariageDto;
import io.urbis.acte.mariage.dto.MentionDivorceDto;
import io.urbis.acte.mariage.dto.MentionModifRegimeBiensDto;
import io.urbis.acte.mariage.dto.MentionOrdonRetranscriptionDto;
import io.urbis.acte.mariage.dto.RegimeDto;
import io.urbis.acte.mariage.service.ActeMariageService;
import io.urbis.acte.mariage.service.RegimeService;
import io.urbis.common.util.BaseBacking;

import io.urbis.common.dto.SituationMatrimonialeDto;
import io.urbis.common.service.SituationMatrimonialeService;

import io.urbis.param.dto.OfficierEtatCivilDto;
import io.urbis.param.service.OfficierService;

import io.urbis.registre.dto.RegistreDto;
import io.urbis.registre.service.RegistreService;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FlowEvent;

/**
 *
 * @author florent
 */
@Named(value = "acteMariageEditerBacking")
@ViewScoped
public class EditerBacking extends BaseBacking implements Serializable{
    
    private static final Logger LOG = Logger.getLogger(EditerBacking.class.getName());
    
    @Inject 
    RegistreService registreService;
    
    @Inject 
    ActeMariageService acteMariageService;
    
    @Inject 
    SituationMatrimonialeService situationMatrimonialeService;
    
    @Inject 
    RegimeService regimeService;
    
    @Inject 
    OfficierService officierService;
    
    //@Inject
    //RegistreReferenceBacking registreReferenceBacking;
    
    private String registreID;
    private RegistreDto registreDto;
    
    private String acteMariageID;
    
    private ActeMariageDto acteDto;
    
    private String operationParam;
    private Operation operation;
    
    private List<SituationMatrimonialeDto> situations;
    private List<RegimeDto> regimes = List.of();
    
    private List<OfficierEtatCivilDto> officiers;
    
    private MentionDivorceDto divorceDto = new MentionDivorceDto();
    private MentionDivorceDto selectedMentionDivorce;
    
    private MentionModifRegimeBiensDto modifRegimeBiensDto = new MentionModifRegimeBiensDto();
    private MentionModifRegimeBiensDto selectedmentionModifRegimeBiens;
    
    private MentionOrdonRetranscriptionDto ordonRetranscriptionDto = new MentionOrdonRetranscriptionDto();
    private MentionOrdonRetranscriptionDto selectedMentionOrdonRetranscriptionDto;
    
    
    
    @Inject
    LazyActeMariageDataModel lazyActeMariageDataModel;
    
    @PostConstruct
    public void init(){
        officiers = officierService.findAll();
        
    }
    
    
    public void onload(){
        LOG.log(Level.INFO,"--- ON LOAD REGISTRE ID: {0}",registreID);
        registreDto = registreService.findById(registreID);
        LOG.log(Level.INFO,"-- ON LOAD REGISTRE LIBELLE: {0}",registreDto.getLibelle());
        
        operation = Operation.fromString(operationParam);
        LOG.log(Level.INFO,"---ON LOAD CURRENT OPERATION : {0}",operation.name());
        
        situations = situationMatrimonialeService.findAll();
        regimes = regimeService.findAll();
        
        initActeDto();
        
    }
    
    public void creer(){
        try{
           // acteMariageService.create(acteDto);
            acteMariageService.creer(acteDto);
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
            //acteMariageService.update(acteDto.getId(),acteDto);
            acteMariageService.modifier(acteDto.getId(),acteDto);
            acteDto = acteMariageService.findById(acteDto.getId());
            addGlobalMessage("L'acte a été modifié avec succès", FacesMessage.SEVERITY_INFO);
        }catch(ValidationException ex){
           LOG.log(Level.SEVERE,ex.getMessage());
           addGlobalMessage(ex.getLocalizedMessage(), FacesMessage.SEVERITY_ERROR);
        }
        
        
    }
    
    public void valider(){
        
        try{
            acteDto.setStatut(StatutActeMariage.VALIDE.name());
            //acteMariageService.update(acteDto.getId(), acteDto);
            acteMariageService.modifier(acteDto.getId(), acteDto);
            addGlobalMessage("Acte validé avec succès", FacesMessage.SEVERITY_INFO);
            PrimeFaces.current().dialog().closeDynamic(null);
        }catch(ValidationException ex){
            LOG.log(Level.SEVERE,ex.getMessage());
            addGlobalMessage(ex.getLocalizedMessage(), FacesMessage.SEVERITY_ERROR);
        }

    }
    
    public boolean renderedValiderButton(){
        if(operation != null){
            return operation == Operation.VALIDATION && 
                    acteDto.getStatut().equals(StatutActeMariage.PROJET.name());
        }
        
        return false;
    }
    
    public boolean renderedCreerButton(){
        LOG.log(Level.INFO,"---RENDERED CURRENT OPERATION : {0}",operation);
        if(operation != null){
            LOG.log(Level.INFO,"---RENDERED CURRENT OPERATION : {0}",operation.name());
            return operation == Operation.SAISIE_ACTE_EXISTANT || operation == Operation.DECLARATION;
        }
        
        return false;
    }
    
    public boolean renderedModifierButton(){
        if(operation != null){
            return operation == Operation.MODIFICATION;
        }
        
        return false;
    }
    
    private void initActeDto(){
        /*
        acteDto = new ActeMariageDto();
        if(operation == Operation.DECLARATION){
            int numeroActe = acteMariageService.numeroActe(registreID);
            acteDto.setNumero(numeroActe);
        }
        */
       // selectedActe = null;
       
        switch(operation){
            case DECLARATION:
                acteDto = new ActeMariageDto();
                acteDto.setRegistreID(registreID);
                int numeroActe = acteMariageService.numeroActe(registreID);
                acteDto.setNumero(numeroActe);
                
                break;
            case SAISIE_ACTE_EXISTANT:
                acteDto = new ActeMariageDto();
                acteDto.setRegistreID(registreID);
                break;
            case MODIFICATION:
                acteDto = acteMariageService.findById(acteMariageID);
                break;
            case VALIDATION:
                acteDto = acteMariageService.findById(acteMariageID);
                break;
        }
        
        acteDto.setOperation(operation.name());
        lazyActeMariageDataModel.setRegistreID(registreID);
    
    }
    
    public String onFlowProcess(FlowEvent event) {
       LOG.log(Level.INFO, "--- EVENT NEW STEP: {0}", event.getNewStep());
       LOG.log(Level.INFO, "--- EVENT OLD STEP: {0}", event.getOldStep());
       return event.getNewStep();
       
    }
    
    
     public void ajouterMentionDivorce(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<MentionDivorceDto>> violations = validator.validate(divorceDto);
        if(violations.isEmpty()){
            
           // adoptionDtos.add(adoptionDto);
            acteDto.getMentionDivorceDtos().add(divorceDto);
            divorceDto = new MentionDivorceDto();
        }else{
            violations.stream().forEach(v -> {
                addGlobalMessage(v.getMessage(), FacesMessage.SEVERITY_ERROR);
            });
        }
    }
    
    
    public void ajouterMentionModifRegimeBiens(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<MentionModifRegimeBiensDto>> violations = validator.validate(modifRegimeBiensDto);
        if(violations.isEmpty()){
            
           // dissolutionMariageDtos.add(dissolutionMariageDto);
            acteDto.getMentionModifRegimeBiensDtos().add(modifRegimeBiensDto);
            modifRegimeBiensDto = new MentionModifRegimeBiensDto();
        }else{
            violations.stream().forEach(v -> {
                addGlobalMessage(v.getMessage(), FacesMessage.SEVERITY_ERROR);
            });
        }
    }
    
    
  
    public void ajouterMentionOrdonRetranscription(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<MentionOrdonRetranscriptionDto>> violations = validator.validate(ordonRetranscriptionDto);
        if(violations.isEmpty()){
            
           // reconnaissanceDtos.add(reconnaissanceDto);
            acteDto.getMentionOrdonRetranscriptionDtos().add(ordonRetranscriptionDto);
            ordonRetranscriptionDto = new MentionOrdonRetranscriptionDto();
        }else{
            violations.stream().forEach(v -> {
                addGlobalMessage(v.getMessage(), FacesMessage.SEVERITY_ERROR);
            });
        }
    }
    
    public void deleteMentionDivorce(){
        LOG.log(Level.INFO,"Deleting mention divorce...");
        acteDto.getMentionDivorceDtos().remove(divorceDto);
      
    }
    
    public void deleteMentionModifRegimeBiens(){
        LOG.log(Level.INFO,"Deleting mention modif regime...");
        acteDto.getMentionModifRegimeBiensDtos().remove(modifRegimeBiensDto);
      
    }
    
    public void deleteMentionOrdonRetranscription(){
        LOG.log(Level.INFO,"Deleting mention ordonnance...");
        acteDto.getMentionOrdonRetranscriptionDtos().remove(ordonRetranscriptionDto);
       
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
        return acteMariageID;
    }

    public void setActeMariageID(String acteMariageID) {
        this.acteMariageID = acteMariageID;
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

    public ActeMariageDto getActeDto() {
        return acteDto;
    }

    public void setActeDto(ActeMariageDto acteDto) {
        this.acteDto = acteDto;
    }

    public LazyActeMariageDataModel getLazyActeMariageDataModel() {
        return lazyActeMariageDataModel;
    }

    public List<SituationMatrimonialeDto> getSituations() {
        return situations;
    }

    public List<RegimeDto> getRegimes() {
        return regimes;
    }

    public List<OfficierEtatCivilDto> getOfficiers() {
        return officiers;
    }

    public void setOfficiers(List<OfficierEtatCivilDto> officiers) {
        this.officiers = officiers;
    }

    public MentionDivorceDto getDivorceDto() {
        return divorceDto;
    }

    public void setDivorceDto(MentionDivorceDto divorceDto) {
        this.divorceDto = divorceDto;
    }

    public MentionDivorceDto getSelectedMentionDivorce() {
        return selectedMentionDivorce;
    }

    public void setSelectedMentionDivorce(MentionDivorceDto selectedMentionDivorce) {
        this.selectedMentionDivorce = selectedMentionDivorce;
    }

    public MentionModifRegimeBiensDto getModifRegimeBiensDto() {
        return modifRegimeBiensDto;
    }

    public void setModifRegimeBiensDto(MentionModifRegimeBiensDto modifRegimeBiensDto) {
        this.modifRegimeBiensDto = modifRegimeBiensDto;
    }

    public MentionModifRegimeBiensDto getSelectedmentionModifRegimeBiens() {
        return selectedmentionModifRegimeBiens;
    }

    public void setSelectedmentionModifRegimeBiens(MentionModifRegimeBiensDto selectedmentionModifRegimeBiens) {
        this.selectedmentionModifRegimeBiens = selectedmentionModifRegimeBiens;
    }

    public MentionOrdonRetranscriptionDto getOrdonRetranscriptionDto() {
        return ordonRetranscriptionDto;
    }

    public void setOrdonRetranscriptionDto(MentionOrdonRetranscriptionDto ordonRetranscriptionDto) {
        this.ordonRetranscriptionDto = ordonRetranscriptionDto;
    }

    

    public MentionOrdonRetranscriptionDto getSelectedMentionOrdonRetranscriptionDto() {
        return selectedMentionOrdonRetranscriptionDto;
    }

    public void setSelectedMentionOrdonRetranscriptionDto(MentionOrdonRetranscriptionDto selectedMentionOrdonRetranscriptionDto) {
        this.selectedMentionOrdonRetranscriptionDto = selectedMentionOrdonRetranscriptionDto;
    }

    
    
    
    
}
