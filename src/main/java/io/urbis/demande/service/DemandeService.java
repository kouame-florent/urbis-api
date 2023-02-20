/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.demande.service;

import io.agroal.api.AgroalDataSource;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Sort;
import io.urbis.acte.common.domain.Acte;
import io.urbis.acte.deces.service.ActeDecesService;
import io.urbis.acte.divers.service.ActeDiversService;
import io.urbis.acte.mariage.service.ActeMariageService;
import io.urbis.acte.naissance.service.ActeNaissanceService;
import io.urbis.acte.common.service.ActeService;
import io.urbis.acte.deces.domain.ActeDeces;
import io.urbis.acte.divers.domain.ActeDivers;
import io.urbis.acte.mariage.domain.ActeMariage;
import io.urbis.acte.naissance.domain.ActeNaissance;
import io.urbis.common.domain.TypePiece;

import io.urbis.demande.domain.Demande;
import io.urbis.demande.domain.Demandeur;
import io.urbis.demande.domain.StatutActe;
import io.urbis.demande.domain.StatutDemande;
import io.urbis.demande.dto.DemandeDto;
import io.urbis.registre.domain.TypeRegistre;
import io.urbis.security.service.AuthenticationContext;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.jboss.logging.Logger;

/**
 *
 * @author florent
 */
@ApplicationScoped
@Transactional 
public class DemandeService {
    
    @Inject
    ActeDecesService acteDecesService;
    
    @Inject
    ActeDiversService acteDiversService;
    
    @Inject
    ActeMariageService acteMariageService;
    
    @Inject
    ActeNaissanceService acteNaissanceService;
    
    @Inject
    ActeService acteService;
    
        
    @Inject
    AgroalDataSource defaultDataSource;
    
        
    @Inject
    EntityManager em;
    
    @Inject
    Logger log;
    
    @Inject
    AuthenticationContext authenticationContext;
    
    
    public String creer(@NotNull DemandeDto dto){
        
       
        Acte   acte = findActeByDemande(dto);
             
        Demandeur demandeur = new Demandeur();

        Demande demande = new Demande(demandeur);
        
        if(acte != null){
            demande.statutActe = StatutActe.ACTE_PRESENT;
            demande.statutDemande = StatutDemande.PRIS_EN_CHARGE;
            demande.acte = acte;
        }else{
            demande.statutActe = StatutActe.ACTE_ABSENT;
            demande.statutDemande = StatutDemande.EN_ATTENTE;
        }
        
        
        demande.dateHeureDemande = dto.getDateHeureDemande();
        demande.dateHeureRdvRetrait = dto.getDateHeureRdvRetrait();
        demande.dateOuvertureRegistre = dto.getDateOuvertureRegistre();
        demande.demandeur.email = dto.getDemandeurEmail();
        demande.demandeur.nom = dto.getDemandeurNom();
        demande.demandeur.numeroTelephone = dto.getDemandeurNumeroTelephone();
        demande.demandeur.numeroPiece = dto.getDemandeurNumeroPiece();
        demande.demandeur.prenoms = dto.getDemandeurPrenoms();
        demande.demandeur.qualite = dto.getDemandeurQualite();
        demande.demandeur.typePiece = TypePiece.fromString(dto.getDemandeurTypePiece());

        demande.nombreCopies = dto.getNombreCopies();
        demande.nombreExtraits = dto.getNombreExtraits();
        demande.numero = dto.getNumero();
        demande.numeroActe = dto.getNumeroActe();
        demande.typeRegistre = TypeRegistre.fromString(dto.getTypeRegistre());
        demande.updatedBy = authenticationContext.userLogin();

        demande.persist();
        //demande.persistAndFlush();
        
        return demande.id;
        
     }
    
    
    public boolean verifierActe(@NotBlank String id,@NotNull DemandeDto dto){
        Acte acte = findActeByDemande(dto);
        Demande demande = Demande.findById(id);    
        if(acte != null && demande.statutDemande == StatutDemande.EN_ATTENTE){         
            demande.statutActe = StatutActe.ACTE_PRESENT;
            demande.statutDemande = StatutDemande.PRIS_EN_CHARGE;
            demande.acte = acte;
        }
        
       return acte != null;       
    
    }
    
     
    
    public Acte findActeByDemande(@NotNull DemandeDto dto){
        TypeRegistre typeRegistre = TypeRegistre.fromString(dto.getTypeRegistre());
        log.infof("---->> TYPE REGISTRE: %s", typeRegistre);
        log.infof("---->> NUMERO ACTE: %s", dto.getNumeroActe());
        log.infof("---->> DATE OUVERTURE REGISTRE: %s", dto.getDateOuvertureRegistre());
        
             
         Acte  acte = acteService.findByDemandeCreteria(dto.getNumeroActe(),typeRegistre,
                            dto.getDateOuvertureRegistre());
         
        return acte;
    }
    
    public DemandeDto findById(@NotBlank String id){
       Demande d = Demande.findById(id);
       return mapToDto(d);
    }
    
   
    public void modifier(@NotBlank String id,@NotNull DemandeDto dto){
        Demande demande = Demande.findById(id);
        
        
        demande.dateHeureDemande = dto.getDateHeureDemande();
        demande.dateHeureRdvRetrait = dto.getDateHeureRdvRetrait();
        demande.dateOuvertureRegistre = dto.getDateOuvertureRegistre();
        demande.demandeur.email = dto.getDemandeurEmail();
        demande.demandeur.nom = dto.getDemandeurNom();
        demande.demandeur.numeroTelephone = dto.getDemandeurNumeroTelephone();
        demande.demandeur.numeroPiece = dto.getDemandeurNumeroPiece();
        demande.demandeur.prenoms = dto.getDemandeurPrenoms();
        demande.demandeur.qualite = dto.getDemandeurQualite();
        demande.demandeur.typePiece = TypePiece.fromString(dto.getDemandeurTypePiece());

        demande.nombreCopies = dto.getNombreCopies();
        demande.nombreExtraits = dto.getNombreExtraits();
        demande.numero = dto.getNumero();
        demande.numeroActe = dto.getNumeroActe();
        demande.typeRegistre = TypeRegistre.fromString(dto.getTypeRegistre());
        demande.updatedBy = authenticationContext.userLogin();
    }
    
    public boolean supprimer(@NotBlank String id){
        return Demande.deleteById(id);
    }
    
    
    public List<DemandeDto> findWithFilters(int offset,int pageSize,String type){
        
        PanacheQuery<Demande> query = Demande.find("typeRegistre", 
               Sort.by("numero", Sort.Direction.Descending),
               TypeRegistre.fromString(type));
        
       // PanacheQuery<Demande>  query = Demande.findAll(Sort.by("numero").descending());
        
        PanacheQuery<Demande> rq =  query.range(offset, offset + (pageSize-1));
        
        return rq.stream().map(this::mapToDto).collect(Collectors.toList());
      
    }
    
    
    public int count(){
        return (int)Demande.count();
    }
    
     public int numeroDemande(){
   
        TypedQuery<Integer> query =  em.createNamedQuery("Demande.findMaxNumero", Integer.class);   
          
        try{
            Integer num = query.getSingleResult();
            log.infof("--MAX NUM DEMANDE: %d", num);
            if(num != null){
                return num + 1;
            }
            log.infof("aucune demande trouvée...");
            return 1;
        }catch(NoResultException ex){
            log.infof("aucune demande trouvée...");
            return 1;
        }
      
      
    }
    


    public DemandeDto mapToDto(@NotNull Demande demande){
        
        DemandeDto dto = new DemandeDto();
        Acte acte = acteService.findByDemandeCreteria(demande.numeroActe, 
                demande.typeRegistre, demande.dateOuvertureRegistre);
                
        dto.setId(demande.id);
        if(acte != null){
            dto.setActeID(acte.id);
        }
        
        dto.setCreated(demande.created);
        dto.setDateHeureDemande(demande.dateHeureDemande);
        dto.setDateHeureRdvRetrait(demande.dateHeureRdvRetrait);
        dto.setDateOuvertureRegistre(demande.dateOuvertureRegistre);
        dto.setDemandeurEmail(demande.demandeur.email);
        dto.setDemandeurNom(demande.demandeur.nom);
        dto.setDemandeurNumeroTelephone(demande.demandeur.numeroTelephone);
        dto.setDemandeurNumeroPiece(demande.demandeur.numeroPiece);
        dto.setDemandeurPrenoms(demande.demandeur.prenoms);
        dto.setDemandeurQualite(demande.demandeur.qualite);
        dto.setDemandeurTypePiece(demande.demandeur.typePiece.name());
        dto.setId(demande.id);
        dto.setNombreCopies(demande.nombreCopies);
        dto.setNombreExtraits(demande.nombreExtraits);
        dto.setNumero(demande.numero);
        dto.setNumeroActe(demande.numeroActe);
        dto.setTypeRegistre(demande.typeRegistre.name());
        dto.setUpdated(demande.updated);
        
        dto.setStatutDemande(demande.statutDemande.name()); 
        dto.setStatutActe(demande.statutActe.name());
        
        return dto;
    }
    
    
    public String printExtrait(@NotNull @NotBlank String acteID,TypeRegistre type) throws SQLException, JRException, FileNotFoundException{
        
        return doPrint(acteID, type,"/META-INF/resources/report/" + getExtraitTemplateName(type));
              
   }
     
    public String printCopie(@NotNull @NotBlank String acteID,TypeRegistre type) throws SQLException, JRException, FileNotFoundException{
       String reportPath =  "/META-INF/resources/report/"+ getCopieTemplateName(type);
       log.infof("---REPORT PATH: %s", reportPath);
       return doPrint(acteID,type,reportPath);
       
   }
    
    
   private String doPrint(String acteID,TypeRegistre type,String resource) throws SQLException, JRException, FileNotFoundException{
     
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream reportStream = loader.getResourceAsStream(resource);
        log.infof("-- REPORT INPUT: %s", reportStream);
        
        
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(getReportIdParamName(type), acteID);
        JasperPrint jasperPrint = JasperFillManager.fillReport(reportStream, parameters, defaultDataSource.getConnection());
       
        JRPdfExporter exporter = new JRPdfExporter();
        

        String reportFilePath = filePath(acteID,type);
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput( new SimpleOutputStreamExporterOutput(reportFilePath));
        
        exporter.exportReport();
        
        return reportFilePath;
   
   }
   
   
   private String filePath(String acteID,TypeRegistre type){
      Acte acte = getActe(acteID,type);
      if( acte == null){
          throw new EntityNotFoundException("Acte not found");
      }
      String name = acte.numero + "_" + acte.registre.dateOuverture  +"-"+ LocalDateTime.now().toString() + ".pdf";
      
      return "/tmp/" + name.replaceAll(" ", "-");
   }
   
   private String getExtraitTemplateName(TypeRegistre typeRegistre){
       
       String tpl = "";
       
       switch(typeRegistre){
            case DECES:
               tpl = "acte_deces.jasper";
               break;
            case MARIAGE:
               tpl = "acte_mariage.jasper";
               break;
            case SPECIAL_DECES:
               tpl = "acte_deces.jasper";
               break;
            case DIVERS:
               tpl = "acte_divers.jasper";
               break;
            case NAISSANCE:
               tpl = "acte_naissance.jasper";
               break;
            case SPECIAL_NAISSANCE:
               tpl = "acte_naissance.jasper";
               break;
       }
   
       return tpl;
   }
   
   private String getCopieTemplateName(TypeRegistre typeRegistre){
       
       String tpl = "";
       
       switch(typeRegistre){
            case DECES:
               tpl = "acte_deces_ci.jasper";
               break;
            case MARIAGE:
               tpl = "acte_mariage_ci.jasper";
               break;
            case SPECIAL_DECES:
               tpl = "acte_deces_ci.jasper";
               break;
            case DIVERS:
               tpl = "acte_divers_ci.jasper";
               break;
            case NAISSANCE:
               tpl = "acte_naissance_ci.jasper";
               break;
            case SPECIAL_NAISSANCE:
               tpl = "acte_naissance_ci.jasper";
               break;
       }
   
       return tpl;
   }
   
   public Acte getActe(@NotNull String acteID,TypeRegistre typeRegistre){
       
       log.infof("--->> ACTE ID: %s", acteID);
       Acte acte = null;
   
       switch(typeRegistre){
            case DECES:
               acte = ActeDeces.findById(acteID);
               break;
            case MARIAGE:
               acte = ActeMariage.findById(acteID);
               break;
            case SPECIAL_DECES:
               acte = ActeDeces.findById(acteID);
               break;
            case DIVERS:
               acte = ActeDivers.findById(acteID);
               break;
            case NAISSANCE:
               acte = ActeNaissance.findById(acteID);
               break;
            case SPECIAL_NAISSANCE:
               acte = ActeNaissance.findById(acteID);
               break;
       }
       
       return acte;
   }
   
   private String getReportIdParamName(TypeRegistre typeRegistre){
       String name = "";
       
       switch(typeRegistre){
            case DECES:
               name = "ACTE_DECES_ID";
               break;
            case MARIAGE:
               name = "ACTE_MARIAGE_ID";
               break;
            case SPECIAL_DECES:
               name = "ACTE_DECES_ID";
               break;
            case DIVERS:
               name = "ACTE_DIVERS_ID";
               break;
            case NAISSANCE:
               name = "ACTE_NAISSANCE_ID";
               break;
            case SPECIAL_NAISSANCE:
               name = "ACTE_NAISSANCE_ID";
               break;
       }
   
       return name;
   
   }
}
