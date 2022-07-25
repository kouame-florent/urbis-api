/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.registre.backing;

import io.urbis.common.util.BaseBacking;

import io.urbis.param.dto.OfficierEtatCivilDto;
import io.urbis.param.dto.CentreDto;
import io.urbis.param.dto.LocaliteDto;
import io.urbis.param.dto.TribunalDto;
import io.urbis.param.service.CentreService;
import io.urbis.param.service.LocaliteService;
import io.urbis.param.service.OfficierService;
import io.urbis.param.service.TribunalService;
import io.urbis.registre.domain.TypeRegistre;
import io.urbis.registre.dto.RegistreDto;

import io.urbis.registre.dto.TypeRegistreDto;
import io.urbis.registre.service.RegistreService;
import io.urbis.registre.service.TypeRegistreService;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityExistsException;
import javax.transaction.Transactional;
import javax.validation.ValidationException;
import javax.ws.rs.WebApplicationException;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.primefaces.PrimeFaces;

/**
 *
 * @author florent
 */
@Named(value = "registreEditerSerieBacking")
@ViewScoped
public class EditerSerieBacking extends BaseBacking implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    private static final Logger LOG = Logger.getLogger(EditerSerieBacking.class.getName());
    
    @Inject 
    TypeRegistreService typeRegistreService;
     
    @Inject 
    RegistreService registreService;
    
    @Inject 
    CentreService centreService;
 
    @Inject 
    TribunalService tribunalService;
    
    @Inject
    LocaliteService localiteService;
    
    @Inject 
    OfficierService officierService;
    
    @Inject
    FacesContext facesContext;
    
    private List<TypeRegistreDto> typesRegistre;
    private TypeRegistreDto selectedType;
    private String typeRegistre;  //initialized from GET params
    
    private int premier;
    private int dernier;
    
    private int nombreDeFeuillets = 50;
    private int annee;
    private String libelle;
    private LocaliteDto currentLocalite;
    private CentreDto currentCentre;
    private TribunalDto currentTribunal;
    
    
    private List<OfficierEtatCivilDto> officiers = new ArrayList<>();
    private String selectedOfficierId;
    
   // private RegistreDto registreDto;
    
    @PostConstruct
    public void init(){
       // typesRegistre = typeRegistreService.findAll();
        
        officiers = officierService.findAll();
        currentLocalite = localiteService.findActive();
        currentCentre = centreService.findActive();
        currentTribunal = tribunalService.findActive();
    }
    
    public void onload(){
        LOG.log(Level.INFO, "--> PARAM TYPE REGISTRE: {0}", typeRegistre);
        TypeRegistre tr = TypeRegistre.fromString(typeRegistre);
        selectedType = new TypeRegistreDto(tr.name(), tr.getLibelle());
    
    }
    
    public String pageTitle(){
        if(typeRegistre != null){
            var type = TypeRegistre.fromString(typeRegistre);
            return type.getLibelle()+ ": Création en serie";
        }
        
        return "";
    }
   
    public void onTypeRgistreSelect(){
        LOG.log(Level.INFO, "SELECTED TYPE: {0}", selectedType);
        
        try{
            currentLocalite = localiteService.findActive();
            currentCentre = centreService.findActive();
            currentTribunal = tribunalService.findActive();
        }catch(WebApplicationException ex){
          //LOG.log(Level.SEVERE, "{0}", ex);
          addGlobalMessage("Certains Paramètres de l'application sont inexistants", FacesMessage.SEVERITY_ERROR);
          ex.printStackTrace();
        }
      
    }
    
    
    
    public boolean renderedLibelle(){
        if(selectedType != null){
            return selectedType.getCode().equals(TypeRegistre.SPECIAL_NAISSANCE.name())
                || selectedType.getCode().equals(TypeRegistre.SPECIAL_DECES.name());
        }
        return false;
    
    }
    
    public boolean requiredLibelle(){
        if(selectedType != null){
            return selectedType.getCode().equals(TypeRegistre.SPECIAL_NAISSANCE.name())
                || selectedType.getCode().equals(TypeRegistre.SPECIAL_DECES.name());
        }
        return false;
    }
    
    
    public void creer(){
        if(dernier < premier){
            FacesMessage message = new FacesMessage("le numéro du dernier registre doit etre inférieur au premier");
            facesContext.addMessage("contentForm:creer",message);
        }
        if(premier <= 0 ){
            FacesMessage message = new FacesMessage("le numéro du premier registre ne peut être inférieur ou égal à '0' ");
            facesContext.addMessage("contentForm:creer",message);
        }
        if(dernier <= 0){
            FacesMessage message = new FacesMessage("le numéro du dernier registre ne peut être inférieur ou égal à '0' ");
            facesContext.addMessage("contentForm:creer",message);
        }
        for (int i = premier; i <= dernier; i++ ){
            LOG.log(Level.INFO, "CREATING REGISTRE ...");
            
            int numeroPremierActe = registreService.numeroPremierActe(selectedType.getCode(),annee,i-1);
            
            /*  si il n'y a aucun acte qui suit l'acte courant, numDernier = numPremier + nbrFeuillet 
             *  sinon numDernier = numPremier du suivant - 1
             *  cela permet de gerer les suppressions au milieu d'une serie
             */
            int numeroDernierActe = registreService.numeroDernierActe(selectedType.getCode(), annee,i+1,
                    numeroPremierActe, nombreDeFeuillets); 
            LOG.log(Level.INFO, "NUMERO PREMIER ACTE: {0}", numeroPremierActe);
            LOG.log(Level.INFO, "NUMERO DERNIER ACTE: {0}", numeroDernierActe);
            
           // LocalDateTime dateOuverture = LocalDateTime.of(annee, 1, 1, 12, 0);
                       
            
            RegistreDto registreDto = new RegistreDto();
            
            registreDto.setTypeRegistre(selectedType.getCode());
            registreDto.setLibelle(selectedType.getLibelle());
            registreDto.setLocalite(currentLocalite.getLibelle());
            registreDto.setLocaliteID(currentLocalite.getId());
            registreDto.setCentre(currentCentre.getLibelle());
            registreDto.setCentreID(currentCentre.getId());
            registreDto.setAnnee(annee);
            registreDto.setNumero(i);
            //registreDto.setDateOuverture(null);
            registreDto.setTribunal(currentTribunal.getLibelle());
            registreDto.setTribunalID(currentTribunal.getId());
            registreDto.setOfficierEtatCivilID(selectedOfficierId);
            registreDto.setNumeroPremierActe(numeroPremierActe);
            registreDto.setNumeroDernierActe(numeroDernierActe);
            //registreDto.setNumeroDernierActe(nombreDeFeuillets + numeroPremierActe - 1);
            registreDto.setNombreDeFeuillets(nombreDeFeuillets);
            registreDto.setNombreActe(0);
            registreDto.setStatut("");
            registreDto.setDateAnnulation(null);
            registreDto.setMotifAnnulation("");
            
            try{
                registreService.creer(registreDto);
            }catch(EntityExistsException | ValidationException e){
                LOG.log(Level.SEVERE, e.getMessage());
                addGlobalMessage(e.getMessage(), FacesMessage.SEVERITY_ERROR);
                return;
            }
           
        }
        
       PrimeFaces.current().dialog().closeDynamic("");
    }
    
    private String registreLibelle(TypeRegistreDto type){
        if(type.getCode().equals(TypeRegistre.SPECIAL_NAISSANCE.name()) 
                || type.getCode().equals(TypeRegistre.SPECIAL_DECES.name())){
            return libelle;
        }
        return type.getLibelle();
    }
    
    private void clear(){
        annee = 0;
        premier = 0;
        dernier = 0;
        nombreDeFeuillets = 0;
    }

    public int getPremier() {
        return premier;
    }

    public void setPremier(int premier) {
        this.premier = premier;
    }

    public int getDernier() {
        return dernier;
    }

    public void setDernier(int dernier) {
        this.dernier = dernier;
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

    public TypeRegistreDto getSelectedType() {
        return selectedType;
    }

    public void setSelectedType(TypeRegistreDto selectedType) {
        this.selectedType = selectedType;
    }

    public TribunalDto getCurrentTribunal() {
        return currentTribunal;
    }

    public void setCurrentTribunal(TribunalDto currentTribunal) {
        this.currentTribunal = currentTribunal;
    }

    public String getSelectedOfficierId() {
        return selectedOfficierId;
    }

    public void setSelectedOfficierId(String selectedOfficierId) {
        this.selectedOfficierId = selectedOfficierId;
    }

    

    public List<TypeRegistreDto> getTypesRegistre() {
        return typesRegistre;
    }

    public List<OfficierEtatCivilDto> getOfficiers() {
        return officiers;
    }

    public int getNombreDeFeuillets() {
        return nombreDeFeuillets;
    }

    public void setNombreDeFeuillets(int nombreDeFeuillets) {
        this.nombreDeFeuillets = nombreDeFeuillets;
    }

    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getTypeRegistre() {
        return typeRegistre;
    }

    public void setTypeRegistre(String typeRegistre) {
        this.typeRegistre = typeRegistre;
    }

    
   
}
