/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.demande.backing;

import io.urbis.acte.naissance.dto.ActeNaissanceDto;
import io.urbis.acte.naissance.service.ActeNaissanceRestClient;
import io.urbis.common.util.BaseBacking;
import io.urbis.demande.domain.Operation;
import io.urbis.demande.dto.DemandeDto;
import io.urbis.demande.service.DemandeService;
import io.urbis.registre.domain.TypeRegistre;
import io.urbis.registre.dto.TypeRegistreDto;
import io.urbis.registre.service.TypeRegistreService;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotBlank;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import net.sf.jasperreports.engine.JRException;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author florent
 */
@Named(value = "demandeListerBacking")
@ViewScoped
public class ListerBacking extends BaseBacking implements Serializable{
    
    private static final Logger LOG = Logger.getLogger(ListerBacking.class.getName());
    
    @Inject
    LazyDemandeDataModel lazyDemandeDataModel;
    
    @Inject 
    @RestClient
    ActeNaissanceRestClient acteNaissanceRestClient;
    
    @Inject 
    TypeRegistreService typeRegistreService;
    
    @Inject
    DemandeService demandeService;
    
    @Inject
    @ConfigProperty(name = "URBIS_TENANT", defaultValue = "standard")
    String tenant;
    
    private String selectedActeID;
    
    private DemandeDto selectedDemande;
    private String selectedDemandeID;
    
    private List<TypeRegistreDto> typesRegistre;
    private TypeRegistreDto selectedType;
    
    public void onload(){
   
    }
    
   
    
    @PostConstruct
    public void init(){
        
             
        typesRegistre = typeRegistreService.findAll();
        
        lazyDemandeDataModel.setTypeRegistre("naissance");  
        selectedType = defaultSelectedType();
    }
    
     public TypeRegistreDto defaultSelectedType(){
       for(TypeRegistreDto t : typesRegistre){
           if(t.getCode().equals(TypeRegistre.NAISSANCE.name())){
               return t;
           }
       }
       
       throw new IllegalStateException("cannot get type registre 'NAISSANCE'");
       
    }
     
    public void onTypeRegistreSelect(){
        LOG.log(Level.INFO, "SELECTED TYPE: {0}", selectedType);
        lazyDemandeDataModel.setTypeRegistre(selectedType.getCode());
       // lazyRegistreDataModel.setAnnee(2018);
        
    }
    
     public void openCreerView(){
        List<String> ids = List.of();
        var operations = List.of(Operation.CREATION.name());
        List<String> type = List.of(selectedType.getCode());
        Map<String, List<String>> params = Map.of("operation",operations,"type",type);
        Map<String,Object> options = getDialogOptions(98, 98, true);
        options.put("resizable", false);
        PrimeFaces.current().dialog().openDynamic("editer", options, params);
    }
    
    
    
    public void onNewDemande(SelectEvent event){
        //FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Création de demande", "Demande créee avec succès");
        //FacesContext.getCurrentInstance().addMessage(null, message);
         LOG.log(Level.INFO, "RETURN FROM NEW ACTE...");
    }
    
    public StreamedContent download(){
        LOG.log(Level.INFO, "-- SLECTED DEMANDE ID: {0}", selectedDemandeID);
        StreamedContent content = null;
        Path path = null;
        try {
            TypeRegistre typeRegistre = TypeRegistre.fromString(selectedType.getCode());
            String pathString = demandeService.print(selectedActeID,typeRegistre);
            path = Paths.get(pathString);
            InputStream input = Files.newInputStream(path);
            content = DefaultStreamedContent.builder() 
                .name(path.getFileName().toString())
                .contentType("application/pdf")
                .stream(() -> input).build();
                
        } catch (FileNotFoundException | SQLException | JRException ex) {
            Logger.getLogger(ListerBacking.class.getName()).log(Level.SEVERE, null, ex);
            
        } catch (IOException ex) {
            Logger.getLogger(ListerBacking.class.getName()).log(Level.SEVERE, null, ex);
            
        }finally{
           if(path != null){
                try {
                    Files.deleteIfExists(path);
                } catch (IOException ex) {
                    Logger.getLogger(ListerBacking.class.getName()).log(Level.SEVERE, null, ex);
                }
           }
           
        }
       
       return content;
      
    }
    
    public StreamedContent downloadCopie(){
        LOG.log(Level.INFO, "-- SLECTED DEMANDE ID: {0}", selectedDemandeID);
         
        StreamedContent content = null;
        Path path = null;
        try {
            TypeRegistre typeRegistre = TypeRegistre.fromString(selectedType.getCode());
            String pathString = demandeService.printCopie(selectedActeID,typeRegistre);
            path = Paths.get(pathString);
            InputStream input = Files.newInputStream(path);
            content = DefaultStreamedContent.builder() 
                .name(path.getFileName().toString())
                .contentType("application/pdf")
                .stream(() -> input).build();
                
        } catch (FileNotFoundException | SQLException | JRException ex) {
            Logger.getLogger(ListerBacking.class.getName()).log(Level.SEVERE, null, ex);
            
        } catch (IOException ex) {
            Logger.getLogger(ListerBacking.class.getName()).log(Level.SEVERE, null, ex);
            
        }finally{
           if(path != null){
                try {
                    Files.deleteIfExists(path);
                } catch (IOException ex) {
                    Logger.getLogger(ListerBacking.class.getName()).log(Level.SEVERE, null, ex);
                }
           }
           
        }
       
       return content;
    }
    
    
    public void openConsulterDemandeView(DemandeDto dto){
        LOG.log(Level.INFO, "DEMANDE ID: {0}", dto.getId());
        var operations = List.of(Operation.CONSULTATION.name());
        var demandesIds = List.of(dto.getId());
        Map<String, List<String>> params = Map.of("demande-id",demandesIds,"operation",operations);
        PrimeFaces.current().dialog().openDynamic("/demande/editer", getDialogOptions(98,98,false), params);
    }
    
    public void openModifierDemandeView(DemandeDto dto){
    
        LOG.log(Level.INFO, "DEMANDE ID: {0}", dto.getId());
        var operations = List.of(Operation.MODIFICATION.name());
        var demandesIds = List.of(dto.getId());
        Map<String, List<String>> params = Map.of("demande-id",demandesIds,"operation",operations);
        PrimeFaces.current().dialog().openDynamic("/demande/editer", getDialogOptions(98,98,false), params);
    }
    
    public void supprimer(@NotBlank String id){
       boolean result = demandeService.supprimer(id);
       if(!result){
           addGlobalMessage("L'acte ne peut être supprimé!", FacesMessage.SEVERITY_ERROR);
       }
    }
    
     public String returnToRegistresList(){
        return "/registre/lister.xhtml?faces-redirect=true";
    }
    

    public LazyDemandeDataModel getLazyDemandeDataModel() {
        return lazyDemandeDataModel;
    }

    public void setLazyDemandeDataModel(LazyDemandeDataModel lazyDemandeDataModel) {
        this.lazyDemandeDataModel = lazyDemandeDataModel;
    }

    public DemandeDto getSelectedDemande() {
        return selectedDemande;
    }

    public void setSelectedDemande(DemandeDto selectedDemande) {
        this.selectedDemande = selectedDemande;
    }

   

    public List<TypeRegistreDto> getTypesRegistre() {
        return typesRegistre;
    }

    public void setTypesRegistre(List<TypeRegistreDto> typesRegistre) {
        this.typesRegistre = typesRegistre;
    }

    public TypeRegistreDto getSelectedType() {
        return selectedType;
    }

    public void setSelectedType(TypeRegistreDto selectedType) {
        this.selectedType = selectedType;
    }

    public String getSelectedDemandeID() {
        return selectedDemandeID;
    }

    public void setSelectedDemandeID(String selectedDemandeID) {
        this.selectedDemandeID = selectedDemandeID;
    }

    public String getSelectedActeID() {
        return selectedActeID;
    }

    public void setSelectedActeID(String selectedActeID) {
        this.selectedActeID = selectedActeID;
    }

   
    
    
}
