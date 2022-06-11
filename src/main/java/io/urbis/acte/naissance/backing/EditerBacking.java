/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.acte.naissance.backing;

import io.urbis.acte.naissance.domain.Operation;
import io.urbis.acte.naissance.dto.ActeNaissanceDto;
import io.urbis.acte.naissance.dto.LienDeclarantDto;
import io.urbis.acte.naissance.dto.ModeDeclarationDto;
import io.urbis.acte.naissance.dto.TypeNaissanceDto;
import io.urbis.acte.naissance.service.ActeNaissanceService;
import io.urbis.acte.naissance.service.LienDeclarantService;
import io.urbis.acte.naissance.service.ModeDeclarationService;
import io.urbis.acte.naissance.service.TypeNaissanceService;
import io.urbis.common.util.BaseBacking;
import io.urbis.mention.dto.MentionAdoptionDto;
import io.urbis.mention.dto.MentionDecesDto;
import io.urbis.mention.dto.MentionDissolutionMariageDto;
import io.urbis.mention.dto.MentionLegitimationDto;
import io.urbis.mention.dto.MentionMariageDto;
import io.urbis.mention.dto.MentionReconnaissanceDto;
import io.urbis.mention.dto.MentionRectificationDto;

import io.urbis.common.dto.NationaliteDto;
import io.urbis.common.dto.SexeDto;
import io.urbis.param.dto.OfficierEtatCivilDto;

import io.urbis.common.dto.TypePieceDto; 

import io.urbis.registre.dto.RegistreDto;

import io.urbis.common.service.NationaliteService;
import io.urbis.common.service.SexeService;
import io.urbis.common.service.TypePieceService;
import io.urbis.mention.service.MentionAdoptionService;
import io.urbis.mention.service.MentionDecesService;
import io.urbis.mention.service.MentionDissolutionMariageService;
import io.urbis.mention.service.MentionLegitimationService;
import io.urbis.mention.service.MentionMariageService;
import io.urbis.mention.service.MentionReconnaissanceService;
import io.urbis.mention.service.MentionRectificationService;

import io.urbis.param.service.OfficierService;

import io.urbis.registre.service.RegistreService;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.SelectEvent;

import org.primefaces.PrimeFaces;

/**
 *
 * @author florent
 */
@Named(value = "acteNaissanceEditerBacking")
@ViewScoped
public class EditerBacking extends BaseBacking implements Serializable{
    
    private static final Logger LOG = Logger.getLogger(EditerBacking.class.getName());
    
    @Inject 
    RegistreService registreService;
    
    @Inject
    ModeDeclarationService modeDeclarationService;
    
    @Inject
    TypeNaissanceService typeNaissanceService;
    
    @Inject
    SexeService sexeService;
    
    @Inject
    NationaliteService nationaliteService;
    
    @Inject
    TypePieceService  typePieceService;
    
    @Inject
    LienDeclarantService lienDeclarantService;
    
    @Inject
    ActeNaissanceService acteNaissanceService;
    
    @Inject 
    OfficierService officierService;
    
    @Inject
    MentionDecesService mentionDecesService;
    
    @Inject
    MentionAdoptionService mentionAdoptionService;
    
    @Inject
    MentionDissolutionMariageService mentionDissolutionService;
    
    @Inject
    MentionLegitimationService mentionLegitimationService;
    
    @Inject
    MentionMariageService mentionMariageService;
    
    @Inject
    MentionReconnaissanceService mentionReconnaissanceService;
    
    @Inject
    MentionRectificationService mentionRectificationService;
    
    @Inject
    LazyActeNaissanceDataModel lazyActeNaissanceDataModel;
    
    //private ViewMode viewMode;
    
    private String registreID;
    private RegistreDto registreDto;
    
    private String acteID;
    
    private String operationParam;
    private Operation operation;
    
    private ActeNaissanceDto selectedActe;
    
       
    private List<ModeDeclarationDto> modesDeclaration;
    //private String selectedModeDeclaration;
    
    private List<TypeNaissanceDto> typesNaissance;
    private String selectedTypeNaissance;
    
    private List<SexeDto> sexes;
    private String selectedSexe;
    
    private List<LienDeclarantDto> liensParenteDeclarant; 
    private String selectedDeclarantLienParente;
    
    private List<TypePieceDto> typesPiece;
    private List<NationaliteDto> nationalites;
     
    private List<OfficierEtatCivilDto> officiers;
    private String selectedOfficierId;
     
    private int extraitsExtraordinaires;
    private int nombreCopies;
    private int extraitsOrdinaires;
    
   // private int numeroActe;
    
    //private boolean naissanceMultiple;
    private int nombreNaissance;
    private int rang = 1;
    
    
    private boolean pereDecede;
    private boolean mereDecede;
    
    private ActeNaissanceDto acteNaissanceDto;
    
    private MentionAdoptionDto adoptionDto = new MentionAdoptionDto();
    private MentionAdoptionDto selectedMentionAdoption;
    
    private MentionDecesDto decesDto = new MentionDecesDto();
    private MentionDecesDto selectedMentionDeces;
    
    private MentionDissolutionMariageDto dissolutionMariageDto = new MentionDissolutionMariageDto();
    private MentionDissolutionMariageDto selectedMentionDissolutionMariage;
    
    private MentionLegitimationDto legitimationDto = new MentionLegitimationDto();  
    private MentionLegitimationDto selectedMentionLegitimation;

    
    private MentionMariageDto mariageDto = new MentionMariageDto();
    private MentionMariageDto selectedMentionMariage;
    
    private MentionReconnaissanceDto reconnaissanceDto = new MentionReconnaissanceDto();
    private MentionReconnaissanceDto selectedMentionReconnaissance;
    
    private MentionRectificationDto rectificationDto = new MentionRectificationDto();
    private MentionRectificationDto selectedMentionRectification;
    
   
   
    
    @PostConstruct
    public void init(){
        
        //viewMode = ViewMode.NEW;
        
        officiers = officierService.findAll();
        modesDeclaration = modeDeclarationService.findAll();
        typesNaissance = typeNaissanceService.findAll();
        sexes = sexeService.findAll();
        nationalites = nationaliteService.findAll();
        //liensParenteDeclarant = lienDeclarantService.findAll();
        liensParenteDeclarant = lienDeclarantService.findAll();
        typesPiece = typePieceService.findAll(); 
        
       //acteNaissanceDto = new ActeNaissanceDto();
        
    }
    
     public void onload(){
        LOG.log(Level.INFO,"LOAD REGISTRE ID: {0}",registreID);
        registreDto = registreService.findById(registreID);
        LOG.log(Level.INFO,"REGISTRE LIBELLE: {0}",registreDto.getLibelle());
        
        operation = Operation.fromString(operationParam);
        LOG.log(Level.INFO,"--- CURRENT OPERATION : {0}",operation.name());
        
        initActeDto();
        
       
       
    }
    
    public void onRowSelect(SelectEvent<ActeNaissanceDto> event){
        /*
        LOG.log(Level.INFO,"ENFANT NOM: {0}",selectedActe.getEnfantNom());
        LOG.log(Level.INFO,"SELECTED ACTE OFFICIER ID: {0}",selectedActe.getOfficierEtatCivilID());
        acteNaissanceDto = selectedActe;
        LOG.log(Level.INFO,"SELECTED ACTE NUM: {0}",selectedActe.getNumero());
       // mariageDtos = mentionMariageService.findByActeNaissance(selectedActe.getId());
        //change view mode to render maj commande button
        //viewMode = ViewMode.UPDATE;
        */
    }
    
    /*
    public void openNewActe(){
        Map<String,Object> options = getDialogOptions(100, 100, true);
        options.put("resizable", false);
        PrimeFaces.current().dialog().openDynamic("infos-acte", options, null);
    }
    */
    
    public void onMentionMariageRowSelect(SelectEvent<MentionMariageDto> event){
        mariageDto = selectedMentionMariage;
    }
    
    public void onMentionDecesRowSelect(SelectEvent<MentionMariageDto> event){
        decesDto = selectedMentionDeces;
    }
    
    public void onMentionDissolutionRowSelect(SelectEvent<MentionMariageDto> event){
        mariageDto = selectedMentionMariage;
    }
    
    public void onMentionLegitimationRowSelect(SelectEvent<MentionMariageDto> event){
        mariageDto = selectedMentionMariage;
    }
    
    public void onMentionAdoptionRowSelect(SelectEvent<MentionMariageDto> event){
        mariageDto = selectedMentionMariage;
    }
    
    public void onMentionReconnaissanceRowSelect(SelectEvent<MentionMariageDto> event){
        mariageDto = selectedMentionMariage;
    }
    
    public void onMentionRectificationRowSelect(SelectEvent<MentionMariageDto> event){
        mariageDto = selectedMentionMariage;
    }
    
    public void deleteMentionMariage(MentionMariageDto dto){
        LOG.log(Level.INFO,"Deleting mention mariage...");
        acteNaissanceDto.getMentionMariageDtos().remove(dto);
      
    }
    
    public void deleteMentionAdoption(MentionAdoptionDto dto){
        LOG.log(Level.INFO,"Deleting mention mariage...");
        acteNaissanceDto.getMentionAdoptionDtos().remove(dto);
      
    }
    
    public void deleteMentionDeces(MentionDecesDto dto){
        LOG.log(Level.INFO,"Deleting mention mariage...");
        acteNaissanceDto.getMentionDecesDtos().remove(dto);
       
    }
    
    public void deleteMentionDissolutionMariage(MentionDissolutionMariageDto dto){
        LOG.log(Level.INFO,"Deleting mention mariage...");
        acteNaissanceDto.getMentionDissolutionMariageDtos().remove(dto);
        
    }
    
    public void deleteMentionLegitimation(MentionLegitimationDto dto){
        LOG.log(Level.INFO,"Deleting mention mariage...");
        acteNaissanceDto.getMentionLegitimationDtos().remove(dto);
        
    }
    
    public void deleteMentionReconnaisance(MentionReconnaissanceDto dto){
        LOG.log(Level.INFO,"Deleting mention mariage...");
        acteNaissanceDto.getMentionReconnaissanceDtos().remove(dto);
        
    }
        
    public void deleteMentionRectification(MentionRectificationDto dto){
        LOG.log(Level.INFO,"Deleting mention mariage...");
        acteNaissanceDto.getMentionRectificationDtos().remove(dto);
        
    }
    
        
    public void creer(){
        LOG.log(Level.INFO,"Creating acte naissance...");
         
       // LOG.log(Level.INFO,"ENFANT DATE NAISSANCE: {0}",acteNaissanceDto.getEnfantDateNaissance());
      //  acteNaissanceDto.setOperation(operation.name());
      //  LOG.log(Level.INFO,"--- CREATE REGISTRE ID: {0}",acteNaissanceDto.getRegistreID());
        //acteNaissanceDto.setRegistreID(registreID);
        
        try{
            String id = acteNaissanceService.creer(acteNaissanceDto);
            LOG.log(Level.INFO,"--- ACTE NAISSANCE ID: {0}",id);
            //creerMentions(id);
            initActeDto();
            addGlobalMessage("Déclaration enregistrée avec succès", FacesMessage.SEVERITY_INFO);
        }catch(ValidationException | SQLException ex){
            Logger.getLogger(EditerBacking.class.getName()).log(Level.SEVERE, null, ex);
            addGlobalMessage(ex.getLocalizedMessage(), FacesMessage.SEVERITY_ERROR);
        } 
        
        
       // PrimeFaces.current().dialog().closeDynamic(null);
        
        //numeroActe = acteNaissanceService.numeroActe(registreID);
        
    }
    
   
    public void modifier(){
        
        LOG.log(Level.INFO,"Updating acte naissance...");
         
        LOG.log(Level.INFO,"ENFANT DATE NAISSANCE: {0}",acteNaissanceDto.getEnfantDateNaissance());
        acteNaissanceDto.setOperation(Operation.MODIFICATION.name());
          
        try{
            //acteNaissanceService.update(acteNaissanceDto.getId(),acteNaissanceDto);
            acteNaissanceService.modifier(acteNaissanceDto.getId(), acteNaissanceDto);
            //creerMentions(acteNaissanceDto.getId());
            initActeDto();
            addGlobalMessage("L'acte a été modifié avec succès", FacesMessage.SEVERITY_INFO);
        }catch(ValidationException | SQLException ex){
            Logger.getLogger(EditerBacking.class.getName()).log(Level.SEVERE, null, ex);
            addGlobalMessage(ex.getLocalizedMessage(), FacesMessage.SEVERITY_ERROR);
        } 
        //viewMode = ViewMode.NEW;
        
        //numeroActe = acteNaissanceService.numeroActe(registreID);
        
    }
    
    public void valider(){
   
        acteNaissanceService.valider(acteNaissanceDto.getId(), acteNaissanceDto);
        initActeDto();
        addGlobalMessage("L'acte a été validé avec succès", FacesMessage.SEVERITY_INFO);
        PrimeFaces.current().dialog().closeDynamic(null);
        
    }
    
    private void initActeDto(){
        
        switch(operation){
            case DECLARATION_JUGEMENT:
                acteNaissanceDto = new ActeNaissanceDto();
                acteNaissanceDto.setRegistreID(registreID);
                int numeroActe = acteNaissanceService.numeroActe(registreID);
                acteNaissanceDto.setNumero(numeroActe);
                
                break;
            case SAISIE_ACTE_EXISTANT:
                acteNaissanceDto = new ActeNaissanceDto();
                acteNaissanceDto.setRegistreID(registreID);
                break;
            case MODIFICATION:
                acteNaissanceDto = acteNaissanceService.findById(acteID);
                break;
            case VALIDATION:
                acteNaissanceDto = acteNaissanceService.findById(acteID);
                break;
            case CONULTATION:
                acteNaissanceDto = acteNaissanceService.findById(acteID);
                break;
        }
        
        acteNaissanceDto.setOperation(operation.name());
        lazyActeNaissanceDataModel.setRegistreID(registreID);
       
        //selectedActe = null;
    
    }
    
    public boolean renderCreateButton(){
        if(operation != null){
            return operation == Operation.DECLARATION_JUGEMENT || 
                    operation == Operation.SAISIE_ACTE_EXISTANT;
        }
        return false;
    }
    
    public boolean renderValidateButton(){
        if(operation != null){
            return operation == Operation.VALIDATION ;
                    
        }
        return false;
    }
    
    
    public boolean renderUpdateButton(){
        if(operation != null){
            return operation == Operation.MODIFICATION ;
                    
        }
        return false;
    }
    
    /*
    public void nouvelEngeristrement(){
        resetActeDto();
        //viewMode = ViewMode.NEW;
    }
    */
    
    
    
    private void resetMentions(){
    
    }
    
    /*
    private void resetForNaissanceMultiple(ActeNaissanceDto old){
        acteNaissanceDto = new ActeNaissanceDto();
        acteNaissanceDto.setPereDateDeces(old.getPereDateDeces());
    }
*/
    
    public void ajouterMentionAdoption(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<MentionAdoptionDto>> violations = validator.validate(adoptionDto);
        if(violations.isEmpty()){
            
           // adoptionDtos.add(adoptionDto);
            acteNaissanceDto.getMentionAdoptionDtos().add(adoptionDto);
            adoptionDto = new MentionAdoptionDto();
        }else{
            violations.stream().forEach(v -> {
                addGlobalMessage(v.getMessage(), FacesMessage.SEVERITY_ERROR);
            });
        }
    }
    
    
    public void ajouterMentionDissolution(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<MentionDissolutionMariageDto>> violations = validator.validate(dissolutionMariageDto);
        if(violations.isEmpty()){
            
           // dissolutionMariageDtos.add(dissolutionMariageDto);
            acteNaissanceDto.getMentionDissolutionMariageDtos().add(dissolutionMariageDto);
            dissolutionMariageDto = new MentionDissolutionMariageDto();
        }else{
            violations.stream().forEach(v -> {
                addGlobalMessage(v.getMessage(), FacesMessage.SEVERITY_ERROR);
            });
        }
    }
    
    
  
    public void ajouterMentionReconnaissance(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<MentionReconnaissanceDto>> violations = validator.validate(reconnaissanceDto);
        if(violations.isEmpty()){
            
           // reconnaissanceDtos.add(reconnaissanceDto);
            acteNaissanceDto.getMentionReconnaissanceDtos().add(reconnaissanceDto);
            reconnaissanceDto = new MentionReconnaissanceDto();
        }else{
            violations.stream().forEach(v -> {
                addGlobalMessage(v.getMessage(), FacesMessage.SEVERITY_ERROR);
            });
        }
    }
    
    public void ajouterMentionLegitimation(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<MentionLegitimationDto>> violations = validator.validate(legitimationDto);
        if(violations.isEmpty()){
            
           // legitimationDtos.add(legitimationDto);
            acteNaissanceDto.getMentionLegitimationDtos().add(legitimationDto);
            legitimationDto = new MentionLegitimationDto();
        }else{
            violations.stream().forEach(v -> {
                addGlobalMessage(v.getMessage(), FacesMessage.SEVERITY_ERROR);
            });
        }
    }
    
    public void ajouterMentionRectification(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<MentionRectificationDto>> violations = validator.validate(rectificationDto);
        if(violations.isEmpty()){
            
           // rectificationDtos.add(rectificationDto);
            acteNaissanceDto.getMentionRectificationDtos().add(rectificationDto);
            rectificationDto = new MentionRectificationDto();
        }else{
            violations.stream().forEach(v -> {
                addGlobalMessage(v.getMessage(), FacesMessage.SEVERITY_ERROR);
            });
        }
    }
     
    public void ajouterMentionDeces(){
       LOG.log(Level.INFO,"Ajouter mention décès...");
       ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
       Validator validator = factory.getValidator();
       Set<ConstraintViolation<MentionDecesDto>> violations = validator.validate(decesDto);
       if(violations.isEmpty()){
           LOG.log(Level.INFO,"--MENTION DECES DTO OFFICIER ID {0}",decesDto.getOfficierEtatCivilID());
           
           //var dcs = new MentionDecesDto(decesDto);
          // decesDtos.add(decesDto);
          // LOG.log(Level.INFO,"--DECES DTO SIZE {0}",decesDtos.size());
           acteNaissanceDto.getMentionDecesDtos().add(decesDto);
           decesDto = new MentionDecesDto();
       }else{
           violations.stream().forEach(v -> {
               addGlobalMessage(v.getMessage(), FacesMessage.SEVERITY_ERROR);
           });
       }
       
    }
    
    public void ajouterMentionMariage(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<MentionMariageDto>> violations = validator.validate(mariageDto);
        if(violations.isEmpty()){
            
           // mariageDtos.add(mariageDto);
           // mariageDto.setActeNaissanceID(selectedActe.getId());
            acteNaissanceDto.getMentionMariageDtos().add(mariageDto);
            mariageDto = new MentionMariageDto();
        }else{
            violations.stream().forEach(v -> {
                addGlobalMessage(v.getMessage(), FacesMessage.SEVERITY_ERROR);
                
            });
        }
    
    }
    
    /*
    public boolean disableNumeroInput(){
        if(operation != null){
            return (operation == Operation.DECLARATION_JUGEMENT);
        }
        
        return false;
    }
    */
    
    /*
    private void creerMentions(String acteID){
        
    }
    */
    /*
    private void creerMentionMariage(@NotBlank String acteID){
        mariageDtos.stream().forEach(m -> {
            m.setActeNaissanceID(acteID);
            mentionMariageService.create(m);
       });
    }
*/
   /*
    private void creerMentionDeces(@NotBlank String acteID){
       decesDtos.stream().forEach(d -> {
            d.setActeNaissanceID(acteID);
            mentionDecesService.create(d);
       });
      
    }
*/
    
   // private boolean skip;
    
    public String onFlowProcess(FlowEvent event) {
       return event.getNewStep();
       
    }
    
    
    public void closeView(){
        PrimeFaces.current().dialog().closeDynamic("");
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

   
    public List<ModeDeclarationDto> getModesDeclaration() {
        return modesDeclaration;
    }

    public int getExtraitsExtraordinaires() {
        return extraitsExtraordinaires;
    }

    public void setExtraitsExtraordinaires(int extraitsExtraordinaires) {
        this.extraitsExtraordinaires = extraitsExtraordinaires;
    }

    public int getNombreCopies() {
        return nombreCopies;
    }

    public void setNombreCopies(int nombreCopies) {
        this.nombreCopies = nombreCopies;
    }

    public int getExtraitsOrdinaires() {
        return extraitsOrdinaires;
    }

    public void setExtraitsOrdinaires(int extraitsOrdinaires) {
        this.extraitsOrdinaires = extraitsOrdinaires;
    }

   
    public List<TypeNaissanceDto> getTypesNaissance() {
        return typesNaissance;
    }

    public ActeNaissanceDto getActeNaissanceDto() {
        return acteNaissanceDto;
    }

    public void setActeNaissanceDto(ActeNaissanceDto acteNaissanceDto) {
        this.acteNaissanceDto = acteNaissanceDto;
    }

    
    public List<SexeDto> getSexes() {
        return sexes;
    }

    public int getRang() {
        return rang;
    }

    public void setRang(int rang) {
        this.rang = rang;
    }

    public boolean isPereDecede() {
        return pereDecede;
    }

    public void setPereDecede(boolean pereDecede) {
        this.pereDecede = pereDecede;
    }


    public List<NationaliteDto> getNationalites() {
        return nationalites;
    }

    public boolean isMereDecede() {
        return mereDecede;
    }

    public void setMereDecede(boolean mereDecede) {
        this.mereDecede = mereDecede;
    }

    public List<LienDeclarantDto> getLiensParenteDeclarant() {
        return liensParenteDeclarant;
    }

  

    
    public List<TypePieceDto> getTypesPiece() {
        return typesPiece;
    }

    public String getSelectedOfficierId() {
        return selectedOfficierId;
    }

    public void setSelectedOfficierId(String selectedOfficierId) {
        this.selectedOfficierId = selectedOfficierId;
    }

    public List<OfficierEtatCivilDto> getOfficiers() {
        return officiers;
    }

    
    public String getSelectedTypeNaissance() {
        return selectedTypeNaissance;
    }

    public void setSelectedTypeNaissance(String selectedTypeNaissance) {
        this.selectedTypeNaissance = selectedTypeNaissance;
    }

    public String getSelectedSexe() {
        return selectedSexe;
    }

    public void setSelectedSexe(String selectedSexe) {
        this.selectedSexe = selectedSexe;
    }

    public String getSelectedDeclarantLienParente() {
        return selectedDeclarantLienParente;
    }

    public void setSelectedDeclarantLienParente(String selectedDeclarantLienParente) {
        this.selectedDeclarantLienParente = selectedDeclarantLienParente;
    }

    public int getNombreNaissance() {
        return nombreNaissance;
    }

    public void setNombreNaissance(int nombreNaissance) {
        this.nombreNaissance = nombreNaissance;
    }

    public LazyActeNaissanceDataModel getLazyActeNaissanceDataModel() {
        return lazyActeNaissanceDataModel;
    }

    
    public ActeNaissanceDto getSelectedActe() {
        return selectedActe;
    }

    public void setSelectedActe(ActeNaissanceDto selectedActe) {
        this.selectedActe = selectedActe;
    }
   

    public MentionAdoptionDto getAdoptionDto() {
        return adoptionDto;
    }

    public void setAdoptionDto(MentionAdoptionDto adoptionDto) {
        this.adoptionDto = adoptionDto;
    }

    public MentionDecesDto getDecesDto() {
        return decesDto;
    }

    public void setDecesDto(MentionDecesDto decesDto) {
        this.decesDto = decesDto;
    }

    public MentionDissolutionMariageDto getDissolutionMariageDto() {
        return dissolutionMariageDto;
    }

    public void setDissolutionMariageDto(MentionDissolutionMariageDto dissolutionMariageDto) {
        this.dissolutionMariageDto = dissolutionMariageDto;
    }

    public MentionLegitimationDto getLegitimationDto() {
        return legitimationDto;
    }

    public void setLegitimationDto(MentionLegitimationDto legitimationDto) {
        this.legitimationDto = legitimationDto;
    }

    public MentionMariageDto getMariageDto() {
        return mariageDto;
    }

    public void setMariageDto(MentionMariageDto mariageDto) {
        this.mariageDto = mariageDto;
    }

    public MentionReconnaissanceDto getReconnaissanceDto() {
        return reconnaissanceDto;
    }

    public void setReconnaissanceDto(MentionReconnaissanceDto reconnaissanceDto) {
        this.reconnaissanceDto = reconnaissanceDto;
    }

    public MentionRectificationDto getRectificationDto() {
        return rectificationDto;
    }

    public void setRectificationDto(MentionRectificationDto rectificationDto) {
        this.rectificationDto = rectificationDto;
    }

   
    public MentionMariageDto getSelectedMentionMariage() {
        return selectedMentionMariage;
    }

    public void setSelectedMentionMariage(MentionMariageDto selectedMentionMariage) {
        this.selectedMentionMariage = selectedMentionMariage;
    }

    public String getOperationParam() {
        return operationParam;
    }

    public void setOperationParam(String operationParam) {
        this.operationParam = operationParam;
    }

    public String getActeID() {
        return acteID;
    }

    public void setActeID(String acteID) {
        this.acteID = acteID;
    }
    
    public MentionAdoptionDto getSelectedMentionAdoption() {
        return selectedMentionAdoption;
    }

    public void setSelectedMentionAdoption(MentionAdoptionDto selectedMentionAdoption) {
        this.selectedMentionAdoption = selectedMentionAdoption;
    }

    public MentionDecesDto getSelectedMentionDeces() {
        return selectedMentionDeces;
    }

    public void setSelectedMentionDeces(MentionDecesDto selectedMentionDeces) {
        this.selectedMentionDeces = selectedMentionDeces;
    }

    public MentionDissolutionMariageDto getSelectedMentionDissolutionMariage() {
        return selectedMentionDissolutionMariage;
    }

    public void setSelectedMentionDissolutionMariage(MentionDissolutionMariageDto selectedMentionDissolutionMariage) {
        this.selectedMentionDissolutionMariage = selectedMentionDissolutionMariage;
    }

    public MentionLegitimationDto getSelectedMentionLegitimation() {
        return selectedMentionLegitimation;
    }

    public void setSelectedMentionLegitimation(MentionLegitimationDto selectedMentionLegitimation) {
        this.selectedMentionLegitimation = selectedMentionLegitimation;
    }

    public MentionReconnaissanceDto getSelectedMentionReconnaissance() {
        return selectedMentionReconnaissance;
    }

    public void setSelectedMentionReconnaissance(MentionReconnaissanceDto selectedMentionReconnaissance) {
        this.selectedMentionReconnaissance = selectedMentionReconnaissance;
    }

    public MentionRectificationDto getSelectedMentionRectification() {
        return selectedMentionRectification;
    }

    public void setSelectedMentionRectification(MentionRectificationDto selectedMentionRectification) {
        this.selectedMentionRectification = selectedMentionRectification;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    

    

   
}
