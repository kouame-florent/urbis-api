/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.demande.backing;

import io.urbis.common.util.BaseBacking;
import java.io.Serializable;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import io.urbis.demande.domain.Operation;
import io.urbis.demande.dto.DemandeDto;
import io.urbis.demande.service.DemandeService;
import io.urbis.registre.domain.TypeRegistre;
import io.urbis.registre.dto.TypeRegistreDto;
import io.urbis.registre.service.TypeRegistreService;
import java.text.MessageFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
import org.primefaces.PrimeFaces;

/**
 *
 * @author florent
 */
@Named(value = "demandeEditerBacking")
@ViewScoped
public class EditerBacking extends BaseBacking implements Serializable{
    
    private static final Logger LOG = Logger.getLogger(EditerBacking.class.getName());
    
    @Inject
    DemandeService demandeService;
    
    @Inject
    TypeRegistreService typeRegistreService;
    
    private List<TypeRegistreDto> typesRegistre;
    
    private String operationParam; //view param
    private Operation operation;
    private String typeRegistre;
    
    private String demandeID;  //view param
    
    private DemandeDto demandeDto;
    
    
    public void onload(){
        LOG.log(Level.INFO,"--- CURRENT OPERATION PARAM : {0}",operationParam);
        operation = Operation.fromString(operationParam);   
        LOG.log(Level.INFO,"--- CURRENT OPERATION : {0}",operation.name());
        
        initDemandDto();
    
    }
    
    @PostConstruct
    public void init(){
        typesRegistre = typeRegistreService.findAll();
    }
    
    
    public void creer(){
        try{
            demandeService.creer(demandeDto);
            initDemandDto();
            addGlobalMessage("Demande enregistrée avec succès", FacesMessage.SEVERITY_INFO);
        }catch(EntityNotFoundException ex){
            LOG.log(Level.SEVERE,ex.getMessage());
            String msg = String.format("L'acte avec le numéro %s du %s n'existe pas", 
                    demandeDto.getNumeroActe(),demandeDto.getDateOuvertureRegistre()) ;
            addGlobalMessage(msg, FacesMessage.SEVERITY_ERROR);
        }
    }
    
    
    public void modifier(){
    
    }
    
    public boolean renderedCreerButton(){
        LOG.log(Level.INFO,"---RENDERED CURRENT OPERATION : {0}",operation);
        if(operation != null){
            LOG.log(Level.INFO,"---RENDERED CURRENT OPERATION : {0}",operation.name());
            return operation == Operation.CREATION;
        }
        
        return false;
    }
    
    public boolean renderedModifierButton(){
        if(operation != null){
            return operation == Operation.MODIFICATION ;
        }
        
        return false;
    }
     
    public void initDemandDto(){ 
        
        switch(operation){
            case CREATION:
                demandeDto = new DemandeDto();
                int numeroDemande = demandeService.numeroDemande();
                TypeRegistre type = TypeRegistre.fromString(typeRegistre);
                demandeDto.setNumero(numeroDemande);
                demandeDto.setTypeRegistre(type.name());
                break;
            
            case MODIFICATION:
                demandeDto = demandeService.findById(demandeID);
                break;
                
            case CONSULTATION:
                demandeDto = demandeService.findById(demandeID);
                break;
           
            
        }
        
       
    }
    
    public void closeView(){
        PrimeFaces.current().dialog().closeDynamic("");
    }

    public String getOperationParam() {
        return operationParam;
    }

    public void setOperationParam(String operationParam) {
        this.operationParam = operationParam;
    }

    public String getDemandeID() {
        return demandeID;
    }

    public void setDemandeID(String demandeID) {
        this.demandeID = demandeID;
    }

    public DemandeDto getDemandeDto() {
        return demandeDto;
    }

    public void setDemandeDto(DemandeDto demandeDto) {
        this.demandeDto = demandeDto;
    }

    public List<TypeRegistreDto> getTypesRegistre() {
        return typesRegistre;
    }

    public void setTypesRegistre(List<TypeRegistreDto> typesRegistre) {
        this.typesRegistre = typesRegistre;
    }

    public String getTypeRegistre() {
        return typeRegistre;
    }

    public void setTypeRegistre(String typeRegistre) {
        this.typeRegistre = typeRegistre;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }
    
    
    
    
    
    
}
