/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.urbis.acte.deces.service;

import io.agroal.api.AgroalDataSource;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.urbis.acte.deces.domain.ActeDeces;
import io.urbis.acte.deces.domain.ActeDecesEtat;
import io.urbis.acte.deces.dto.ActeDecesEtatDto;
import io.urbis.acte.deces.dto.MentionAnnulationDecesDto;
import io.urbis.acte.deces.dto.MentionRectificationDecesDto;
import io.urbis.common.util.DateTimeUtils;
import io.urbis.param.service.LocaliteService;
import io.urbis.security.service.AuthenticationContext;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
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
public class ActeDecesEtatService {
    
    @Inject
    Logger log;
    
    @Inject
    AuthenticationContext authenticationContext;
    
    @Inject
    LocaliteService localiteService;
    
    @Inject
    MentionRectificationDecesService mentionRectificationService;
    
    @Inject
    MentionAnnulationDecesService  mentionAnnulationService;
     
    @Inject
    AgroalDataSource defaultDataSource;
  
    
    public void creer(String acteID){
        
        ActeDecesEtat etat = new ActeDecesEtat();
        ActeDeces acte = ActeDeces.findById(acteID);
        etat.acteDeces = acte;
        etat.updatedBy = authenticationContext.userLogin();  
        
        
        Set<MentionRectificationDecesDto> rectifications = mentionRectificationService.findByActeDeces(acteID);
        Optional<MentionRectificationDecesDto> optRectification = rectifications.stream().max(Comparator.comparing(m -> m.getDateDressage()));
        optRectification.ifPresent(mRectification -> { 
            etat.mentionRectificationTexte = mRectification.getDecision();
           
        });
        
        Set<MentionAnnulationDecesDto> annulations = mentionAnnulationService.findByActeDeces(acteID);
        Optional<MentionAnnulationDecesDto> optAnnulation = annulations.stream().max(Comparator.comparing(m -> m.getDateDressage()));
        optAnnulation.ifPresent(mAnnulation -> { 
            etat.mentionAnnulationTexte = mAnnulation.getDecision();
           
        });
        
        
        etat.nomCompletTexte = nomComplet(acte);
        etat.numeroActeTexte = numeroActeTexte(acte);
        etat.titreTexte = titreTexte(acte);
        etat.extraitTexte = extraitTexte(acte);
        etat.copieNumeroActeTexte = copieNumeroActeTexte(acte);
        etat.copieTitreTexte = copieTitretexte(acte);
        etat.copieTexte = copieIntegraleTexte(acte);
        etat.copieMentionsTextes = copieMentionsTextes(acte);
        
        etat.persist();
   
    }
    
    public void modifier(String acteID){

        ActeDeces acte = ActeDeces.findById(acteID);
        PanacheQuery<ActeDecesEtat> query = ActeDecesEtat.find("acteDeces", acte);
        ActeDecesEtat etat = query.singleResult();
        
        if(etat != null){
            
            etat.updatedBy = authenticationContext.userLogin();
            
            etat.nomCompletTexte = nomComplet(acte);
            etat.numeroActeTexte = numeroActeTexte(acte);
            etat.titreTexte = titreTexte(acte);
            etat.extraitTexte = extraitTexte(acte);
            etat.copieNumeroActeTexte = copieNumeroActeTexte(acte);
            etat.copieTitreTexte = copieTitretexte(acte);
            etat.copieTexte = copieIntegraleTexte(acte);
            etat.copieMentionsTextes = copieMentionsTextes(acte);
            
            etat.mentionRectificationTexte = mentionRectificationService.mentionRecenteTexte(acte);
            etat.mentionAnnulationTexte = mentionAnnulationService.mentionRecenteTexte(acte);
            
            
        }
    
    }
    
    private String nomComplet(ActeDeces acte){
        StringBuilder sb = new StringBuilder();
        sb.append(acte.defunt.nom.toUpperCase(Locale.FRENCH));
        sb.append(" ");
        sb.append(acte.defunt.prenoms);
        
        
        return sb.toString();
        
    }
    
    public String numeroActeTexte(ActeDeces acte){
        StringBuilder sb = new StringBuilder();
        sb.append("N° ");
        sb.append(acte.numero);
        sb.append(" ");
        sb.append("DU ");
        sb.append(acte.dateDressage.format(DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.FRENCH)));
        sb.append(" ");
        sb.append("DU REGISTRE");
        
        return sb.toString();
    
    }
    
    public String titreTexte(ActeDeces acte){
        StringBuilder sb = new StringBuilder();
        sb.append("Pour l'année ");
        sb.append(acte.registre.reference.annee);
        
        return sb.toString();
    }
    
    public String extraitTexte(ActeDeces acte){
        String text = "Le %s ./.\n"
        + "à %s ./.\n"
        + "est décédé(e) à l'Hopital de %s ./.\n" 
        + "%s ./.\n"
        + "Né(e) le %s ./.\n"
        + "à %s ./.\n" 
        + "Profession %s ./.\n\n"
        + "Fils de %s ./.\n"
        + "et de %s ./.\n";
        
        Formatter formatter = new Formatter(Locale.FRENCH);
        return formatter.format(text,
                DateTimeUtils.dateSpelling(acte.defunt.dateDeces.toLocalDate()),
                DateTimeUtils.hourSpelling(acte.defunt.dateDeces),
                localiteService.findActive().getLibelle(),
                acte.defunt.nom+ " " + acte.defunt.prenoms,
                DateTimeUtils.dateSpelling(acte.defunt.dateNaissance),
                acte.defunt.localiteNaissance,
                getEmploi(acte.defunt.profession),
                acte.pere.nom+" "+acte.pere.prenoms,
                acte.mere.nom+" "+acte.mere.prenoms).toString();
        
        
    }
    
    private String getEmploi(String emploi){
        if(emploi == null || emploi.isBlank()){
            return "Sans-emploi";
        }
        return emploi;
    }
    
    
    public String copieNumeroActeTexte(ActeDeces acte){
        StringBuilder sb = new StringBuilder();
        sb.append("Acte N° ");
        sb.append(acte.numero);
        sb.append(" ");
        sb.append("DU ");
        sb.append(acte.defunt.dateDeces.format(DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.FRANCE)));
       
        return sb.toString();
    
    }
    
    
    public String copieTitretexte(ActeDeces acte){
        StringBuilder sb = new StringBuilder();
        sb.append("Pour l'année ");
        sb.append(acte.registre.reference.annee);
        
        return sb.toString();
    }
    
    public String copieIntegraleTexte(ActeDeces acte){
       
        String text = "Le %s, à %s, "
        + "est décédé à l'Hopital de %s, "
        + "%s, %s né le %s "
        + "à %s, fils de %s et de %s ./.\n\n"
        + "Dressé le %s, à %s, par nous, "
        + "%s Officier d'état civil, %s de la %s ./.\n\n"
        + "Lecture fait, et invité à lire l'acte.\n"
        + "Nous avons signé avec le déclarant ./.";
        
          
        Formatter formatter = new Formatter(Locale.FRENCH);
        return formatter.format(text, 
                DateTimeUtils.dateSpelling(acte.defunt.dateDeces.toLocalDate()),
                DateTimeUtils.hourSpelling(acte.defunt.dateDeces),
                localiteService.findActive().getLibelle(),
                acte.defunt.nom +" "+acte.defunt.prenoms,
                getEmploi(acte.defunt.profession),
                DateTimeUtils.dateSpelling(acte.defunt.dateNaissance),
                acte.defunt.localiteNaissance,
                acte.pere.nom+" "+acte.pere.prenoms,
                acte.mere.nom+" "+acte.mere.prenoms,
                DateTimeUtils.dateSpelling(acte.dateDressage.toLocalDate()),
                DateTimeUtils.hourSpelling(acte.dateDressage),
                acte.officierEtatCivil.nom.toUpperCase(Locale.FRENCH) + " " + acte.officierEtatCivil.prenoms,
                acte.officierEtatCivil.titre,
                localiteService.findActive().getType()).toString();
                
    }
    
    public String copieMentionsTextes(ActeDeces acte) {
        StringBuilder sb = new StringBuilder();
        sb.append(mentionRectificationService.mentionRecenteTexte(acte));
        sb.append("\n");
        sb.append("\n");
        sb.append(mentionAnnulationService.mentionRecenteTexte(acte));
        return sb.toString();
    
    }
    
    
    public void patcher(@NotNull ActeDecesEtatDto dto){
       ActeDecesEtat etat = ActeDecesEtat.findById(dto.getId());
       if(etat == null){
           throw new EntityNotFoundException("ActeMariageEtat not found");
       }
       
       etat.extraitTexte = dto.getExtraitTexte();
       etat.copieTexte = dto.getCopieTexte();
       etat.mentionRectificationTexte = dto.getMentionRectificationTexte();
       etat.mentionAnnulationTexte = dto.getMentionAnnulationTexte();
   }
    
    public ActeDecesEtatDto mapToDto(@NotNull ActeDecesEtat etat){
        ActeDecesEtatDto dto = new ActeDecesEtatDto();
        
        dto.setId(etat.id);
        dto.setActeDecesID(etat.acteDeces.id);
        dto.setCopieNumeroActeTexte(etat.copieNumeroActeTexte);
        dto.setCopieTexte(etat.copieTexte);
        dto.setExtraitTexte(etat.extraitTexte);
        dto.setCopieMentionsTextes(etat.copieMentionsTextes);
        dto.setNomCompletTexte(etat.nomCompletTexte);
        dto.setNumeroActeTexte(etat.numeroActeTexte);
        dto.setTitreTexte(etat.titreTexte);
        dto.setMentionRectificationTexte(etat.mentionRectificationTexte);
        dto.setMentionAnnulationTexte(etat.mentionAnnulationTexte);
        
        return dto;
    
    }
    
     public String print(String acteID) throws SQLException, JRException, FileNotFoundException{
        
        return doPrint(acteID, "/META-INF/resources/report/acte_deces.jasper");
              
   }
     
    public String printCopie(String acteID) throws SQLException, JRException, FileNotFoundException{
       return doPrint(acteID, "/META-INF/resources/report/acte_deces_ci.jasper");
       
   }
    
    
   private String doPrint(String acteID,String resource) throws SQLException, JRException, FileNotFoundException{
     
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream reportStream = loader.getResourceAsStream(resource);
        log.infof("-- REPORT INPUT: %s", reportStream);
        
        
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("ACTE_DECES_ID", acteID);
        JasperPrint jasperPrint = JasperFillManager.fillReport(reportStream, parameters, defaultDataSource.getConnection());
       
        JRPdfExporter exporter = new JRPdfExporter();
        

        String reportFilePath = filePath(acteID);
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput( new SimpleOutputStreamExporterOutput(reportFilePath));
        
        exporter.exportReport();
        
        return reportFilePath;
   
   }
   
   
   private String filePath(String acteID){
      ActeDeces acte = ActeDeces.findById(acteID);
      if( acte == null){
          throw new EntityNotFoundException("ActeDeces not found");
      }
      String name = acte.numero + "_" + acte.registre.dateOuverture  +"-"+ LocalDateTime.now().toString() + ".pdf";
      
      return "/tmp/" + name.replaceAll(" ", "-");
   }
  
   
   public ActeDecesEtatDto findByActeMariage(ActeDeces acte) {
        PanacheQuery<ActeDecesEtat> pq = ActeDecesEtat.find("acteDeces", acte);
        ActeDecesEtat etat = pq.firstResult();
        if(etat == null){
            throw new EntityNotFoundException("ActeDecesEtat not found");
        }
        
        return mapToDto(etat);
    }
    
}
