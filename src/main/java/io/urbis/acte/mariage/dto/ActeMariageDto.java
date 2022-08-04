/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.acte.mariage.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 *
 * @author florent
 */

public class ActeMariageDto {
    
    private LocalDateTime created; 
    private LocalDateTime updated; 
    
    @NotBlank
    private String operation;
   
    @NotBlank
    private String id;
    
    @NotBlank
    private String registreID;
    
    @Min(1)
    private int numero;
    
    @NotNull
    private LocalDateTime dateMariage;
    
    @NotBlank
    private String lieuMariage;
    
    @NotBlank
    private String regime;
    
    private int registreAnnee;
    private int registreNumero;
    
    private String statut;
   
    @NotBlank
    private String officierEtatCivilID;
    private String officierEtatCivilNom;
    private String officierEtatCivilPrenoms;
    private String officierEtatCivilTitre;
    
    private String epouxConjointNom;
    private String epouxConjointPrenoms;
    private String epouxConjointProfession;
    private String epouxConjointLieuNaissance;
    private LocalDate epouxConjointDateNaissance;
    private String epouxConjointDomicile;
    private String epouxConjointSituationMatrimoniale;
    
    
    private String epouxPereNom;
    private String epouxPerePrenoms;
    private String epouxPereProfession;
    private String epouxPereDomicile;
    private boolean epouxPereDecede;
    private String epouxPereLieuDeces;
    private LocalDate epouxPereDateDeces;
    
    private String epouxMereNom;
    private String epouxMerePrenoms;
    private String epouxMereProfession;
    private String epouxMereDomicile;
    private boolean epouxMereDecede;
    private String epouxMereLieuDeces;
    private LocalDate epouxMereDateDeces;
  
    
    private String epouxTemoinNom;
    private String epouxTemoinPrenoms;
    private String epouxTemoinProfession;
    private String epouxTemoinDomicile;
    private LocalDate epouxTemoinDateNaissance;
    
    private String epouseConjointNom;
    private String epouseConjointPrenoms;
    private String epouseConjointProfession;
    private String epouseConjointLieuNaissance;
    private LocalDate epouseConjointDateNaissance;
    private String epouseConjointDomicile;
    private String epouseConjointSituationMatrimoniale;
    
    private String epousePereNom;
    private String epousePerePrenoms;
    private String epousePereProfession;
    private String epousePereDomicile;
    private boolean epousePereDecede;
    private String epousePereLieuDeces;
    private LocalDate epousePereDateDeces;
  
    
    private String epouseMereNom;
    private String epouseMerePrenoms;
    private String epouseMereProfession;
    private String epouseMereDomicile;
    private boolean epouseMereDecede;
    private String epouseMereLieuDeces;
    private LocalDate epouseMereDateDeces;
  
    
    private String epouseTemoinNom;
    private String epouseTemoinPrenoms;
    private String epouseTemoinProfession;
    private String epouseTemoinDomicile;
    private LocalDate epouseTemoinDateNaissance;
    
    private Set<MentionDivorceDto> mentionDivorceDtos = new HashSet<>();
    private Set<MentionModifRegimeBiensDto> mentionModifRegimeBiensDtos = new HashSet<>();
    private Set<MentionOrdonRetranscriptionDto> mentionOrdonRetranscriptionDtos = new HashSet<>();

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRegistreID() {
        return registreID;
    }

    public void setRegistreID(String registreID) {
        this.registreID = registreID;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public LocalDateTime getDateMariage() {
        return dateMariage;
    }

    public void setDateMariage(LocalDateTime dateMariage) {
        this.dateMariage = dateMariage;
    }

    public String getLieuMariage() {
        return lieuMariage;
    }

    public void setLieuMariage(String lieuMariage) {
        this.lieuMariage = lieuMariage;
    }

    public String getRegime() {
        return regime;
    }

    public void setRegime(String regime) {
        this.regime = regime;
    }

    public int getRegistreAnnee() {
        return registreAnnee;
    }

    public void setRegistreAnnee(int registreAnnee) {
        this.registreAnnee = registreAnnee;
    }

    public int getRegistreNumero() {
        return registreNumero;
    }

    public void setRegistreNumero(int registreNumero) {
        this.registreNumero = registreNumero;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getOfficierEtatCivilID() {
        return officierEtatCivilID;
    }

    public void setOfficierEtatCivilID(String officierEtatCivilID) {
        this.officierEtatCivilID = officierEtatCivilID;
    }

    public String getOfficierEtatCivilNom() {
        return officierEtatCivilNom;
    }

    public void setOfficierEtatCivilNom(String officierEtatCivilNom) {
        this.officierEtatCivilNom = officierEtatCivilNom;
    }

    public String getOfficierEtatCivilPrenoms() {
        return officierEtatCivilPrenoms;
    }

    public void setOfficierEtatCivilPrenoms(String officierEtatCivilPrenoms) {
        this.officierEtatCivilPrenoms = officierEtatCivilPrenoms;
    }

    public String getOfficierEtatCivilTitre() {
        return officierEtatCivilTitre;
    }

    public void setOfficierEtatCivilTitre(String officierEtatCivilTitre) {
        this.officierEtatCivilTitre = officierEtatCivilTitre;
    }

    public String getEpouxConjointNom() {
        return epouxConjointNom;
    }

    public void setEpouxConjointNom(String epouxConjointNom) {
        this.epouxConjointNom = epouxConjointNom;
    }

    public String getEpouxConjointPrenoms() {
        return epouxConjointPrenoms;
    }

    public void setEpouxConjointPrenoms(String epouxConjointPrenoms) {
        this.epouxConjointPrenoms = epouxConjointPrenoms;
    }

    public String getEpouxConjointProfession() {
        return epouxConjointProfession;
    }

    public void setEpouxConjointProfession(String epouxConjointProfession) {
        this.epouxConjointProfession = epouxConjointProfession;
    }

    public String getEpouxConjointLieuNaissance() {
        return epouxConjointLieuNaissance;
    }

    public void setEpouxConjointLieuNaissance(String epouxConjointLieuNaissance) {
        this.epouxConjointLieuNaissance = epouxConjointLieuNaissance;
    }

    public LocalDate getEpouxConjointDateNaissance() {
        return epouxConjointDateNaissance;
    }

    public void setEpouxConjointDateNaissance(LocalDate epouxConjointDateNaissance) {
        this.epouxConjointDateNaissance = epouxConjointDateNaissance;
    }

    public String getEpouxConjointDomicile() {
        return epouxConjointDomicile;
    }

    public void setEpouxConjointDomicile(String epouxConjointDomicile) {
        this.epouxConjointDomicile = epouxConjointDomicile;
    }

    public String getEpouxConjointSituationMatrimoniale() {
        return epouxConjointSituationMatrimoniale;
    }

    public void setEpouxConjointSituationMatrimoniale(String epouxConjointSituationMatrimoniale) {
        this.epouxConjointSituationMatrimoniale = epouxConjointSituationMatrimoniale;
    }

    public String getEpouxPereNom() {
        return epouxPereNom;
    }

    public void setEpouxPereNom(String epouxPereNom) {
        this.epouxPereNom = epouxPereNom;
    }

    public String getEpouxPerePrenoms() {
        return epouxPerePrenoms;
    }

    public void setEpouxPerePrenoms(String epouxPerePrenoms) {
        this.epouxPerePrenoms = epouxPerePrenoms;
    }

    public String getEpouxPereProfession() {
        return epouxPereProfession;
    }

    public void setEpouxPereProfession(String epouxPereProfession) {
        this.epouxPereProfession = epouxPereProfession;
    }

    public String getEpouxPereDomicile() {
        return epouxPereDomicile;
    }

    public void setEpouxPereDomicile(String epouxPereDomicile) {
        this.epouxPereDomicile = epouxPereDomicile;
    }

    public boolean isEpouxPereDecede() {
        return epouxPereDecede;
    }

    public void setEpouxPereDecede(boolean epouxPereDecede) {
        this.epouxPereDecede = epouxPereDecede;
    }

    public String getEpouxPereLieuDeces() {
        return epouxPereLieuDeces;
    }

    public void setEpouxPereLieuDeces(String epouxPereLieuDeces) {
        this.epouxPereLieuDeces = epouxPereLieuDeces;
    }

    public LocalDate getEpouxPereDateDeces() {
        return epouxPereDateDeces;
    }

    public void setEpouxPereDateDeces(LocalDate epouxPereDateDeces) {
        this.epouxPereDateDeces = epouxPereDateDeces;
    }

    public String getEpouxMereNom() {
        return epouxMereNom;
    }

    public void setEpouxMereNom(String epouxMereNom) {
        this.epouxMereNom = epouxMereNom;
    }

    public String getEpouxMerePrenoms() {
        return epouxMerePrenoms;
    }

    public void setEpouxMerePrenoms(String epouxMerePrenoms) {
        this.epouxMerePrenoms = epouxMerePrenoms;
    }

    public String getEpouxMereProfession() {
        return epouxMereProfession;
    }

    public void setEpouxMereProfession(String epouxMereProfession) {
        this.epouxMereProfession = epouxMereProfession;
    }

    public String getEpouxMereDomicile() {
        return epouxMereDomicile;
    }

    public void setEpouxMereDomicile(String epouxMereDomicile) {
        this.epouxMereDomicile = epouxMereDomicile;
    }

    public boolean isEpouxMereDecede() {
        return epouxMereDecede;
    }

    public void setEpouxMereDecede(boolean epouxMereDecede) {
        this.epouxMereDecede = epouxMereDecede;
    }

    public String getEpouxMereLieuDeces() {
        return epouxMereLieuDeces;
    }

    public void setEpouxMereLieuDeces(String epouxMereLieuDeces) {
        this.epouxMereLieuDeces = epouxMereLieuDeces;
    }

    public LocalDate getEpouxMereDateDeces() {
        return epouxMereDateDeces;
    }

    public void setEpouxMereDateDeces(LocalDate epouxMereDateDeces) {
        this.epouxMereDateDeces = epouxMereDateDeces;
    }

    public String getEpouxTemoinNom() {
        return epouxTemoinNom;
    }

    public void setEpouxTemoinNom(String epouxTemoinNom) {
        this.epouxTemoinNom = epouxTemoinNom;
    }

    public String getEpouxTemoinPrenoms() {
        return epouxTemoinPrenoms;
    }

    public void setEpouxTemoinPrenoms(String epouxTemoinPrenoms) {
        this.epouxTemoinPrenoms = epouxTemoinPrenoms;
    }

    public String getEpouxTemoinProfession() {
        return epouxTemoinProfession;
    }

    public void setEpouxTemoinProfession(String epouxTemoinProfession) {
        this.epouxTemoinProfession = epouxTemoinProfession;
    }

    public String getEpouxTemoinDomicile() {
        return epouxTemoinDomicile;
    }

    public void setEpouxTemoinDomicile(String epouxTemoinDomicile) {
        this.epouxTemoinDomicile = epouxTemoinDomicile;
    }

    public LocalDate getEpouxTemoinDateNaissance() {
        return epouxTemoinDateNaissance;
    }

    public void setEpouxTemoinDateNaissance(LocalDate epouxTemoinDateNaissance) {
        this.epouxTemoinDateNaissance = epouxTemoinDateNaissance;
    }

    public String getEpouseConjointNom() {
        return epouseConjointNom;
    }

    public void setEpouseConjointNom(String epouseConjointNom) {
        this.epouseConjointNom = epouseConjointNom;
    }

    public String getEpouseConjointPrenoms() {
        return epouseConjointPrenoms;
    }

    public void setEpouseConjointPrenoms(String epouseConjointPrenoms) {
        this.epouseConjointPrenoms = epouseConjointPrenoms;
    }

    public String getEpouseConjointProfession() {
        return epouseConjointProfession;
    }

    public void setEpouseConjointProfession(String epouseConjointProfession) {
        this.epouseConjointProfession = epouseConjointProfession;
    }

    public String getEpouseConjointLieuNaissance() {
        return epouseConjointLieuNaissance;
    }

    public void setEpouseConjointLieuNaissance(String epouseConjointLieuNaissance) {
        this.epouseConjointLieuNaissance = epouseConjointLieuNaissance;
    }

    public LocalDate getEpouseConjointDateNaissance() {
        return epouseConjointDateNaissance;
    }

    public void setEpouseConjointDateNaissance(LocalDate epouseConjointDateNaissance) {
        this.epouseConjointDateNaissance = epouseConjointDateNaissance;
    }

    public String getEpouseConjointDomicile() {
        return epouseConjointDomicile;
    }

    public void setEpouseConjointDomicile(String epouseConjointDomicile) {
        this.epouseConjointDomicile = epouseConjointDomicile;
    }

    public String getEpouseConjointSituationMatrimoniale() {
        return epouseConjointSituationMatrimoniale;
    }

    public void setEpouseConjointSituationMatrimoniale(String epouseConjointSituationMatrimoniale) {
        this.epouseConjointSituationMatrimoniale = epouseConjointSituationMatrimoniale;
    }

    public String getEpousePereNom() {
        return epousePereNom;
    }

    public void setEpousePereNom(String epousePereNom) {
        this.epousePereNom = epousePereNom;
    }

    public String getEpousePerePrenoms() {
        return epousePerePrenoms;
    }

    public void setEpousePerePrenoms(String epousePerePrenoms) {
        this.epousePerePrenoms = epousePerePrenoms;
    }

    public String getEpousePereProfession() {
        return epousePereProfession;
    }

    public void setEpousePereProfession(String epousePereProfession) {
        this.epousePereProfession = epousePereProfession;
    }

    public String getEpousePereDomicile() {
        return epousePereDomicile;
    }

    public void setEpousePereDomicile(String epousePereDomicile) {
        this.epousePereDomicile = epousePereDomicile;
    }

    public boolean isEpousePereDecede() {
        return epousePereDecede;
    }

    public void setEpousePereDecede(boolean epousePereDecede) {
        this.epousePereDecede = epousePereDecede;
    }

    public String getEpousePereLieuDeces() {
        return epousePereLieuDeces;
    }

    public void setEpousePereLieuDeces(String epousePereLieuDeces) {
        this.epousePereLieuDeces = epousePereLieuDeces;
    }

    public LocalDate getEpousePereDateDeces() {
        return epousePereDateDeces;
    }

    public void setEpousePereDateDeces(LocalDate epousePereDateDeces) {
        this.epousePereDateDeces = epousePereDateDeces;
    }

    public String getEpouseMereNom() {
        return epouseMereNom;
    }

    public void setEpouseMereNom(String epouseMereNom) {
        this.epouseMereNom = epouseMereNom;
    }

    public String getEpouseMerePrenoms() {
        return epouseMerePrenoms;
    }

    public void setEpouseMerePrenoms(String epouseMerePrenoms) {
        this.epouseMerePrenoms = epouseMerePrenoms;
    }

    public String getEpouseMereProfession() {
        return epouseMereProfession;
    }

    public void setEpouseMereProfession(String epouseMereProfession) {
        this.epouseMereProfession = epouseMereProfession;
    }

    public String getEpouseMereDomicile() {
        return epouseMereDomicile;
    }

    public void setEpouseMereDomicile(String epouseMereDomicile) {
        this.epouseMereDomicile = epouseMereDomicile;
    }

    public boolean isEpouseMereDecede() {
        return epouseMereDecede;
    }

    public void setEpouseMereDecede(boolean epouseMereDecede) {
        this.epouseMereDecede = epouseMereDecede;
    }

    public String getEpouseMereLieuDeces() {
        return epouseMereLieuDeces;
    }

    public void setEpouseMereLieuDeces(String epouseMereLieuDeces) {
        this.epouseMereLieuDeces = epouseMereLieuDeces;
    }

    public LocalDate getEpouseMereDateDeces() {
        return epouseMereDateDeces;
    }

    public void setEpouseMereDateDeces(LocalDate epouseMereDateDeces) {
        this.epouseMereDateDeces = epouseMereDateDeces;
    }

    public String getEpouseTemoinNom() {
        return epouseTemoinNom;
    }

    public void setEpouseTemoinNom(String epouseTemoinNom) {
        this.epouseTemoinNom = epouseTemoinNom;
    }

    public String getEpouseTemoinPrenoms() {
        return epouseTemoinPrenoms;
    }

    public void setEpouseTemoinPrenoms(String epouseTemoinPrenoms) {
        this.epouseTemoinPrenoms = epouseTemoinPrenoms;
    }

    public String getEpouseTemoinProfession() {
        return epouseTemoinProfession;
    }

    public void setEpouseTemoinProfession(String epouseTemoinProfession) {
        this.epouseTemoinProfession = epouseTemoinProfession;
    }

    public String getEpouseTemoinDomicile() {
        return epouseTemoinDomicile;
    }

    public void setEpouseTemoinDomicile(String epouseTemoinDomicile) {
        this.epouseTemoinDomicile = epouseTemoinDomicile;
    }

    public LocalDate getEpouseTemoinDateNaissance() {
        return epouseTemoinDateNaissance;
    }

    public void setEpouseTemoinDateNaissance(LocalDate epouseTemoinDateNaissance) {
        this.epouseTemoinDateNaissance = epouseTemoinDateNaissance;
    }

    public Set<MentionDivorceDto> getMentionDivorceDtos() {
        return mentionDivorceDtos;
    }

    public void setMentionDivorceDtos(Set<MentionDivorceDto> mentionDivorceDtos) {
        this.mentionDivorceDtos = mentionDivorceDtos;
    }

    public Set<MentionModifRegimeBiensDto> getMentionModifRegimeBiensDtos() {
        return mentionModifRegimeBiensDtos;
    }

    public void setMentionModifRegimeBiensDtos(Set<MentionModifRegimeBiensDto> mentionModifRegimeBiensDtos) {
        this.mentionModifRegimeBiensDtos = mentionModifRegimeBiensDtos;
    }

    public Set<MentionOrdonRetranscriptionDto> getMentionOrdonRetranscriptionDtos() {
        return mentionOrdonRetranscriptionDtos;
    }

    public void setMentionOrdonRetranscriptionDtos(Set<MentionOrdonRetranscriptionDto> mentionOrdonRetranscriptionDtos) {
        this.mentionOrdonRetranscriptionDtos = mentionOrdonRetranscriptionDtos;
    }

    
    
    
}
