/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.naissance.service;

import io.urbis.common.service.DateTimeUtils;
import io.urbis.naissance.dto.ActeNaissanceDto;
import io.urbis.naissance.domain.ActeNaissance;
import io.urbis.naissance.domain.Declarant;
import io.urbis.naissance.domain.Enfant;
import io.urbis.naissance.domain.Interprete;
import io.urbis.naissance.domain.Jugement;
import io.urbis.naissance.domain.Mere;
import io.urbis.naissance.domain.ModeDeclaration;
import io.urbis.naissance.domain.Nationalite;
import io.urbis.naissance.domain.Pere;
import io.urbis.naissance.domain.Sexe;
import io.urbis.naissance.domain.StatutActeNaissance;
import io.urbis.naissance.domain.Temoins;
import io.urbis.naissance.domain.TypeNaissance;
import io.urbis.naissance.domain.TypePiece;
import io.urbis.registre.domain.OfficierEtatCivil;
import io.urbis.registre.domain.Registre;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.constraints.NotBlank;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import org.jboss.logging.Logger;

/**
 *
 * @author florent
 */
@ApplicationScoped
public class ActeNaissanceService {
    
    @Inject
    Logger log;
    
    
    public void creerActe(@NotBlank ActeNaissanceDto acteNaissanceDto){
        
        log.infof("-- DECLARE: %s", acteNaissanceDto.getEnfantNom() + " " + acteNaissanceDto.getEnfantPrenoms());
        log.infof("-- REGISTRE ID: %s", acteNaissanceDto.getRegistreID());
        log.infof("-- OFFICIER ID: %s", acteNaissanceDto.getOfficierEtatCivilID());
        
        Registre registre = Registre.findById(acteNaissanceDto.getRegistreID());
        OfficierEtatCivil officier = OfficierEtatCivil.findById(acteNaissanceDto.getOfficierEtatCivilID());
        
        ActeNaissance acte = new ActeNaissance(new Enfant(), new Jugement(), new Pere(), 
                new Mere(), new Declarant(), new Interprete(), new Temoins());
        
        acte.officierEtatCivil = officier;
        acte.registre = registre;
        
        if(acteNaissanceDto.getDateDeclaration() != null && !acteNaissanceDto.getDateDeclaration().isBlank()){
            acte.dateDeclaration = DateTimeUtils.fromStringToDateTime(acteNaissanceDto.getDateDeclaration());
        }
        if(acteNaissanceDto.getDateDressage() != null && !acteNaissanceDto.getDateDressage().isBlank()){
            acte.dateDressage = DateTimeUtils.fromStringToDateTime(acteNaissanceDto.getDateDressage());
        }
        
       // acte.dateEnregistrement = DateTimeUtils.fromStringToDateTime(acteNaissanceDto.getDateEnregistrement());
        if(acteNaissanceDto.getEnfantDateNaissance() != null && !acteNaissanceDto.getEnfantDateNaissance().isBlank()){
            acte.enfant.dateNaissance = DateTimeUtils.fromStringToDateTime(acteNaissanceDto.getEnfantDateNaissance()) ;
        }      
        acte.enfant.lieuNaissance = acteNaissanceDto.getEnfantLieuNaissance();
        acte.enfant.localite = acteNaissanceDto.getEnfantLocalite();
        
        if(acteNaissanceDto.getEnfantNationalite() != null && !acteNaissanceDto.getEnfantNationalite().isBlank()){
            acte.enfant.nationalite = Nationalite.fromString(acteNaissanceDto.getEnfantNationalite());
        }
        
        acte.enfant.nom = acteNaissanceDto.getEnfantNom();
        acte.enfant.prenoms = acteNaissanceDto.getEnfantPrenoms();
        
        if(acteNaissanceDto.getEnfantSexe() != null && !acteNaissanceDto.getEnfantSexe().isBlank()){
            acte.enfant.sexe = Sexe.fromString(acteNaissanceDto.getEnfantSexe());
        }
        
        if(acteNaissanceDto.getJugementDate() != null && !acteNaissanceDto.getJugementDate().isBlank()){
            acte.jugement.date = DateTimeUtils.fromStringToDateTime(acteNaissanceDto.getJugementDate());
        }
        
        acte.jugement.numero = acteNaissanceDto.getJugementNumero();
        acte.jugement.tribunal = acteNaissanceDto.getJugementTribunal();
        
        if(acteNaissanceDto.getMereDateDeces() != null && !acteNaissanceDto.getMereDateDeces().isBlank()){
            acte.mere.dateDeces = DateTimeUtils.fromStringToDateTime(acteNaissanceDto.getMereDateDeces());
        }
        if(acteNaissanceDto.getMereDateNaissance() != null && !acteNaissanceDto.getMereDateNaissance().isBlank()){
            acte.mere.dateNaissance = DateTimeUtils.fromStringToDateTime(acteNaissanceDto.getMereDateNaissance());
        }
        if(acteNaissanceDto.getMereDatePiece() != null && !acteNaissanceDto.getMereDatePiece().isBlank()){
            acte.mere.datePiece = DateTimeUtils.fromStringToDateTime(acteNaissanceDto.getMereDatePiece());
        }
        
        acte.mere.lieuDeces = acteNaissanceDto.getMereLieuDeces();
        acte.mere.lieuNaissance = acteNaissanceDto.getMereLieuNaissance();
        acte.mere.lieuPiece = acteNaissanceDto.getMereLieuPiece();
        acte.mere.localite = acteNaissanceDto.getMereLocalite();
        
        if(acteNaissanceDto.getMereNationalite() != null && !acteNaissanceDto.getMereNationalite().isBlank()){
            acte.mere.nationalite =  Nationalite.fromString(acteNaissanceDto.getMereNationalite());
        }
        
        acte.mere.nom = acteNaissanceDto.getMereNom();
        acte.mere.numeroPiece = acteNaissanceDto.getMereNumeroPiece();
        acte.mere.prenoms = acteNaissanceDto.getMerePrenoms();
        acte.mere.profession = acteNaissanceDto.getMereProfession();
        
        if(acteNaissanceDto.getMereTypePiece() != null && !acteNaissanceDto.getMereTypePiece().isBlank()){
            acte.mere.typePiece = TypePiece.fromString(acteNaissanceDto.getMereTypePiece()) ;
        }
        
        if(acteNaissanceDto.getPereDateDeces() != null && !acteNaissanceDto.getPereDateDeces().isBlank()){
             acte.pere.dateDeces = DateTimeUtils.fromStringToDateTime(acteNaissanceDto.getPereDateDeces());
        }
        if(acteNaissanceDto.getPereDateNaissance() != null && !acteNaissanceDto.getPereDateNaissance().isBlank()){
            acte.pere.dateNaissance = DateTimeUtils.fromStringToDateTime(acteNaissanceDto.getPereDateNaissance());
        }
        if(acteNaissanceDto.getPereDatePiece() != null && !acteNaissanceDto.getPereDatePiece().isBlank()){
            acte.pere.datePiece = DateTimeUtils.fromStringToDateTime(acteNaissanceDto.getPereDatePiece());
        }
        
        acte.pere.lieuDeces = acteNaissanceDto.getPereLieuDeces();
        acte.pere.lieuNaissance = acteNaissanceDto.getPereLieuNaissance();
        acte.pere.lieuPiece = acteNaissanceDto.getPereLieuPiece();
        acte.pere.localite = acteNaissanceDto.getPereLocalite();
        
        if(acteNaissanceDto.getPereNationalite() != null && !acteNaissanceDto.getPereNationalite().isBlank()){
            acte.pere.nationalite =  Nationalite.fromString(acteNaissanceDto.getPereNationalite()); 
        }
        
        acte.pere.nom = acteNaissanceDto.getPereNom();
        acte.pere.numeroPiece = acteNaissanceDto.getPereNumeroPiece();
        acte.pere.prenoms = acteNaissanceDto.getPerePrenoms();
        acte.pere.profession = acteNaissanceDto.getPereProfession();
        
        if(acteNaissanceDto.getPereTypePiece() != null && !acteNaissanceDto.getPereTypePiece().isBlank()){
            acte.pere.typePiece = TypePiece.fromString(acteNaissanceDto.getPereTypePiece()) ;
        }
            
        acte.declarant.lien = acteNaissanceDto.getDeclarantLien();
        if(acteNaissanceDto.getDeclarantDatePiece() != null && !acteNaissanceDto.getDeclarantDatePiece().isBlank()){
            acte.declarant.dateNaissance = DateTimeUtils.fromStringToDateTime(acteNaissanceDto.getDeclarantDateNaissance());
        }
        if(acteNaissanceDto.getDeclarantDatePiece() != null && !acteNaissanceDto.getDeclarantDatePiece().isBlank()){
            acte.declarant.datePiece = DateTimeUtils.fromStringToDateTime(acteNaissanceDto.getDeclarantDatePiece());
        }
        
        acte.declarant.lieuNaissance = acteNaissanceDto.getDeclarantLieuNaissance();
        acte.declarant.lieuPiece = acteNaissanceDto.getDeclarantLieuPiece();
        acte.declarant.localite = acteNaissanceDto.getDeclarantLocalite();
        
        if(acteNaissanceDto.getDeclarantNationalite() != null && !acteNaissanceDto.getDeclarantNationalite().isBlank()){
            acte.declarant.nationalite =  Nationalite.fromString(acteNaissanceDto.getDeclarantNationalite()); 
        }
        
        acte.declarant.nom = acteNaissanceDto.getDeclarantNom();
        acte.declarant.numeroPiece = acteNaissanceDto.getDeclarantNumeroPiece();
        acte.declarant.prenoms = acteNaissanceDto.getDeclarantPrenoms();
        acte.declarant.profession = acteNaissanceDto.getDeclarantProfession();
        
        if(acteNaissanceDto.getDeclarantTypePiece() != null && !acteNaissanceDto.getDeclarantTypePiece().isBlank()){
            acte.declarant.typePiece = TypePiece.fromString(acteNaissanceDto.getDeclarantTypePiece());
        }
        
        if(acteNaissanceDto.getInterpreteDateNaissance() != null && !acteNaissanceDto.getInterpreteDateNaissance().isBlank()){
            acte.interprete.dateNaissance = DateTimeUtils.fromStringToDateTime(acteNaissanceDto.getInterpreteDateNaissance());
        }
        
        acte.interprete.domicile = acteNaissanceDto.getInterpreteDomicile();
        acte.interprete.langue = acteNaissanceDto.getInterpreteLangue();
        acte.interprete.nom = acteNaissanceDto.getInterpreteNom();
        acte.interprete.prenoms = acteNaissanceDto.getInterpretePrenoms();
        acte.interprete.profession = acteNaissanceDto.getInterpreteProfession();
        
        if(acteNaissanceDto.getTemoinPremierDateNaissance() != null && !acteNaissanceDto.getTemoinPremierDateNaissance().isBlank()){
            acte.temoins.premierDateNaissance = DateTimeUtils.fromStringToDateTime(acteNaissanceDto.getTemoinPremierDateNaissance());
        }
        
        acte.temoins.premierDomicile = acteNaissanceDto.getTemoinPremierDomicile();
        acte.temoins.premierNom = acteNaissanceDto.getTemoinPremierNom();
        acte.temoins.premierPrenoms = acteNaissanceDto.getTemoinPremierPrenoms();
        acte.temoins.premierProfession = acteNaissanceDto.getTemoinPremierProfession();
        
        if(acteNaissanceDto.getTemoinDeuxiemeDateNaissance() != null && !acteNaissanceDto.getTemoinDeuxiemeDateNaissance().isBlank()){
            acte.temoins.deuxiemeDateNaissance = DateTimeUtils.fromStringToDateTime(acteNaissanceDto.getTemoinDeuxiemeDateNaissance());
        }
        
        acte.temoins.deuxiemeDomicile = acteNaissanceDto.getTemoinDeuxiemeDomicile();
        acte.temoins.deuxiemeNom = acteNaissanceDto.getTemoinDeuxiemeNom();
        acte.temoins.deuxiemePrenoms = acteNaissanceDto.getTemoinDeuxiemePrenoms();
        acte.temoins.deuxiemeProfession = acteNaissanceDto.getTemoinPremierProfession();
        
        if(acteNaissanceDto.getModeDeclaration() != null && !acteNaissanceDto.getModeDeclaration().isBlank()){
            acte.modeDeclaration = ModeDeclaration.fromString(acteNaissanceDto.getModeDeclaration());
        }
        
        acte.motifAnnulation = acteNaissanceDto.getMotifAnnulation();
        acte.nombreCopiesIntegrales = acteNaissanceDto.getNombreCopiesIntegrales();
        acte.nombreExtraits = acteNaissanceDto.getNombreExtraits();
        acte.numero = acteNaissanceDto.getNumero();
        
        
        if(acteNaissanceDto.getStatut() != null && !acteNaissanceDto.getStatut().isBlank()){
            acte.statut = StatutActeNaissance.fromString(acteNaissanceDto.getStatut());
        }
        
        if(acteNaissanceDto.getTypeNaissance() != null && !acteNaissanceDto.getTypeNaissance().isBlank()){
            acte.typeNaissance = TypeNaissance.fromString(acteNaissanceDto.getTypeNaissance());
        }
        
        acte.statut = StatutActeNaissance.PROJET;
        
        acte.persist();
        
        
        
    }
 
    public ActeNaissanceDto mapToDto(ActeNaissance acte){
        ActeNaissanceDto dto = new ActeNaissanceDto();
        
        dto.setCreated(acte.created);
        dto.setUpdated(acte.updated);
        if(acte.dateDeclaration != null){
            dto.setDateDeclaration(DateTimeUtils.fromDateTimeToString(acte.dateDeclaration));
        }
        if(acte.dateDressage != null){
            dto.setDateDressage(DateTimeUtils.fromDateTimeToString(acte.dateDressage));
        }
        
      //  dto.setDateEnregistrement(DateTimeUtils.fromDateTimeToString(acte.dateEnregistrement));
        
        dto.setDeclarantLien(acte.declarant.lien);
        if(acte.declarant.dateNaissance != null){
            dto.setDeclarantDateNaissance(DateTimeUtils.fromDateTimeToString(acte.declarant.dateNaissance));
        }
        if(acte.declarant.datePiece != null){
            dto.setDeclarantDatePiece(DateTimeUtils.fromDateTimeToString(acte.declarant.datePiece));
        }
        
        dto.setDeclarantLieuNaissance(acte.declarant.lieuNaissance);
        dto.setDeclarantLieuPiece(acte.declarant.lieuPiece);
        dto.setDeclarantLocalite(acte.declarant.localite);
        dto.setDeclarantNationalite(acte.declarant.nationalite.name());
        dto.setDeclarantNom(acte.declarant.nom);
        dto.setDeclarantNumeroPiece(acte.declarant.numeroPiece);
        dto.setDeclarantPrenoms(acte.declarant.prenoms);
        dto.setDeclarantProfession(acte.declarant.profession);
        dto.setDeclarantTypePiece(acte.declarant.typePiece.name());
        
        if(acte.enfant.dateNaissance != null){
            dto.setEnfantDateNaissance(DateTimeUtils.fromDateTimeToString(acte.enfant.dateNaissance));
        }
        
        dto.setEnfantLieuNaissance(acte.enfant.lieuNaissance);
        dto.setEnfantLocalite(acte.enfant.lieuNaissance);
        dto.setEnfantNationalite(acte.enfant.nationalite.name());
        dto.setEnfantNom(acte.enfant.nom);
        dto.setEnfantPrenoms(acte.enfant.prenoms);
        dto.setEnfantSexe(acte.enfant.sexe.name());
        
        if(acte.interprete.dateNaissance != null){
            dto.setInterpreteDateNaissance(DateTimeUtils.fromDateTimeToString(acte.interprete.dateNaissance));
        }
        
        dto.setInterpreteDomicile(acte.interprete.domicile);
        dto.setInterpreteLangue(acte.interprete.langue);
        dto.setInterpreteNom(acte.interprete.nom);
        dto.setInterpretePrenoms(acte.interprete.prenoms);
        dto.setInterpreteProfession(acte.interprete.profession);
        
        if(acte.jugement.date != null){
            dto.setJugementDate(DateTimeUtils.fromDateTimeToString(acte.jugement.date));
        }
        
        dto.setJugementNumero(acte.jugement.numero);
        dto.setJugementTribunal(acte.jugement.tribunal);
        
        if(acte.mere.dateDeces != null){
            dto.setMereDateDeces(DateTimeUtils.fromDateTimeToString(acte.mere.dateDeces));
        }
        if(acte.mere.dateNaissance != null){
            dto.setMereDateNaissance(DateTimeUtils.fromDateTimeToString(acte.mere.dateNaissance));
        }
        if(acte.mere.datePiece != null){
            dto.setMereDatePiece(DateTimeUtils.fromDateTimeToString(acte.mere.datePiece));
        }
        
        dto.setMereLieuDeces(acte.mere.lieuDeces);
        dto.setMereLieuNaissance(acte.mere.lieuNaissance);
        dto.setMereLieuPiece(acte.mere.lieuPiece);
        dto.setMereLocalite(acte.mere.localite);
        dto.setMereNationalite(acte.mere.nationalite.name());
        dto.setMereNom(acte.mere.nom);
        dto.setMereNumeroPiece(acte.mere.numeroPiece);
        dto.setMerePrenoms(acte.mere.prenoms);
        dto.setMereProfession(acte.mere.profession);
        dto.setMereTypePiece(acte.mere.typePiece.name());
        
        if(acte.pere.dateDeces != null){
            dto.setPereDateDeces(DateTimeUtils.fromDateTimeToString(acte.pere.dateDeces));
        }
        if(acte.pere.dateNaissance != null){
            dto.setPereDateNaissance(DateTimeUtils.fromDateTimeToString(acte.pere.dateNaissance));
        }
        if(acte.pere.datePiece != null){
            dto.setPereDatePiece(DateTimeUtils.fromDateTimeToString(acte.pere.datePiece));
        }
        
        dto.setPereLieuDeces(acte.pere.lieuDeces);
        dto.setPereLieuNaissance(acte.pere.lieuNaissance);
        dto.setPereLieuPiece(acte.pere.lieuPiece);
        dto.setPereLocalite(acte.pere.localite);
        dto.setPereNationalite(acte.pere.nationalite.name());
        dto.setPereNom(acte.pere.nom);
        dto.setPereNumeroPiece(acte.pere.numeroPiece);
        dto.setPerePrenoms(acte.pere.prenoms);
        dto.setPereProfession(acte.pere.profession);
        dto.setPereTypePiece(acte.pere.typePiece.name());
        
        if(acte.temoins.premierDateNaissance != null){
            dto.setTemoinPremierDateNaissance(DateTimeUtils.fromDateTimeToString(acte.temoins.premierDateNaissance));
        }
        
        dto.setTemoinPremierDomicile(acte.temoins.premierDomicile);
        dto.setTemoinPremierNom(acte.temoins.premierNom);
        dto.setTemoinPremierPrenoms(acte.temoins.premierPrenoms);
        dto.setTemoinPremierProfession(acte.temoins.premierProfession);
        
        if(acte.temoins.deuxiemeDateNaissance != null){
            dto.setTemoinDeuxiemeDateNaissance(DateTimeUtils.fromDateTimeToString(acte.temoins.deuxiemeDateNaissance));
        }
        
        dto.setTemoinDeuxiemeDomicile(acte.temoins.deuxiemeDomicile);
        dto.setTemoinDeuxiemeNom(acte.temoins.deuxiemeNom);
        dto.setTemoinDeuxiemePrenoms(acte.temoins.deuxiemePrenoms);
        dto.setTemoinDeuxiemeProfession(acte.temoins.deuxiemeProfession);
        
        dto.setModeDeclaration(acte.modeDeclaration.name());
        dto.setMotifAnnulation(acte.motifAnnulation);
        dto.setNombreCopiesIntegrales(acte.nombreCopiesIntegrales);
        dto.setNombreExtraits(acte.nombreExtraits);
        
        dto.setNumero(acte.numero);
        dto.setOfficierEtatCivilID(acte.officierEtatCivil.id);
        dto.setRegistreID(acte.registre.id);
        dto.setStatut(acte.statut.name());
        dto.setTypeNaissance(acte.typeNaissance.name());
        
        return dto;
    }
    
    public int numeroActe(@NotBlank String registreID){
        Registre registre = Registre.findById(registreID);
        if(registre == null){
            throw new WebApplicationException("Registre not found", Response.Status.NOT_FOUND);
        }
        if(ActeNaissance.count() > 0){
            return registre.indexDernierNumero + 1;
        }else{
            return registre.numeroPremierActe;
        }
      
    }
}
