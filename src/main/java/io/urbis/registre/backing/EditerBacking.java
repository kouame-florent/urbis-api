/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.registre.backing;

import io.urbis.common.util.BaseBacking;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import io.urbis.param.dto.OfficierEtatCivilDto;
import io.urbis.param.dto.CentreDto;
import io.urbis.param.dto.LocaliteDto;
import io.urbis.param.dto.TribunalDto;
import io.urbis.param.service.CentreService;
import io.urbis.param.service.LocaliteService;
import io.urbis.param.service.OfficierService;
import io.urbis.param.service.TribunalService;
import io.urbis.registre.domain.StatutRegistre;
import io.urbis.registre.dto.RegistreDto;
import io.urbis.registre.dto.RegistrePatchDto;
import io.urbis.registre.dto.TypeRegistreDto;
import io.urbis.registre.service.RegistreService;
import io.urbis.registre.service.TypeRegistreService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.ValidationException;
import org.primefaces.PrimeFaces;

/**
 *
 * @author florent
 */
@Named(value = "registreEditerBacking")
@ViewScoped
public class EditerBacking extends BaseBacking implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    private static final Logger LOG = Logger.getLogger(EditerBacking.class.getName());
    
    @Inject 
    TypeRegistreService typeRegistreService;
    
    @Inject
    LocaliteService localiteService;
    
    @Inject 
    RegistreService registreService;
    
    @Inject 
    CentreService centreService;
 
    @Inject 
    TribunalService tribunalService;
    
    @Inject 
    OfficierService officierService;
    
    private String operationParam;
    private String registreID;
    private Operation operation;
    
    private List<TypeRegistreDto> typesRegistre;
   // private TypeRegistreDto selectedType;
    
    private LocaliteDto currentLocalite;
    private CentreDto currentCentre;
   // private int annee;
   // private int numeroRegistre;
  //  private int numeroPremierActe;
  //  private int nombreDeFeuillets;
   // private LocalDateTime dateOuverture;
    private TribunalDto currentTribunal;
    
   // private LocalDate dateOuverture;
    
    private List<OfficierEtatCivilDto> officiers = new ArrayList<>();
   // private String selectedOfficierId;
    
    
    private RegistreDto registreDto;
    
    
    @PostConstruct
    public void init(){
       typesRegistre = typeRegistreService.findAll();
       officiers = officierService.findAll();
       //registreDto = new RegistreDto();
    }
    
    public void onload(){
        if(registreID != null && !registreID.isBlank()){
            LOG.log(Level.INFO,"--- ON LOAD REGISTRE ID: {0}",registreID);
            registreDto = registreService.findById(registreID);
            LOG.log(Level.INFO,"-- ON LOAD REGISTRE LIBELLE: {0}",registreDto.getLibelle());
        }
       
        if(operationParam != null && !operationParam.isBlank()){
            operation = Operation.fromString(operationParam);
            LOG.log(Level.INFO,"---ON LOAD CURRENT OPERATION : {0}",operation.name());
        
        }
        
        switch(operation){
            case CREATION:
                registreDto = new RegistreDto();    
                break;
            case MODIFICATION:
                registreDto = registreService.findById(registreID);
                break;
            case VALIDATION:
                registreDto = registreService.findById(registreID);
                retrieveDefaultConfigs();

                break;
        }
        
        //registreDto.setOperation(operation.name());
        
        
    }
    
    /*
    TypeRegistreDto getTypeRegistreFromCode(String code){
        return typesRegistre.stream().filter(t -> t.getCode().equals(code))
                .findFirst().orElseThrow(() -> new IllegalStateException("cannot get TypeRegistre"));
    }
    */
    /*
    OfficierEtatCivilDto getOfficierFromId(String officierID){
        return officiers.stream().filter(o -> o.getId().equals(officierID))
                .findFirst().orElseThrow(() -> new IllegalStateException("cannot get officier"));
    }
    */
    /*
    public void onload(){
        LOG.log(Level.INFO, "REGISTRE ID: {0}", registreID);
        LOG.log(Level.INFO, "VIEW MODE: {0}", modeParam);
        
        if(modeParam != null && !modeParam.isEmpty()){
            viewMode = ViewMode.valueOf(modeParam);
            registreDto = registreService.findById(registreID);
        }
        
    }
    */
    
    
    public void onTypeRgistreSelect(){
        LOG.log(Level.INFO, "SELECTED TYPE: {0}", registreDto.getTypeRegistre());
        retrieveDefaultConfigs();
        retrieveAutoValues();
    }
    
    private void retrieveDefaultConfigs(){
        try{
            currentLocalite = localiteService.findActive();
            currentCentre = centreService.findActive();
            currentTribunal = tribunalService.findActive();
            
        }catch(EntityNotFoundException e){
            LOG.log(Level.SEVERE, e.getMessage());
            addGlobalMessage(e.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
        
        
    }
    
    private void retrieveAutoValues(){
        LOG.log(Level.INFO, "-- SELECTED OPERATION: {0}", operation.name());
        if(operation == Operation.CREATION){
            int annee = registreService.anneeCourante();
            registreDto.setAnnee(annee);
            //numeroRegistre = registreService.numeroRegistre(selectedType.getCode(),annee);
            registreDto.setNumero(registreService.numeroRegistre(registreDto.getTypeRegistre(),annee));
            //numeroPremierActe = registreService.numeroPremierActeCourant(selectedType.getCode(),annee);
            registreDto.setNumeroPremierActe(registreService.numeroPremierActeCourant(registreDto.getTypeRegistre(),annee));
            registreDto.setDateOuverture(LocalDate.now());
            //dateOuverture = LocalDateTime.now();
        }
        
    }

    /*
    public void onOfficierSelect(){
        LOG.log(Level.INFO, "SELECTED OFFICIER: {0}", selectedOfficierId);
    }
    */
    
    public void valider(){
        if(registreDto != null && registreDto.getDateOuverture() == null){
            addGlobalMessage("La date d'ouverture du registre doit être renseignée avant validation",
                    FacesMessage.SEVERITY_ERROR);
            return;
        }
       // registreService.patch(registreID,new RegistrePatchDto(StatutRegistre.VALIDE.name(),
             //   registreDto.getDateOuverture(),""));
        registreService.validerRegistre(registreID, new RegistrePatchDto(StatutRegistre.VALIDE.name(),
                registreDto.getDateOuverture(),""));
        PrimeFaces.current().dialog().closeDynamic(null);
    }
    
    public boolean renderedCreerButton(){
        LOG.log(Level.INFO,"---RENDERED CURRENT OPERATION : {0}",operation);
        if(operation != null){
            LOG.log(Level.INFO,"---RENDERED CURRENT OPERATION : {0}",operation.name());
            return operation == Operation.CREATION;
        }
        
        return false;
    }
    
    public boolean renderedValiderButton(){
        if(operation != null){
            return operation == Operation.VALIDATION && 
                    registreDto.getStatut().equals(StatutRegistre.PROJET.name());
        }
        
        return false;
    }
    
    
    public List<TypeRegistreDto> getTypesRegistre() {
        return typesRegistre;
    }

    public void setTypesRegistre(List<TypeRegistreDto> typesRegistre) {
        this.typesRegistre = typesRegistre;
    }

   
    public void creer(){
        LOG.log(Level.INFO, "CREATING REGISTRE ...");
     
        if(registreDto != null){
           // registreDto.setTypeRegistre(selectedType.getCode());
           // registreDto.setLibelle(selectedType.getLibelle());
            registreDto.setLocalite(currentLocalite.getLibelle());
            registreDto.setLocaliteID(currentLocalite.getId());
            registreDto.setCentre(currentCentre.getLibelle());
            registreDto.setCentreID(currentCentre.getId());
          //  registreDto.setAnnee(annee);
          //  registreDto.setNumero(numeroRegistre);
           // registreDto.setDateOuverture(dateOuverture);
            registreDto.setTribunal(currentTribunal.getLibelle());
            registreDto.setTribunalID(currentTribunal.getId());
           // registreDto.setOfficierEtatCivilID(selectedOfficierId);
           // registreDto.setNumeroPremierActe(numeroPremierActe);
           // registreDto.setNumeroDernierActe(nombreDeFeuillets + numeroPremierActe - 1);
          //  registreDto.setNombreDeFeuillets(nombreDeFeuillets);
            registreDto.setNombreActe(0);
            registreDto.setStatut("");
            registreDto.setDateAnnulation(null);
            registreDto.setMotifAnnulation("");
            
            try{
                  registreService.creer(registreDto);
            }catch(EntityExistsException | ValidationException  e){
                  LOG.log(Level.SEVERE, e.getMessage());
                  addGlobalMessage(e.getMessage(), FacesMessage.SEVERITY_ERROR);
            }
            
            PrimeFaces.current().dialog().closeDynamic("");
            
        }else{
            IllegalStateException ex = new IllegalStateException("registre dto cannot be null");
            LOG.log(Level.SEVERE, ex.getMessage());
            
        }
    
    }

    public LocaliteDto getCurrentLocalite() {
        return currentLocalite;
    }

    public void setCurrentLocalite(LocaliteDto currentLocalite) {
        this.currentLocalite = currentLocalite;
    }

    public CentreDto getCurrentCentre() {
        return currentCentre;
    }

    public void setCurrentCentre(CentreDto currentCentre) {
        this.currentCentre = currentCentre;
    }


    public TribunalDto getCurrentTribunal() {
        return currentTribunal;
    }

    public void setCurrentTribunal(TribunalDto currentTribunal) {
        this.currentTribunal = currentTribunal;
    }

   


    public List<OfficierEtatCivilDto> getOfficiers() {
        return officiers;
    }

   
    public RegistreDto getRegistreDto() {
        return registreDto;
    }

    public void setRegistreDto(RegistreDto registreDto) {
        this.registreDto = registreDto;
    }

    public String getOperationParam() {
        return operationParam;
    }

    public void setOperationParam(String operationParam) {
        this.operationParam = operationParam;
    }

    public String getRegistreID() {
        return registreID;
    }

    public void setRegistreID(String registreID) {
        this.registreID = registreID;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    
}
