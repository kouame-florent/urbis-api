/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.registre.backing;


import io.urbis.common.util.BaseBacking;
import io.urbis.param.service.LocaliteService;
import io.urbis.param.service.CentreService;
import io.urbis.param.service.TribunalService;
import io.urbis.param.service.OfficierService;
import io.urbis.registre.service.RegistreService;
import io.urbis.registre.service.TypeRegistreService;
import io.urbis.registre.domain.StatutRegistre;
import io.urbis.registre.domain.TypeRegistre;
import io.urbis.registre.dto.RegistreDto;
import io.urbis.registre.dto.TypeRegistreDto;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ValidationException;
import javax.validation.constraints.NotBlank;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.omnifaces.util.Ajax;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author florent
 */
@Named(value = "registreListerBacking")
@ViewScoped
public class ListerBacking extends BaseBacking implements Serializable{
    
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(ListerBacking.class.getName());
    
    @Inject 
    TypeRegistreService typeRegistreService;
    
    @Inject 
    RegistreService registreService;
    
        
    @Inject
    CentreService centreService; 
    
    @Inject
    LocaliteService localiteService;
    
    @Inject
    TribunalService tribunalService;
    
    @Inject
    OfficierService officierService;
    
    
    @Inject
    LazyRegistreDataModel lazyRegistreDataModel;
    
    @Inject
    FilterData filterData;
    
    @Inject
    FacesContext facesCtx;
    
    
    //@Inject
    //SecurityContext sc;    
    
    @Inject
    FacesContext facesContext;
    
   
    
   // private LazyDataModel<RegistreDto> lazyModel;
    
    private List<RegistreDto> registres = new ArrayList<>();
    private RegistreDto selectedRegistre;
    //private int nombreActe;
    
    private List<TypeRegistreDto> typesRegistre;
    private TypeRegistreDto selectedType;
    
   // private int annee;
    
    @PostConstruct
    public void init(){
        
        
        
       // Principal pr = facesContext.getExternalContext().getUserPrincipal();
       // sc.getCallerPrincipal().
       
      // LOG.log(Level.INFO, "--------- SECURITY CONTEXT : {0}", sc.getCallerPrincipal().getName()); 
       
       /*
       Configuration config = new Configuration("http://127.0.0.1:8585/auth/", 
                "urbis",
                "urbis-jsf", 
                Map.of("secret", "f57e3e40-788c-450e-8a29-d32c01501348"), null);
       */
       
        /*
        KeycloakPrincipal principal = (KeycloakPrincipal)facesContext.getExternalContext().getUserPrincipal();
        LOG.log(Level.INFO, "--------- KEYCLOAK PRINCIPAL: {0}", principal.getKeycloakSecurityContext()); 
        RefreshableKeycloakSecurityContext ctx = (RefreshableKeycloakSecurityContext)principal.getKeycloakSecurityContext();
        LOG.log(Level.INFO, "--------- PREFERRED USER NAME: {0}", ctx.getIdToken().getPreferredUsername());  
        
        AccessToken accessToken = principal.getKeycloakSecurityContext().getToken();
        LOG.log(Level.INFO, "--------- SCOPE: {0}", accessToken.getScope());  
        
       */
       // AuthzClient ac = AuthzClient.create();
        
       
       //LOG.log(Level.INFO, "--------- AUTHZ CLIENT: {0}", ac);  
       //LOG.log(Level.INFO, "--------- ACCESS TOKEN: {0}", ac.obtainAccessToken().getTokenType());  
     
       
       
       //Map<String,String> headers = facesContext.getExternalContext().getRequestHeaderMap();
       //headers.forEach((k,v) -> LOG.log(Level.INFO, "--------- KEY : {0}\n",v));
       
       
      // Principal princ = facesCtx.getExternalContext().getUserPrincipal();
               
      // LOG.log(Level.INFO, "--------- SECURITY PRINCIPAL : {0}", princ);   
       
      /*
        Configuration config = new Configuration("http://127.0.0.1:8585/auth/", 
                "urbis",
                "urbis-jsf", 
                Map.of("secret", "f57e3e40-788c-450e-8a29-d32c01501348"), null);
        AuthzClient ac = AuthzClient.create();
       
       LOG.log(Level.INFO, "--------- AUTHZ CLIENT: {0}", ac);  
      */
       
       /*
       String userName = sc.getCallerPrincipal().getName();

        if (sc.getCallerPrincipal() instanceof KeycloakPrincipal) {
          KeycloakPrincipal<KeycloakSecurityContext> kp = (KeycloakPrincipal<KeycloakSecurityContext>)sc.getCallerPrincipal();
         
          // this is how to get the real userName (or rather the login name)
          userName = kp.getKeycloakSecurityContext().getIdToken().getPreferredUsername();
          LOG.log(Level.INFO, "--------- USER NAME FROM EYCLOAK : {0}", userName);   
        }
       */
       
       
        typesRegistre = typeRegistreService.findAll();
        
        lazyRegistreDataModel.setTypeRegistre("naissance");  
        selectedType = defaultSelectedType();
    }
    
    public void onLoad(){
        checkRequiredParams();
    }
    
    private void checkRequiredParams(){
        if(localiteService.count() != 1){
            addGlobalMessage("Aucune localité n'a été créée.", FacesMessage.SEVERITY_ERROR);
            Ajax.update("messageForm");
        }
        
        if(centreService.count() != 1){
            addGlobalMessage("Aucun centre n'a été créé.", FacesMessage.SEVERITY_ERROR);
            Ajax.update("messageForm");
        }
        
        if(tribunalService.count() != 1){
            addGlobalMessage("Aucun tribunal n'a été créé.", FacesMessage.SEVERITY_ERROR);
            Ajax.update("messageForm");
        }
    }
    
    public TypeRegistreDto defaultSelectedType(){
       return typesRegistre.stream()
                .peek(c -> LOG.log(Level.INFO, "TYPE NAME: {0}", c.getCode()))
                //.filter(t -> t.getCode() == TypeRegistre.NAISSANCE.name())
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("cannot get type registre 'NAISSANCE'"));
    }
    
    public void filtrer(){
        LOG.log(Level.INFO, "FILTRER...");
       
        LOG.log(Level.INFO, "FILTRE ANNEE: {0}", filterData.getAnnee());   
        LOG.log(Level.INFO, "FILTRE NUMERO: {0}", filterData.getNumero());
        LOG.log(Level.INFO, "SELECTED TYPE: {0}", selectedType);
        lazyRegistreDataModel.setTypeRegistre(selectedType.getCode());
        lazyRegistreDataModel.setAnnee(filterData.getAnnee());
        lazyRegistreDataModel.setNumero(filterData.getNumero());
    
    }
    
    public void effacerFiltres(){
        filterData.setAnnee(0);
        filterData.setNumero(0);
        //lazyRegistreDataModel.setTypeRegistre("naissance");  
        lazyRegistreDataModel.setAnnee(0);
        lazyRegistreDataModel.setNumero(0);
    }
    
    
    public void onTypeRegistreSelect(){
        LOG.log(Level.INFO, "SELECTED TYPE: {0}", selectedType);
        lazyRegistreDataModel.setTypeRegistre(selectedType.getCode());
       // lazyRegistreDataModel.setAnnee(2018);
        
    }
    
    public void cloturer(@NotBlank String registreID){
        try{
           // registreService.patch(registreID,new RegistrePatchDto(StatutRegistre.CLOTURE.name(),null,""));
            registreService.cloturerRegistre(registreID);
        }catch(ValidationException ex){
            LOG.log(Level.INFO,"ERROR MESSAGE: {0}",ex.getMessage());
            addGlobalMessage(ex.getLocalizedMessage(), FacesMessage.SEVERITY_ERROR);
        }
        
        
    }
    
    public void supprimer(@NotBlank String id){
        LOG.log(Level.INFO, "-- DELETE REGISTRE WITH ID: {0}", id);
        registreService.supprimer(id);
    }
    
    public boolean disabledButtonSupprimer(RegistreDto dto){
       return  dto.getNombreActe() > 0;
    }
    
    /*
    public void showCreerView(){
        Map<String,Object> options = getDialogOptions(96, 96, true);
        options.put("resizable", false);
        PrimeFaces.current().dialog().openDynamic("editer", options, null);
    }
    */
    
    public void openCreerView(){
        List<String> ids = List.of();
        var operations = List.of(Operation.CREATION.name());
        Map<String, List<String>> params = Map.of("reg-id", ids,"operation",operations);
        Map<String,Object> options = getDialogOptions(98, 98, true);
        options.put("resizable", false);
        PrimeFaces.current().dialog().openDynamic("editer", options, params);
    }
    
    public void openCreerSerieView(){
        Map<String,Object> options = getDialogOptions(98, 98, true);
        options.put("resizable", false);
        PrimeFaces.current().dialog().openDynamic("editer-serie", options, null);
    }
    
    /*
    public String openConsulterView(RegistreDto registreDto){
        
        //return "editer";
        
        return "editer?faces-redirect=true&reg-id="+registreDto.getId()+"&operation="+Operation.CONSULTATION.name();
    
    }
    */
    
    
    public void openConsulterView(RegistreDto registreDto){
        LOG.log(Level.INFO, "--- CALL OPEN CONSULTER");
        LOG.log(Level.INFO, "--- REGISTRE ID: {0}", registreDto.getId());
        var operations = List.of(Operation.CONSULTATION.name());
        var values = List.of(registreDto.getId());
        Map<String, List<String>> params = Map.of("reg-id", values,"operation",operations);
        PrimeFaces.current().dialog().openDynamic("/registre/editer", getDialogOptions(98,98,true), params);
    
    }
    
    
    public void openValiderView(RegistreDto registreDto){
        LOG.log(Level.INFO, "--- REGISTRE ID: {0}", registreDto.getId());
        var operations = List.of(Operation.VALIDATION.name());
        var values = List.of(registreDto.getId());

        Map<String, List<String>> params = Map.of("reg-id", values,"operation",operations);
        PrimeFaces.current().dialog().openDynamic("/registre/editer", getDialogOptions(98,98,true), params);
    
    }
    
    public void showParamsView(){
        Map<String,Object> options = getDialogOptions(96, 96, true);
        options.put("resizable", false);
        PrimeFaces.current().dialog().openDynamic("/parametre/parametres", options, null);
    }
    
    public String openDemandeView(){
        String url = "/demande/lister.xhtml?faces-redrect=true";
        LOG.log(Level.INFO, "--- DEMANDE URL: {0}", url);   
        
        return  url;
    }
    
    /*
    public void showModifierView(RegistreDto registreDto){
        LOG.log(Level.INFO, "REGISTRE ID: {0}", registreDto.getId());
        
        var ids = List.of(registreDto.getId());
        var modes = List.of(ViewMode.UPDATE.name());
        Map<String, List<String>> params = Map.of("id", ids,"mode",modes);
        PrimeFaces.current().dialog().openDynamic("/registre/editer", getDialogOptions(96,96,true), params);
    
    }
*/
    
    public void onRegistreValidated(SelectEvent event){
        addGlobalMessage("Le registre a été validé avec succès", FacesMessage.SEVERITY_INFO);
    }
    
    public void showAnnulerView(RegistreDto registreDto){
        LOG.log(Level.INFO, "REGISTRE ID: {0}", registreDto.getId());
        var values = List.of(registreDto.getId());
        Map<String, List<String>> params = Map.of("id", values);
        PrimeFaces.current().dialog().openDynamic("/registre/annuler", getDialogOptions(80,95,true), params);
    
    }
    
    public void openCloturerRegistreView(RegistreDto registreDto){
        LOG.log(Level.INFO, "REGISTRE ID: {0}", registreDto.getId());
        var values = List.of(registreDto.getId());
        Map<String, List<String>> params = Map.of("id", values);
        PrimeFaces.current().dialog().openDynamic("/registre/cloturer", getDialogOptions(80,95,true), params);
    }
    
    
    /*
    public void showDeclarationView(RegistreDto registreDto){
        LOG.log(Level.INFO, "REGISTRE ID: {0}", registreDto.getId());
        var ids = List.of(registreDto.getId());
        var operations = List.of(Operation.DECLARATION_JUGEMENT.name());
        Map<String, List<String>> params = Map.of("id", ids,"operation",operations);
        //String url = "/naissance/edition.xhtml?id="+registreDto.getId()+"&operation="
        //        +Operation.DECLARATION_JUGEMENT.name()+"&faces-redirect=true";
       // LOG.log(Level.INFO, "--- RETURNED URL: {0}", url);
        //return url
        PrimeFaces.current().dialog().openDynamic("/acte/naissance/editer", getDialogOptions(98,98,true), params);
    }
    */
    
    public String openListActes(RegistreDto registreDto){
        TypeRegistre typeRegistre = TypeRegistre.fromString(registreDto.getTypeRegistre());
        LOG.log(Level.INFO, "--- TYPE REGISTRE: {0}", typeRegistre);   
        String uri = "";
        switch(typeRegistre){
            case NAISSANCE:
                uri = openListActesNaissance(registreDto);
                break;
            case MARIAGE:
                uri = openListActesMariage(registreDto);
                break;
            case DECES:
                uri = openListActesDeces(registreDto);
                break;
        }
        
        LOG.log(Level.INFO, "--- URI FROM OPEN LIST ACTES {0}", uri);   
        return uri;
    }
    
    public String openListActesDecRecEnfNaturel(RegistreDto registreDto) {
        /*
        var ids = List.of(registreDto.getId());
        Map<String, List<String>> params = Map.of("reg-id", ids);
        PrimeFaces.current().dialog().openDynamic("/acte/divers/lister-reconnaissance-enfant-naturel", 
                getDialogOptions(100,100,true), params);
        */
        
        LOG.log(Level.INFO, "REC REC REGISTRE: {0}", registreDto.getId());
        
        String url  = "/acte/divers/lister-reconnaissance-enfant-naturel.xhtml?reg-id="
                + registreDto.getId() 
                + "&faces-redirect=true";
        
        
        LOG.log(Level.INFO, "DEC REC URL: {0}", url);
        
        return url;
        
    
    }
    
    public String openListActesDecRecEnfAdulterin(RegistreDto registreDto) {
        /*
        var ids = List.of(registreDto.getId());
        Map<String, List<String>> params = Map.of("reg-id", ids);
        PrimeFaces.current().dialog().openDynamic("/acte/divers/lister-reconnaissance-enfant-adulterin", 
                getDialogOptions(100,100,true), params);
        */
        String url = "/acte/divers/lister-reconnaissance-enfant-adulterin.xhtml?faces-redrect=true"+"&reg-id=" + registreDto.getId();
        LOG.log(Level.INFO, "--- ACTES URL: {0}", url);   
        
        return  url;
    }
    
    public String openListActesConsReconnaissance(RegistreDto registreDto) {
        /*
        var ids = List.of(registreDto.getId());
        Map<String, List<String>> params = Map.of("reg-id", ids);
        PrimeFaces.current().dialog().openDynamic("/acte/divers/lister-consentement-reconnaissance", 
                getDialogOptions(100,100,true), params);
        */
        String url = "/acte/deces/lister-consentement-reconnaissance.xhtml?faces-redrect=true"+"&reg-id=" + registreDto.getId();
        LOG.log(Level.INFO, "--- ACTES URL: {0}", url);   
        
        return  url;
    }
    
    public String openListActesDeces(RegistreDto registreDto) {
        /*
        var ids = List.of(registreDto.getId());
        Map<String, List<String>> params = Map.of("reg-id", ids);
        PrimeFaces.current().dialog().openDynamic("/acte/deces/lister", 
                getDialogOptions(100,100,true), params);
        */
        String url = "/acte/deces/lister.xhtml?faces-redrect=true"+"&reg-id=" + registreDto.getId();
        LOG.log(Level.INFO, "--- ACTES URL: {0}", url);   
        
        return  url;
    }
    
    public boolean renderGererActesMenu(RegistreDto registreDto){
        return !registreDto.getTypeRegistre().equals(TypeRegistre.DIVERS.name());
    }
    
    public boolean renderGererActesDiversMenu(RegistreDto registreDto){
        return registreDto.getTypeRegistre().equals(TypeRegistre.DIVERS.name());
    }
    
    private String openListActesNaissance(RegistreDto registreDto){
        //var ids = List.of(registreDto.getId());
        //var operations = List.of(Operation.SAISIE_ACTE_EXISTANT.name());
        //Map<String, List<String>> params = Map.of("id", ids,"operation",operations);
        //Map<String, List<String>> params = Map.of("id", ids);
        //PrimeFaces.current().dialog().openDynamic("/acte/naissance/lister", getDialogOptions(98,98,true), params);
        
        String url = "/acte/naissance/lister.xhtml?faces-redrect=true"+"&reg-id=" + registreDto.getId();
        LOG.log(Level.INFO, "--- ACTES URL: {0}", url);   
        
        return  url;
        
    }
    
    private String openListActesMariage(RegistreDto registreDto){
        //var ids = List.of(registreDto.getId());
        //var operations = List.of(Operation.SAISIE_ACTE_EXISTANT.name());
        //Map<String, List<String>> params = Map.of("reg-id", ids);
        //PrimeFaces.current().dialog().openDynamic("/acte/mariage/lister", getDialogOptions(98,98,true), params);
        String url = "/acte/mariage/lister.xhtml?faces-redrect=true"+"&reg-id=" + registreDto.getId();
        LOG.log(Level.INFO, "--- ACTES URL: {0}", url);   
        
        return  url;
    }
    
    /*
    private void openListActesDivers(RegistreDto registreDto){
        var ids = List.of(registreDto.getId());
        Map<String, List<String>> params = Map.of("reg-id", ids);
        PrimeFaces.current().dialog().openDynamic("/acte/divers/lister-reconnaissance-enfant-naturel", getDialogOptions(98,98,true), params);
    }
    */
    /*
    public void showActesListView(RegistreDto registreDto){
        LOG.log(Level.INFO, "REGISTRE ID: {0}", registreDto.getId());
        var values = List.of(registreDto.getId());
        Map<String, List<String>> params = Map.of("id", values);
        PrimeFaces.current().dialog().openDynamic("/acte/naissance/lister", getDialogOptions(98,98,true), params);
    }
*/
    
    public boolean renderActeNaissanceMenus(RegistreDto registre){
        return registre.getTypeRegistre().equals("NAISSANCE");
    }
    
    public boolean renderActeMariageMenus(RegistreDto registre){
        return registre.getTypeRegistre().equals("MARIAGE");
    }
    
    public boolean renderActeDecesMenus(RegistreDto registre){
        return registre.getTypeRegistre().equals("DECES");
    }
    
    public boolean renderActeDiversMenus(RegistreDto registre){
        return registre.getTypeRegistre().equals(TypeRegistre.DIVERS.name());
    }
    
    public boolean renderActeSpecialNaissance(RegistreDto registre){
        return registre.getTypeRegistre().equals(TypeRegistre.SPECIAL_NAISSANCE.name());
    }
     
     public boolean renderActeSpecialDeces(RegistreDto registre){
        return registre.getTypeRegistre().equals(TypeRegistre.SPECIAL_DECES.name());
    }
    
    public boolean renderRegistreLibelle(){
        if(selectedType != null){
            return selectedType.getCode().equals(TypeRegistre.SPECIAL_NAISSANCE.name())
                    || selectedType.getCode().equals(TypeRegistre.SPECIAL_DECES.name());
        }
        
        return false;
    }
    
    public boolean disableMenuActiver(RegistreDto registreDto){
        return registreDto.getStatut().equals(StatutRegistre.ANNULE.name()) || 
                registreDto.getStatut().equals(StatutRegistre.CLOTURE.name()) ||
                registreDto.getStatut().equals(StatutRegistre.VALIDE.name());  
    }
    
    public boolean disableMenuAnnuler(RegistreDto registreDto){
        return registreDto.getStatut().equals(StatutRegistre.ANNULE.name()) || 
                registreDto.getStatut().equals(StatutRegistre.CLOTURE.name());  
    }
    
    public boolean disableMenuCloturer(RegistreDto registreDto){
        return registreDto.getStatut().equals(StatutRegistre.ANNULE.name()) || 
                registreDto.getStatut().equals(StatutRegistre.CLOTURE.name()) ||
                registreDto.getStatut().equals(StatutRegistre.PROJET.name());  
    }
    
    public boolean disableMenuModifier(RegistreDto registreDto){ 
        return registreDto.getStatut().equals(StatutRegistre.ANNULE.name()) || 
                registreDto.getStatut().equals(StatutRegistre.CLOTURE.name());  
    }
    
       
    public boolean disableMenuGererActes(RegistreDto registreDto){
        return registreDto.getStatut().equals(StatutRegistre.ANNULE.name()) || 
                registreDto.getStatut().equals(StatutRegistre.PROJET.name());  
    }
    
    
    public void onCreate(SelectEvent event){
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Création de registre", "Registre crée avec succès");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
        
    
    
    
    public void onSerieCreated(SelectEvent event){
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Création de registre", "Serie créee avec succès");
        
        FacesContext.getCurrentInstance().addMessage(null, message);
    
    }
    
    public String statutSeverity(String statut){
        
        if(statut.equalsIgnoreCase("PROJET")){
            return "warning";
        }
         
        if(statut.equalsIgnoreCase("VALIDE")){
            return "success";
        }
        
        if(statut.equalsIgnoreCase("CLOTURE")){
            return "info";
        }
        
         if(statut.equalsIgnoreCase("ANNULE")){
            return "danger";
        }
        
        return "";
    }
    
    public String registreDataTableHeader(){
        if(selectedType != null){
            switch(selectedType.getCode()){
                case "NAISSANCE":
                    return "REGISTRES DES ACTES DE NAISSANCE";
                case "MARIAGE":
                    return "REGISTRES DES ACTES DE MARIAGES";
                case "DIVERS":
                    return "REGISTRES DES ACTES DIVERS";
                case "DECES":
                    return "REGISTRES DES ACTES DE DÉCÈS";
                case "SPECIAL_NAISSANCE":
                    return "REGISTRES DES ACTES SPÉCIAUX DE NAISSANCE";
                case "SPECIAL_DECES":
                    return "REGISTRES DES ACTES SPÉCIAUX DE DÉCÈS";
            }
        }
      
        return "";
    }
    
    public StreamedContent download(){
        /*
       File file = etatService.downloadRegistre();
       LOG.log(Level.INFO, "FILE NAME: {0}", file.getName());
       LOG.log(Level.INFO, "FILE ABSOLUTE PATH: {0}", file.getAbsolutePath());
       LOG.log(Level.INFO, "FILE LENGHT: {0}", file.length());
       
       StreamedContent content = null;
        try {
            InputStream input = new FileInputStream(file);
            content = DefaultStreamedContent.builder()
                .name("registres.pdf")
                .contentType("application/pdf")
                .stream(() -> input).build();
                
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ListerBacking.class.getName()).log(Level.SEVERE, null, ex);
        }
       
       return content;
       */
        return null;
    }
    

    public List<RegistreDto> getRegistres() {
        return registres;
    }

    public RegistreDto getSelectedRegistre() {
        return selectedRegistre;
    }

    public void setSelectedRegistre(RegistreDto selectedRegistre) {
        this.selectedRegistre = selectedRegistre;
    }


    public TypeRegistreDto getSelectedType() {
        return selectedType;
    }

    public void setSelectedType(TypeRegistreDto selectedType) {
        this.selectedType = selectedType;
    }

    public List<TypeRegistreDto> getTypesRegistre() {
        return typesRegistre;
    }

    public LazyRegistreDataModel getLazyRegistreDataModel() {
        return lazyRegistreDataModel;
    }

    public FilterData getFilterData() {
        return filterData;
    }

    public void setFilterData(FilterData filterData) {
        this.filterData = filterData;
    }

    

   
    
    
}
