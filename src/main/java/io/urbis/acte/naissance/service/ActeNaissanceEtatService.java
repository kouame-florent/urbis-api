/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.acte.naissance.service;

import com.ibm.icu.text.RuleBasedNumberFormat;
import io.agroal.api.AgroalDataSource;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.urbis.acte.naissance.domain.ActeNaissance;
import io.urbis.acte.naissance.domain.ActeNaissanceEtat;
import io.urbis.acte.naissance.dto.ActeNaissanceEtatDto;
import io.urbis.common.domain.Sexe;
import io.urbis.acte.naissance.dto.MentionAdoptionDto;
import io.urbis.acte.naissance.dto.MentionAnnulationDto;
import io.urbis.acte.naissance.dto.MentionDecesDto;
import io.urbis.acte.naissance.dto.MentionDissolutionMariageDto;
import io.urbis.acte.naissance.dto.MentionLegitimationDto;
import io.urbis.acte.naissance.dto.MentionMariageDto;
import io.urbis.acte.naissance.dto.MentionReconnaissanceDto;
import io.urbis.acte.naissance.dto.MentionRectificationDto;
import io.urbis.param.service.LocaliteService;
import io.urbis.security.service.AuthenticationContext;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Comparator;
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
public class ActeNaissanceEtatService {
  
    @Inject
    Logger log;
    
    @Inject
    MentionAdoptionService mentionAdoptionService;
    
    @Inject
    MentionDecesService mentionDecesService;
    
    @Inject
    MentionDissolutionMariageService mentionDissolutionMariageService;
    
    @Inject
    MentionLegitimationService mentionLegitimationService;
    
    @Inject
    MentionReconnaissanceService mentionReconnaissanceService;
    
    @Inject
    MentionRectificationService mentionRectificationService;
    
    @Inject
    MentionAnnulationService  mentionAnnulationService;
    
    @Inject
    MentionMariageService mentionMariageService;
    
    @Inject
    AuthenticationContext authenticationContext;
    
    @Inject
    LocaliteService localiteService;
    
    @Inject
    AgroalDataSource defaultDataSource;
    
       
    public ActeNaissanceEtatDto findByActeNaissance(ActeNaissance acteNaissance) throws SQLException, IOException{
        PanacheQuery<ActeNaissanceEtat> pq = ActeNaissanceEtat.find("acteNaissance", acteNaissance);
        ActeNaissanceEtat etat = pq.firstResult();
        if(etat == null){
            throw new EntityNotFoundException("ActeNaissanceEtat not found");
        }
        
        return mapToDto(etat);
    }
    
    public void creer(String acteID) throws SQLException{
        ActeNaissanceEtat etat = new ActeNaissanceEtat();
        ActeNaissance acte = ActeNaissance.findById(acteID);
        etat.acteNaissance = acte;
        etat.updatedBy = authenticationContext.userLogin();
        etat.nomCompletTexte = acte.enfant.nom + " " + acte.enfant.prenoms;
        
        //get mentions mariage
        Set<MentionMariageDto> mariages = mentionMariageService.findByActeNaissance(acteID);
        Optional<MentionMariageDto> optMariage = mariages.stream().max(Comparator.comparing(m -> m.getDateDressage()));
        optMariage.ifPresent(mMariage -> { 
            etat.conjointNomComplet = mMariage.getConjointNom() + " " + mMariage.getConjointPrenoms();
            etat.dateMariage = mMariage.getDate();
            etat.lieuMariage = mMariage.getLieu();
            etat.mentionMarigeTexte = mMariage.getDecision();
           
        });
        
        
        Set<MentionAdoptionDto> adoptions = mentionAdoptionService.findByActeNaissance(acteID);
        Optional<MentionAdoptionDto> optAdoption = adoptions.stream().max(Comparator.comparing(m -> m.getDateDressage()));
        optAdoption.ifPresent(mAdoption -> { 
            etat.mentionAdoptionTexte = mAdoption.getDecision();
           
        });
      
        
        Set<MentionDissolutionMariageDto> dissolutions = mentionDissolutionMariageService.findByActeNaissance(acteID);
        Optional<MentionDissolutionMariageDto> optDiss = dissolutions.stream().max(Comparator.comparing(m -> m.getDateDressage()));
        optDiss.ifPresent(mDiss -> { 
            etat.mentionDissolutionMariageDecisionDate = mDiss.getDateJugement();
            etat.mentionDissolutionMarigeTexte = mDiss.getDecision();
            
        });
      
        
        Set<MentionDecesDto> deces = mentionDecesService.findByActeNaissance(acteID);
        Optional<MentionDecesDto> optDeces = deces.stream().max(Comparator.comparing(m -> m.getDateDressage()));
        optDeces.ifPresent(mDeces -> { 
            etat.dateDeces = mDeces.getDate();
            etat.lieuDeces = mDeces.getLieu();
            etat.mentionDecesTexte = mDeces.getDecision();
           
     
        });
      
        
        Set<MentionLegitimationDto> ligitimations = mentionLegitimationService.findByActeNaissance(acteID);
        Optional<MentionLegitimationDto> optLegitimation = ligitimations.stream().max(Comparator.comparing(m -> m.getDateDressage()));
        optLegitimation.ifPresent(mLegitimation -> { 
            etat.mentionLegitimationTexte = mLegitimation.getDecision();
       
        });
        
        
        Set<MentionReconnaissanceDto> reconnaissances = mentionReconnaissanceService.findByActeNaissance(acteID);
        Optional<MentionReconnaissanceDto> optReconnaissance = reconnaissances.stream().max(Comparator.comparing(m -> m.getDateDressage()));
        optReconnaissance.ifPresent(mReconnaissance -> { 
           
            etat.mentionReconnaissanceTexte = mReconnaissance.getDecision();
            
        });
     
        
        Set<MentionRectificationDto> rectifications = mentionRectificationService.findByActeNaissance(acteID);
        Optional<MentionRectificationDto> optRectification = rectifications.stream().max(Comparator.comparing(m -> m.getDateDressage()));
        optRectification.ifPresent(mRectification -> { 
            etat.mentionRectificationTexte = mRectification.getDecision();
           
        });
        
        Set<MentionAnnulationDto> annulations = mentionAnnulationService.findByActeNaissance(acteID);
        Optional<MentionAnnulationDto> optAnnulation = annulations.stream().max(Comparator.comparing(m -> m.getDateDressage()));
        optAnnulation.ifPresent(mAnnulation -> { 
            etat.mentionAnnulationTexte = mAnnulation.getDecision();
           
        });
        
        
        etat.numeroActeTexte = numeroActeTexte(acte);
        etat.titreTexte = titreTexte(acte);
        etat.extraitTexte = extraitTexte(acte);
        etat.copieNumeroActeTexte = copieNumeroActeTexte(acte);
        etat.copieTitreTexte = copieTitretexte(acte);
        etat.copieTexte = copieIntegraleTexte(acte);
        etat.copieMentionsTextes = copieMentionsTextes(acte);
        
        etat.persist();
        
        
    }
    
    public void modifier(String acteID) throws SQLException{
        ActeNaissance acte = ActeNaissance.findById(acteID);
        PanacheQuery<ActeNaissanceEtat> query = ActeNaissanceEtat.find("acteNaissance", acte);
        ActeNaissanceEtat etat = query.singleResult();
        if(etat != null){
            
            etat.updatedBy = authenticationContext.userLogin();
            
            etat.nomCompletTexte = acte.enfant.nom + " " + acte.enfant.prenoms;
            etat.titreTexte = titreTexte(acte);
            etat.numeroActeTexte = numeroActeTexte(acte);
            etat.extraitTexte = extraitTexte(acte);
            etat.copieNumeroActeTexte = copieNumeroActeTexte(acte);
            etat.copieTitreTexte = copieTitretexte(acte);
            etat.copieTexte = copieIntegraleTexte(acte);
            etat.copieMentionsTextes = copieMentionsTextes(acte);
            
            etat.mentionAdoptionTexte = mentionAdoptionService.mentionRecenteTexte(acte);
            
            etat.mentionDecesTexte = mentionDecesService.mentionRecenteTexte(acte);
            
            etat.mentionDissolutionMarigeTexte = mentionDissolutionMariageService.mentionRecenteTexte(acte);
            
            etat.mentionLegitimationTexte = mentionLegitimationService.mentionRecenteTexte(acte);
            
            etat.mentionMarigeTexte = mentionMariageService.mentionRecenteTexte(acte);
            
            etat.mentionReconnaissanceTexte = mentionReconnaissanceService.mentionRecenteTexte(acte);
            
            etat.mentionRectificationTexte = mentionRectificationService.mentionRecenteTexte(acte);
            
            etat.mentionAnnulationTexte = mentionAnnulationService.mentionRecenteTexte(acte);
            
            
        }
    
    }
    
   @Transactional
   public void patcher(@NotNull ActeNaissanceEtatDto dto){
       ActeNaissanceEtat etat = ActeNaissanceEtat.findById(dto.getId());
       if(etat == null){
           throw new EntityNotFoundException("ActeNaissanceEtat not found");
       }
       
       etat.extraitTexte = dto.getExtraitTexte();
       etat.copieTexte = dto.getCopieTexte();
       etat.mentionAdoptionTexte = dto.getMentionAdoptionTexte();
       etat.mentionDecesTexte = dto.getMentionDecesTexte();
       etat.mentionDissolutionMarigeTexte = dto.getMentionDissolutionMarigeTexte();
       etat.mentionLegitimationTexte = dto.getMentionLegitimationTexte();
       etat.mentionMarigeTexte = dto.getMentionMarigeTexte();
       etat.mentionReconnaissanceTexte = dto.getMentionReconnaissanceTexte();
       etat.mentionRectificationTexte = dto.getMentionRectificationTexte();
       etat.mentionAnnulationTexte = dto.getMentionAnnulationTexte();
   }
   
    public ActeNaissanceEtatDto mapToDto(@NotNull ActeNaissanceEtat etat) throws SQLException, IOException{
        
        ActeNaissanceEtatDto dto = new ActeNaissanceEtatDto();
        
        dto.setId(etat.id);
        dto.setActeNaissanceID(etat.acteNaissance.id);
        dto.setConjointNomComplet(etat.conjointNomComplet);
        dto.setCopieMentionsTextes(etat.copieMentionsTextes);
        dto.setCopieNumeroActeTexte(etat.copieNumeroActeTexte);
        dto.setCopieTexte(etat.copieTexte);
        dto.setCopieTitreTexte(etat.copieTitreTexte);
        dto.setDateDeces(etat.dateDeces);
        dto.setDateMariage(etat.dateMariage);
        dto.setExtraitTexte(etat.extraitTexte);
        dto.setLieuDeces(etat.lieuDeces);
        dto.setLieuMariage(etat.lieuMariage);
        dto.setMentionAdoptionTexte(etat.mentionAdoptionTexte);
        dto.setMentionDecesTexte(etat.mentionDecesTexte);
        dto.setMentionDissolutionMariageDecisionDate(etat.mentionDissolutionMariageDecisionDate);
        dto.setMentionDissolutionMarigeTexte(etat.mentionDissolutionMarigeTexte);
        dto.setMentionLegitimationTexte(etat.mentionLegitimationTexte);
        dto.setMentionMarigeTexte(etat.mentionMarigeTexte);
        dto.setMentionReconnaissanceTexte(etat.mentionReconnaissanceTexte);
        dto.setMentionRectificationTexte(etat.mentionRectificationTexte);
        dto.setMentionAnnulationTexte(etat.mentionAnnulationTexte);
        dto.setNomCompletTexte(etat.nomCompletTexte);
        dto.setNumeroActeTexte(etat.numeroActeTexte);
        dto.setTitreTexte(etat.titreTexte);
        
    
        return dto;
    }
    
    public String titreTexte(ActeNaissance acte){
        StringBuilder sb = new StringBuilder();
        sb.append("Pour l'année ");
        sb.append(acte.registre.reference.annee);
        
        return sb.toString();
    }
    
    public String extraitTexte(ActeNaissance acte){
        StringBuilder sb = new StringBuilder();
        sb.append("Le ");
        sb.append(ActeNaissanceEtatService.this.dateEnLettre(acte.enfant.dateNaissance));
        sb.append("\n");
        sb.append("à ");
        sb.append(heureEnLettre(acte.enfant.dateNaissance));
        sb.append("\n");
        if(acte.enfant.sexe == Sexe.MASCULIN){
            sb.append("est né ");
        }else{
            sb.append("est née ");
        }
        
        sb.append(acte.enfant.nomComplet);
        sb.append("\n");
        sb.append(acte.enfant.lieuNaissance);
        sb.append("\n");
        sb.append("\n");
        if(acte.enfant.sexe == Sexe.MASCULIN){
            sb.append("fils de ");
        }else{
            sb.append("fille de ");
        }
        sb.append(acte.pere.nomComplet);
        sb.append("\n");
        sb.append("et de ");
        sb.append(acte.mere.nomComplet);
        
        return sb.toString();
    }
    
    public String copieTitretexte(ActeNaissance acte){
        StringBuilder sb = new StringBuilder();
        sb.append("Pour l'année ");
        sb.append(acte.registre.reference.annee);
        
        return sb.toString();
    }
    
    public String copieIntegraleTexte(ActeNaissance acte){
        
        String text = """
                      Le %s 
                      à %s
                      %s %s,
                      %s de %s, l'enfant %s 
                      de sexe %s, ayant pour père %s,
                      né le %s
                      à %s, de nationnalité %s,
                      %s, domicilié à %s et pour mère
                      %s, né le %s
                      à %s, de nationnalité %s, %s
                      domiciliée à %s.
                      
                      Dressé le %s,
                      à %s, sur déclaration
                      du %s, par Nous, %s, Officier d'état civil,
                      %s de la commune, après que le déclarant
                      est été averti des peines sanctionnant les
                      fausses déclarations.
                      
                      Lecture faite et le déclarant invité à lire l'acte.
                      Nous avons signé avec le déclarant.
                      """;
        
        
        var fmtTxt = String.format(text,
                dateEnLettre(acte.enfant.dateNaissance),
                heureEnLettre(acte.enfant.dateNaissance),
                estNe(acte),
                acte.enfant.lieuNaissance,
                localiteService.findActive().getType(),
                localiteService.findActive().getLibelle(),
                acte.enfant.nomComplet,
                acte.enfant.sexe,
                
                acte.pere.nomComplet,
                dateEnLettre(acte.pere.dateNaissance),
                acte.pere.lieuNaissance,
                acte.pere.nationalite,
                acte.pere.profession,
                acte.pere.localite,
                
                                
                acte.mere.nomComplet,
                dateEnLettre(acte.mere.dateNaissance),
                acte.mere.lieuNaissance,
                acte.mere.nationalite,
                acte.mere.profession,
                acte.mere.localite,
                
                dateEnLettre(acte.dateDressage),
                heureEnLettre(acte.dateDressage),
                acte.declarant.lien,
                
                acte.officierEtatCivil.nom + " " + acte.officierEtatCivil.prenoms,
                acte.officierEtatCivil.titre);
         
        
        
        return fmtTxt;
        
    }
    
    public String numeroActeTexte(ActeNaissance acte){
        StringBuilder sb = new StringBuilder();
        sb.append("N° ");
        sb.append(acte.numero);
        sb.append(" ");
        sb.append("DU ");
        sb.append(acte.dateDressage.format(DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.FRANCE)));
        sb.append(" ");
        sb.append("DU REGISTRE");
        
        return sb.toString();
    
    }
    
    public String copieNumeroActeTexte(ActeNaissance acte){
        StringBuilder sb = new StringBuilder();
        sb.append("Acte N° ");
        sb.append(acte.numero);
        sb.append(" ");
        sb.append("DU ");
        sb.append(acte.dateDressage.format(DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.FRANCE)));
       
        return sb.toString();
    
    }
    
    public String copieMentionsTextes(ActeNaissance acte) throws SQLException{
        StringBuilder sb = new StringBuilder();
        
        sb.append(mentionMariageService.mentionRecenteTexte(acte));
        sb.append("\n");
        sb.append("\n");
        sb.append(mentionAdoptionService.mentionRecenteTexte(acte));
        sb.append("\n");
        sb.append("\n");
        sb.append(mentionDissolutionMariageService.mentionRecenteTexte(acte));
        sb.append("\n");
        sb.append("\n");
        sb.append(mentionLegitimationService.mentionRecenteTexte(acte));
        sb.append("\n");
        sb.append("\n");
        sb.append(mentionReconnaissanceService.mentionRecenteTexte(acte));
        sb.append("\n");
        sb.append("\n");
        sb.append(mentionRectificationService.mentionRecenteTexte(acte));
        sb.append("\n");
        sb.append("\n");
        sb.append(mentionAnnulationService.mentionRecenteTexte(acte));
        sb.append("\n");
        sb.append("\n");
        sb.append(mentionDecesService.mentionRecenteTexte(acte));
        
        
        return sb.toString();
    
    }
  

    public String dateEnLettre(LocalDateTime localDateTime){
      
        int dayOfMonth = localDateTime.getDayOfMonth();
        String month = localDateTime.getMonth().getDisplayName(TextStyle.FULL, new Locale("fr","FR"));
        int year = localDateTime.getYear();
        
       String laDate = "";
        
        if(dayOfMonth == 1){
            RuleBasedNumberFormat ruleBasedNumberFormat = new RuleBasedNumberFormat( new Locale("fr", "FR"),
                RuleBasedNumberFormat.SPELLOUT );  
            laDate = ruleBasedNumberFormat.format(dayOfMonth,"%spellout-ordinal-masculine") + " " 
                 + month + " " + ruleBasedNumberFormat.format(year);
        
        }else{
            RuleBasedNumberFormat ruleBasedNumberFormat = new RuleBasedNumberFormat( new Locale("fr", "FR"),
                RuleBasedNumberFormat.SPELLOUT );  
            laDate = ruleBasedNumberFormat.format(dayOfMonth) + " " 
                 + month + " " + ruleBasedNumberFormat.format(year);
        }
      
        
        return laDate;
        
    }
    
    
     public String dateEnLettre(LocalDate localDate){
      
        int dayOfMonth = localDate.getDayOfMonth();
        String month = localDate.getMonth().getDisplayName(TextStyle.FULL, new Locale("fr","FR"));
        int year = localDate.getYear();
        
       String laDate = "";
        
        if(dayOfMonth == 1){
            RuleBasedNumberFormat ruleBasedNumberFormat = new RuleBasedNumberFormat( new Locale("fr", "FR"),
                RuleBasedNumberFormat.SPELLOUT );  
            laDate = ruleBasedNumberFormat.format(dayOfMonth,"%spellout-ordinal-masculine") + " " 
                 + month + " " + ruleBasedNumberFormat.format(year);
        
        }else{
            RuleBasedNumberFormat ruleBasedNumberFormat = new RuleBasedNumberFormat( new Locale("fr", "FR"),
                RuleBasedNumberFormat.SPELLOUT );  
            laDate = ruleBasedNumberFormat.format(dayOfMonth) + " " 
                 + month + " " + ruleBasedNumberFormat.format(year);
        }
      
        
        return laDate;
        
    }
    
    
    public String estNe(ActeNaissance acte){
        if(acte.enfant.sexe == Sexe.MASCULIN){
            return "est né";
        }else{
            return "est née";
        }
    
    }
    
    
    public String heureEnLettre(LocalDateTime localDateTime){
        
        int hour = localDateTime.getHour();
        int minute = localDateTime.getMinute();
        String temps = "";
        
        if(hour != 0 && minute != 0){
            RuleBasedNumberFormat ruleBasedNumberFormat = new RuleBasedNumberFormat(new Locale("fr", "FR"),
                RuleBasedNumberFormat.SPELLOUT );    
            
            if(hour > 1){
                temps = ruleBasedNumberFormat.format(hour,"%spellout-cardinal-feminine") + " " 
                    + "heures" + " ";
                    
            }else{
                temps = ruleBasedNumberFormat.format(hour,"%spellout-cardinal-feminine") + " " 
                    + "heure" + " ";
            }
            
            if(minute > 1 ){
                temps += ruleBasedNumberFormat.format(minute,"%spellout-cardinal-feminine")
                        + " " + "minutes";
            }else{
                temps += ruleBasedNumberFormat.format(minute,"%spellout-cardinal-feminine")
                        + " " + "minute";
            }
             return temps;
        }
        return temps;
    }
    
     public String print(String acteID) throws SQLException, JRException, FileNotFoundException{
        
        return doPrint(acteID, "/META-INF/resources/report/acte_naissance.jasper");
              
   }
    
    
   private String doPrint(String acteID,String resource) throws SQLException, JRException, FileNotFoundException{
     
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream reportStream = loader.getResourceAsStream(resource);
        log.infof("-- REPORT INPUT: %s", reportStream);
        
        
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("ACTE_NAISSANCE_ID", acteID);
        JasperPrint jasperPrint = JasperFillManager.fillReport(reportStream, parameters, defaultDataSource.getConnection());
       
        JRPdfExporter exporter = new JRPdfExporter();
        

        String reportFilePath = filePath(acteID);
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput( new SimpleOutputStreamExporterOutput(reportFilePath));
        
        exporter.exportReport();
        
        return reportFilePath;
   
   }
   
   private String filePath(String acteID){
       ActeNaissance acte = ActeNaissance.findById(acteID);
      if( acte == null){
          throw new EntityNotFoundException("ActeNaissance not found");
      }
      String name = acte.enfant.nom + " " + acte.enfant.prenoms +"-"+ LocalDateTime.now().toString() + ".pdf";
      
      return "/tmp/" + name.replaceAll(" ", "-");
   }
   
}
