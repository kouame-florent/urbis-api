/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.acte.naissance.service;

import com.ibm.icu.text.RuleBasedNumberFormat;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.urbis.acte.naissance.domain.ActeNaissance;
import io.urbis.acte.naissance.domain.ActeNaissanceEtat;
import io.urbis.acte.naissance.dto.ActeNaissanceEtatDto;
import io.urbis.common.domain.Sexe;
import io.urbis.acte.naissance.dto.MentionAdoptionDto;
import io.urbis.acte.naissance.dto.MentionDecesDto;
import io.urbis.acte.naissance.dto.MentionDissolutionMariageDto;
import io.urbis.acte.naissance.dto.MentionLegitimationDto;
import io.urbis.acte.naissance.dto.MentionMariageDto;
import io.urbis.acte.naissance.dto.MentionReconnaissanceDto;
import io.urbis.acte.naissance.dto.MentionRectificationDto;
import io.urbis.param.service.LocaliteService;
import io.urbis.security.service.AuthenticationContext;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Comparator;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
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
    MentionMariageService mentionMariageService;
    
    @Inject
    AuthenticationContext authenticationContext;
    
    @Inject
    LocaliteService localiteService;
    
       
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
            
            etat.copieMentionsTextes = copieMentionsTextes(acte);
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
        sb.append(dateNaissanceEnLettre(acte.enfant.dateNaissance));
        sb.append("\n");
        sb.append("à ");
        sb.append(heureNaissanceEnLettre(acte.enfant.dateNaissance));
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
        StringBuilder sb = new StringBuilder();
        sb.append(dateNaissanceEnLettre(acte.enfant.dateNaissance));
        sb.append(" ");
        sb.append(acte.enfant.lieuNaissance);
        sb.append(", ");
        sb.append("\n");
        sb.append(localiteService.findActive().getType());
        sb.append(" de ");
        sb.append(localiteService.findActive().getLibelle());
        sb.append(", ");
        sb.append("l'enfant ");
        sb.append(acte.enfant.nomComplet);
        sb.append("\n");
        sb.append("de sexe ");
        sb.append(acte.enfant.sexe);
        sb.append(",");
        sb.append("ayant pour père ");
        sb.append(acte.pere.nomComplet);
        sb.append(", ");
        sb.append("né à ");
        sb.append("\n");
        sb.append(acte.pere.lieuNaissance);
        sb.append(", ");
        sb.append(acte.pere.profession);
        sb.append(", ");
        sb.append("domicilié à ");
        sb.append(acte.pere.localite);
        sb.append(" et pour mère ");
        sb.append("\n");
        sb.append(acte.mere.nomComplet);
        sb.append(", ");
        sb.append("né à ");
        sb.append(acte.mere.lieuNaissance);
        sb.append(", ");
        sb.append("\n");
        sb.append(acte.mere.profession);
        sb.append(" domicilié à ");
        sb.append(acte.mere.localite);
        
        sb.append("\n");
        sb.append("\n");
        
        sb.append("Dressé le, par nous, ");
        sb.append(acte.officierEtatCivil.nom);
        sb.append(" ");
        sb.append(acte.officierEtatCivil.prenoms);
        sb.append(", ");
        sb.append("Officier ");
        sb.append("\n");
        sb.append("d'état civil, ");
        sb.append(acte.officierEtatCivil.titre);
        sb.append(" de la commune, après que le déclarant ");
        sb.append("\n");
        sb.append("est été averti des peines sanctionnant les fausses ");
        sb.append("déclarations. ");
        sb.append("\n");
        sb.append("\n");
        sb.append("Lecture faite et le déclarant invité à lire l'acte.");
        sb.append(" Nous avons signé avec le déclarant.");
        
        return sb.toString();
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
        sb.append(mentionDecesService.mentionRecenteTexte(acte));
        
        
        return sb.toString();
    
    }
  

    public String dateNaissanceEnLettre(LocalDateTime localDateTime){
      
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
    
    
    public String heureNaissanceEnLettre(LocalDateTime localDateTime){
        
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
   
}
