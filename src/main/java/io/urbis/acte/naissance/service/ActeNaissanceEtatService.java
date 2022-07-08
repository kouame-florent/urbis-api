/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.acte.naissance.service;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.urbis.acte.naissance.domain.ActeNaissance;
import io.urbis.acte.naissance.domain.ActeNaissanceEtat;
import io.urbis.acte.naissance.dto.ActeNaissanceEtatDto;
import io.urbis.common.util.TextUtils;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
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
    
       
    public ActeNaissanceEtatDto findByActeNaissance(ActeNaissance acteNaissance) throws SQLException, IOException{
        PanacheQuery<ActeNaissanceEtat> pq = ActeNaissanceEtat.find("acteNaissance", acteNaissance);
        ActeNaissanceEtat etat = pq.firstResult();
        if(etat == null){
            throw new EntityNotFoundException("ActeNaissanceEtat not found");
        }
        
        return mapToDto(etat);
    }
    
   @Transactional
   public void modifier(@NotNull ActeNaissanceEtatDto dto){
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
   
}
