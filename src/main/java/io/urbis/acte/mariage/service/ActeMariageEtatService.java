/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.acte.mariage.service;

import io.agroal.api.AgroalDataSource;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.urbis.acte.mariage.domain.ActeMariage;
import io.urbis.acte.mariage.domain.ActeMariageEtat;
import io.urbis.acte.mariage.dto.ActeMariageEtatDto;
import io.urbis.acte.mariage.dto.MentionAnnulationMariageDto;
import io.urbis.acte.mariage.dto.MentionDivorceDto;
import io.urbis.acte.mariage.dto.MentionModifRegimeBiensDto;
import io.urbis.acte.mariage.dto.MentionOrdonRetranscriptionDto;
import io.urbis.acte.mariage.dto.MentionRectificationMariageDto;
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
public class ActeMariageEtatService {
    
    @Inject
    Logger log;
    
    @Inject
    AuthenticationContext authenticationContext;
    
    @Inject
    LocaliteService localiteService;
    
    @Inject
    MentionDivorceService mentionDivorceService ;
    
    @Inject
    MentionModifRegimeBiensService mentionModifRegimeBiensService;
    
    @Inject
    MentionOrdonRetranscriptionService mentionOrdonRetranscriptionService;
    
    @Inject
    MentionRectificationMariageService mentionRectificationService;
    
    @Inject
    MentionAnnulationMariageService  mentionAnnulationService;
    
    @Inject
    AgroalDataSource defaultDataSource;
    
     
    
    public void creer(String acteID){
        
        ActeMariageEtat etat = new ActeMariageEtat();
        ActeMariage acte = ActeMariage.findById(acteID);
        etat.acteMariage = acte;
        etat.updatedBy = authenticationContext.userLogin();  
        
        
        Set<MentionDivorceDto> adoptions = mentionDivorceService.findByActeMariage(acteID);
        Optional<MentionDivorceDto> optAdoption = adoptions.stream().max(Comparator.comparing(m -> m.getDateDressage()));
        optAdoption.ifPresent(m -> { 
            etat.mentionDivorceTexte = m.getDecision();
           
        });
        
        Set<MentionModifRegimeBiensDto> modif = mentionModifRegimeBiensService.findByActeMariage(acteID);
        Optional<MentionModifRegimeBiensDto> optModif  = modif.stream().max(Comparator.comparing(m -> m.getDateDressage()));
        optModif.ifPresent(m -> { 
            etat.mentionModifRegimeBiensTexte = m.getDecision();
           
        });
        
        Set<MentionOrdonRetranscriptionDto> ords = mentionOrdonRetranscriptionService.findByActeMariage(acteID);
        Optional<MentionOrdonRetranscriptionDto> optOrd = ords.stream().max(Comparator.comparing(m -> m.getDateDressage()));
        optOrd.ifPresent(m -> { 
            etat.mentionOrdonRetranscriptionTexte = m.getDecision();
           
        });
        
        Set<MentionRectificationMariageDto> rectifications = mentionRectificationService.findByActeMariage(acteID);
        Optional<MentionRectificationMariageDto> optRectification = rectifications.stream().max(Comparator.comparing(m -> m.getDateDressage()));
        optRectification.ifPresent(mRectification -> { 
            etat.mentionRectificationTexte = mRectification.getDecision();
           
        });
        
        Set<MentionAnnulationMariageDto> annulations = mentionAnnulationService.findByActeMariage(acteID);
        Optional<MentionAnnulationMariageDto> optAnnulation = annulations.stream().max(Comparator.comparing(m -> m.getDateDressage()));
        optAnnulation.ifPresent(mAnnulation -> { 
            etat.mentionAnnulationTexte = mAnnulation.getDecision();
           
        });
      
        
        etat.nomsMariesTexte = nomsMaries(acte);
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
        ActeMariage acte = ActeMariage.findById(acteID);
        PanacheQuery<ActeMariageEtat> query = ActeMariageEtat.find("acteMariage", acte);
        ActeMariageEtat etat = query.singleResult();
        
        if(etat != null){
            
            etat.updatedBy = authenticationContext.userLogin();
            
            etat.nomsMariesTexte = nomsMaries(acte);
            etat.numeroActeTexte = numeroActeTexte(acte);
            etat.titreTexte = titreTexte(acte);
            etat.extraitTexte = extraitTexte(acte);
            etat.copieNumeroActeTexte = copieNumeroActeTexte(acte);
            etat.copieTitreTexte = copieTitretexte(acte);
            etat.copieTexte = copieIntegraleTexte(acte);
            etat.copieMentionsTextes = copieMentionsTextes(acte);
            
            etat.mentionDivorceTexte = mentionDivorceService.mentionRecenteTexte(acte);
            etat.mentionModifRegimeBiensTexte = mentionModifRegimeBiensService.mentionRecenteTexte(acte);
            etat.mentionOrdonRetranscriptionTexte = mentionOrdonRetranscriptionService.mentionRecenteTexte(acte);
            etat.mentionRectificationTexte = mentionRectificationService.mentionRecenteTexte(acte);
            etat.mentionAnnulationTexte = mentionAnnulationService.mentionRecenteTexte(acte);
        }
    
    }
    
    private String nomsMaries(ActeMariage acte){
        StringBuilder sb = new StringBuilder();
        sb.append(acte.epoux.conjoint.nom.toUpperCase(Locale.FRENCH));
        sb.append(" ");
        sb.append(acte.epoux.conjoint.prenoms);
        sb.append("\n");
        sb.append(" et ");
        sb.append("\n");
        sb.append(acte.epouse.conjoint.nom.toUpperCase(Locale.FRENCH));
        sb.append(" ");
        sb.append(acte.epouse.conjoint.prenoms);
        
        return sb.toString();
        
    }
    
    public String numeroActeTexte(ActeMariage acte){
        StringBuilder sb = new StringBuilder();
        sb.append("N° ");
        sb.append(acte.numero);
        sb.append(" ");
        sb.append("DU ");
        sb.append(acte.dateMariage.format(DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.FRENCH)));
        sb.append(" ");
        sb.append("DU REGISTRE");
        
        return sb.toString();
    
    }
    
    public String titreTexte(ActeMariage acte){
        StringBuilder sb = new StringBuilder();
        sb.append("Pour l'année ");
        sb.append(acte.registre.reference.annee);
        
        return sb.toString();
    }
    
    public String extraitTexte(ActeMariage acte){
        StringBuilder sb = new StringBuilder();
        sb.append("Le ");
        sb.append(DateTimeUtils.dateSpelling(acte.dateMariage.toLocalDate()));
        sb.append("./.\n");
        sb.append("Entre ");
        sb.append(acte.epoux.conjoint.nom.toUpperCase(Locale.FRENCH));
        sb.append(" ");
        sb.append(acte.epoux.conjoint.prenoms);
        sb.append("./.\n");
        sb.append(getEmploi(acte.epoux.conjoint.profession));
        sb.append("./.\n");
        sb.append("Né le ");
        sb.append(DateTimeUtils.dateSpelling(acte.epoux.conjoint.dateNaissance));
        sb.append(" à ");
        sb.append(acte.epoux.conjoint.lieuNaissance);
        sb.append("./.\n");
        sb.append("Fils de ");
        sb.append(acte.epoux.pere.nom.toUpperCase(Locale.FRENCH));
        sb.append(" ");
        sb.append(acte.epoux.pere.prenoms);
        sb.append("./.\n");
        sb.append("et de ");
        sb.append(acte.epoux.mere.nom.toUpperCase(Locale.FRENCH));
        sb.append(" ");
        sb.append(acte.epoux.mere.prenoms);
        sb.append("./.\n");
        sb.append("domicilié à ");
        sb.append(acte.epoux.conjoint.domicile);
        sb.append("./.\n");
        sb.append("Et ");
        sb.append(acte.epouse.conjoint.nom.toUpperCase(Locale.FRENCH));
        sb.append(" ");
        sb.append(acte.epouse.conjoint.prenoms);
        sb.append("\n");
        sb.append(getEmploi(acte.epouse.conjoint.profession));
        sb.append("./.\n");
        sb.append("Né le ");
        sb.append(DateTimeUtils.dateSpelling(acte.epouse.conjoint.dateNaissance));
        sb.append(" à ");
        sb.append(acte.epouse.conjoint.lieuNaissance);
        sb.append("./.\n");
        sb.append("Fille de ");
        sb.append(acte.epouse.pere.nom.toUpperCase(Locale.FRENCH));
        sb.append(" ");
        sb.append(acte.epouse.pere.prenoms);
        sb.append("./.\n");
        sb.append("et de ");
        sb.append(acte.epouse.mere.nom.toUpperCase(Locale.FRENCH));
        sb.append(" ");
        sb.append(acte.epouse.mere.prenoms);
        sb.append("./.\n");
        sb.append("domicilié à ");
        sb.append(acte.epouse.conjoint.domicile);
        sb.append("./.\n");
        
        return sb.toString();
    }
    
    private String getEmploi(String emploi){
        if(emploi == null || emploi.isBlank()){
            return "Sans-emploi";
        }
        return emploi;
    }
    
    
    public String copieNumeroActeTexte(ActeMariage acte){
        StringBuilder sb = new StringBuilder();
        sb.append("Acte N° ");
        sb.append(acte.numero);
        sb.append(" ");
        sb.append("DU ");
        sb.append(acte.dateMariage.format(DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.FRANCE)));
       
        return sb.toString();
    
    }
    
    
    public String copieTitretexte(ActeMariage acte){
        StringBuilder sb = new StringBuilder();
        sb.append("Pour l'année ");
        sb.append(acte.registre.reference.annee);
        
        return sb.toString();
    }
    
    public String copieIntegraleTexte(ActeMariage acte){
       
        String text = "Le %s, à %s, devant "
        + "Nous: %s, Officier d'état civil, %s de la " 
        + "commune, ont comparu publiquement, à la mairie de %s, "
        + "%s, %s, né le %s, "
        + "à %s, fils de %s, et de %s, "
        + "domicilié à %s, " 
        + "Célibataire.\n\n"
        + "%s, %s, née le %s "
        + "à %s, fille de %s, et de %s, "
        + "domicilié à %s, "
        + "Célibataire.\n\n"
        + "Lesquels ont déclaré sur notre interpellation opter pour le "
        + "regime de %s, et l'un après l'autre vouloir se prendre "
        + "pour époux et nous avons prononcé, au nom de la loi, qu'ils "
        + "sont unis pour le mariage, en présence de: %s, "
        + "%s, domicilié(e) à %s et %s, %s, "
        + "domicilié(e) à %s.\n\n"
        + "Lecture fait, et invité à lire l'acte.\n"
        + "Nous avons signé avec les époux et les témoins.";
        
        
        Formatter formatter = new Formatter(Locale.FRENCH);
        return formatter.format(text, 
                DateTimeUtils.dateSpelling(acte.dateMariage.toLocalDate()),
                DateTimeUtils.hourSpelling(acte.dateMariage),
                acte.officierEtatCivil.nom.toUpperCase(Locale.FRENCH) + " " + acte.officierEtatCivil.prenoms,
                acte.officierEtatCivil.titre,
                localiteService.findActive().getLibelle().toUpperCase(Locale.FRENCH),
                acte.epoux.conjoint.nom.toUpperCase(Locale.FRENCH) + " " + acte.epoux.conjoint.prenoms,
                getEmploi(acte.epoux.conjoint.profession),
                DateTimeUtils.dateSpelling(acte.epoux.conjoint.dateNaissance),
                acte.epoux.conjoint.lieuNaissance,
                acte.epoux.pere.nom.toUpperCase(Locale.FRENCH) + " " + acte.epoux.pere.prenoms,
                acte.epoux.mere.nom.toUpperCase(Locale.FRENCH) + " " + acte.epoux.mere.prenoms,
                acte.epoux.conjoint.domicile,
                acte.epouse.conjoint.nom.toUpperCase(Locale.FRENCH) + " " + acte.epouse.conjoint.prenoms,
                getEmploi(acte.epouse.conjoint.profession),
                DateTimeUtils.dateSpelling(acte.epouse.conjoint.dateNaissance),
                acte.epouse.conjoint.lieuNaissance,
                acte.epouse.pere.nom.toUpperCase(Locale.FRENCH) + " " + acte.epouse.pere.prenoms,
                acte.epouse.mere.nom.toUpperCase(Locale.FRENCH) + " " + acte.epouse.mere.prenoms,
                acte.epouse.conjoint.domicile,
                acte.regime.getLibelle(),
                acte.epoux.temoin.nom.toUpperCase(Locale.FRENCH) + " " + acte.epoux.temoin.prenoms,
                getEmploi(acte.epoux.temoin.profession),
                acte.epoux.temoin.domicile,
                acte.epouse.temoin.nom.toUpperCase(Locale.FRENCH) + " " + acte.epouse.temoin.prenoms,
                getEmploi(acte.epouse.temoin.profession),
                acte.epouse.temoin.domicile).toString();
        
        
    }
    
    public String copieMentionsTextes(ActeMariage acte) {
        StringBuilder sb = new StringBuilder();
        
        sb.append(mentionDivorceService.mentionRecenteTexte(acte));
        sb.append("\n");
        sb.append("\n");
        sb.append(mentionModifRegimeBiensService.mentionRecenteTexte(acte));
        sb.append("\n");
        sb.append("\n");
        sb.append(mentionOrdonRetranscriptionService.mentionRecenteTexte(acte));
        sb.append("\n");
        sb.append("\n");
        sb.append(mentionRectificationService.mentionRecenteTexte(acte));
        sb.append("\n");
        sb.append("\n");
        sb.append(mentionAnnulationService.mentionRecenteTexte(acte));
        
        
        
        return sb.toString();
    
    }
    
    public void patcher(@NotNull ActeMariageEtatDto dto){
       ActeMariageEtat etat = ActeMariageEtat.findById(dto.getId());
       if(etat == null){
           throw new EntityNotFoundException("ActeMariageEtat not found");
       }
       
       etat.extraitTexte = dto.getExtraitTexte();
       etat.copieTexte = dto.getCopieTexte();
       etat.mentionDivorceTexte = dto.getMentionDivorceTexte();
       etat.mentionModifRegimeBiensTexte = dto.getMentionModifRegimeBiensTexte();
       etat.mentionOrdonRetranscriptionTexte = dto.getMentionOrdonRetranscriptionTexte();
       etat.mentionRectificationTexte = dto.getMentionRectificationTexte();
       etat.mentionAnnulationTexte = dto.getMentionAnnulationTexte();
       
   }
    
    public ActeMariageEtatDto mapToDto(@NotNull ActeMariageEtat etat){
        ActeMariageEtatDto dto = new ActeMariageEtatDto();
        
        dto.setId(etat.id);
        dto.setActeMariageID(etat.acteMariage.id);
        dto.setCopieNumeroActeTexte(etat.copieNumeroActeTexte);
        dto.setCopieTexte(etat.copieTexte);
        dto.setExtraitTexte(etat.extraitTexte);
        dto.setMentionDivorceTexte(etat.mentionDivorceTexte);
        dto.setMentionModifRegimeBiensTexte(etat.mentionModifRegimeBiensTexte);
        dto.setMentionOrdonRetranscriptionTexte(etat.mentionOrdonRetranscriptionTexte);
        dto.setMentionRectificationTexte(etat.mentionRectificationTexte);
        dto.setMentionAnnulationTexte(etat.mentionAnnulationTexte);
        dto.setCopieMentionsTextes(etat.copieMentionsTextes);
        dto.setNomsMariesTexte(etat.nomsMariesTexte);
        dto.setNumeroActeTexte(etat.numeroActeTexte);
        dto.setTitreTexte(etat.titreTexte);
        
        return dto;
    
    }
    
   
    
   public String print(String acteID,String logoUri) throws SQLException, JRException, FileNotFoundException{
        
        return doPrint(acteID,logoUri, "/META-INF/resources/report/acte_mariage.jasper");
              
   }
     
    public String printCopie(String acteID,String logoUri) throws SQLException, JRException, FileNotFoundException{
       return doPrint(acteID,logoUri, "/META-INF/resources/report/acte_mariage_ci.jasper");
       
   }
    
   
   private String doPrint(String acteID,String logoURI,String resource) throws SQLException, JRException, FileNotFoundException{
     
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream reportStream = loader.getResourceAsStream(resource);
        log.infof("-- REPORT INPUT: %s", reportStream);
        
        
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("ACTE_MARIAGE_ID", acteID);
        parameters.put("LOGO_URI", logoURI);
        JasperPrint jasperPrint = JasperFillManager.fillReport(reportStream, parameters, defaultDataSource.getConnection());
       
        JRPdfExporter exporter = new JRPdfExporter();
        

        String reportFilePath = filePath(acteID);
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput( new SimpleOutputStreamExporterOutput(reportFilePath));
        
        exporter.exportReport();
        
        return reportFilePath;
   
   }
   
   
   private String filePath(String acteID){
      ActeMariage acte = ActeMariage.findById(acteID);
      if( acte == null){
          throw new EntityNotFoundException("ActeMariage not found");
      }
      String name = acte.numero + "_" + acte.registre.dateOuverture  +"-"+ LocalDateTime.now().toString() + ".pdf";
      
      return "/tmp/" + name.replaceAll(" ", "-");
   }
  
   
   public ActeMariageEtatDto findByActeMariage(ActeMariage acte) {
        PanacheQuery<ActeMariageEtat> pq = ActeMariageEtat.find("acteMariage", acte);
        ActeMariageEtat etat = pq.firstResult();
        if(etat == null){
            throw new EntityNotFoundException("ActeMariageEtat not found");
        }
        
        return mapToDto(etat);
    }
   
}
