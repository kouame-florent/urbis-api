/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.naissance.service;

import io.urbis.naissance.dto.ActeNaissanceDto;
import io.urbis.naissance.domain.ActeNaissance;
import io.urbis.naissance.domain.ModeDeclaration;
import io.urbis.naissance.domain.Nationalite;
import io.urbis.naissance.domain.Sexe;
import io.urbis.naissance.domain.StatutActeNaissance;
import io.urbis.naissance.domain.TypeNaissance;
import io.urbis.naissance.domain.TypePiece;
import io.urbis.registre.domain.OfficierEtatCivil;
import io.urbis.registre.domain.Registre;
import javax.enterprise.context.ApplicationScoped;
import javax.validation.constraints.NotBlank;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 *
 * @author florent
 */
@ApplicationScoped
public class ActeNaissanceService {
    
    public void creerActe(@NotBlank ActeNaissanceDto acteNaissanceDto){
        
        Registre registre = Registre.findById(acteNaissanceDto.getRegistreID());
        OfficierEtatCivil officier = OfficierEtatCivil.findById(acteNaissanceDto.getOfficierEtatCivilID());
        
        ActeNaissance acte = new ActeNaissance();
        
        acte.dateDeclaration = acteNaissanceDto.getDateDeclaration();
        acte.dateDressage = acteNaissanceDto.getDateDressage();
        acte.dateEnregistrement = acteNaissanceDto.getDateEnregistrement();
        acte.enfant.dateNaissance = acteNaissanceDto.getEnfantDateNaissance();
        acte.enfant.lieuNaissance = acteNaissanceDto.getEnfantLieuNaissance();
        acte.enfant.localite = acteNaissanceDto.getEnfantLocalite();
        acte.enfant.nationalite = Nationalite.fromString(acteNaissanceDto.getEnfantNationalite());
        acte.enfant.nom = acteNaissanceDto.getEnfantNom();
        acte.enfant.prenoms = acteNaissanceDto.getEnfantPrenoms();
        acte.enfant.sexe = Sexe.fromString(acteNaissanceDto.getEnfantSexe());
        
        acte.jugement.date = acteNaissanceDto.getJugementDate();
        acte.jugement.numero = acteNaissanceDto.getJugementNumero();
        acte.jugement.tribunal = acteNaissanceDto.getJugementTribunal();
        
        acte.mere.dateDeces = acteNaissanceDto.getMereDateDeces();
        acte.mere.dateNaissance = acteNaissanceDto.getMereDateNaissance();
        acte.mere.datePiece = acteNaissanceDto.getMereDatePiece();
        acte.mere.lieuDeces = acteNaissanceDto.getMereLieuDeces();
        acte.mere.lieuNaissance = acteNaissanceDto.getMereLieuNaissance();
        acte.mere.lieuPiece = acteNaissanceDto.getMereLieuPiece();
        acte.mere.localite = acteNaissanceDto.getMereLocalite();
        acte.mere.nationalite =  Nationalite.fromString(acteNaissanceDto.getMereNationalite());
        acte.mere.nom = acteNaissanceDto.getMereNom();
        acte.mere.numeroPiece = acteNaissanceDto.getMereNumeroPiece();
        acte.mere.prenoms = acteNaissanceDto.getMerePrenoms();
        acte.mere.profession = acteNaissanceDto.getMereProfession();
        acte.mere.typePiece = TypePiece.fromString(acteNaissanceDto.getMereTypePiece()) ;
        
        acte.pere.dateDeces = acteNaissanceDto.getPereDateDeces();
        acte.pere.dateNaissance = acteNaissanceDto.getPereDateNaissance();
        acte.pere.datePiece = acteNaissanceDto.getPereDatePiece();
        acte.pere.lieuDeces = acteNaissanceDto.getPereLieuDeces();
        acte.pere.lieuNaissance = acteNaissanceDto.getPereLieuNaissance();
        acte.pere.lieuPiece = acteNaissanceDto.getPereLieuPiece();
        acte.pere.localite = acteNaissanceDto.getPereLocalite();
        acte.pere.nationalite =  Nationalite.fromString(acteNaissanceDto.getPereNationalite()); 
        acte.pere.nom = acteNaissanceDto.getPereNom();
        acte.pere.numeroPiece = acteNaissanceDto.getPereNumeroPiece();
        acte.pere.prenoms = acteNaissanceDto.getPerePrenoms();
        acte.pere.profession = acteNaissanceDto.getPereProfession();
        acte.pere.typePiece = TypePiece.fromString(acteNaissanceDto.getPereTypePiece()) ;
            
        acte.declarant.dateNaissance = acteNaissanceDto.getDeclarantDateNaissance();
        acte.declarant.datePiece = acteNaissanceDto.getDeclarantDatePiece();
        acte.declarant.lieuNaissance = acteNaissanceDto.getDeclarantLieuNaissance();
        acte.declarant.lieuPiece = acteNaissanceDto.getDeclarantLieuPiece();
        acte.declarant.localite = acteNaissanceDto.getDeclarantLocalite();
        acte.declarant.nationalite =  Nationalite.fromString(acteNaissanceDto.getDeclarantNationalite()); 
        acte.declarant.nom = acteNaissanceDto.getDeclarantNom();
        acte.declarant.numeroPiece = acteNaissanceDto.getDeclarantNumeroPiece();
        acte.declarant.prenoms = acteNaissanceDto.getDeclarantPrenoms();
        acte.declarant.profession = acteNaissanceDto.getDeclarantProfession();
        acte.declarant.typePiece = TypePiece.fromString(acteNaissanceDto.getDeclarantTypePiece());
        
        acte.interprete.dateNaissance = acteNaissanceDto.getInterpreteDateNaissance();
        acte.interprete.domicile = acteNaissanceDto.getInterpreteDomicile();
        acte.interprete.langue = acteNaissanceDto.getInterpreteLangue();
        acte.interprete.nom = acteNaissanceDto.getInterpreteNom();
        acte.interprete.prenoms = acteNaissanceDto.getInterpretePrenoms();
        acte.interprete.profession = acteNaissanceDto.getInterpreteProfession();
        
        acte.temoins.premierDateNaissance = acteNaissanceDto.getTemoinPremierDateNaissance();
        acte.temoins.premierDomicile = acteNaissanceDto.getTemoinPremierDomicile();
        acte.temoins.premierNom = acteNaissanceDto.getTemoinPremierNom();
        acte.temoins.premierPrenoms = acteNaissanceDto.getTemoinPremierPrenoms();
        acte.temoins.premierProfession = acteNaissanceDto.getTemoinPremierProfession();
        
        acte.temoins.deuxiemeDateNaissance = acteNaissanceDto.getTemoinDeuxiemeDateNaissance();
        acte.temoins.deuxiemeDomicile = acteNaissanceDto.getTemoinDeuxiemeDomicile();
        acte.temoins.deuxiemeNom = acteNaissanceDto.getTemoinDeuxiemeNom();
        acte.temoins.deuxiemePrenoms = acteNaissanceDto.getTemoinDeuxiemePrenoms();
        acte.temoins.deuxiemeProfession = acteNaissanceDto.getTemoinPremierProfession();
        
        acte.modeDeclaration = ModeDeclaration.fromString(acteNaissanceDto.getModeDeclaration());
        acte.motifAnnulation = acteNaissanceDto.getMotifAnnulation();
        acte.nombreCopiesIntegrales = acteNaissanceDto.getNombreCopiesIntegrales();
        acte.nombreExtraits = acteNaissanceDto.getNombreExtraits();
        acte.numero = acteNaissanceDto.getNumero();
        acte.officierEtatCivil = officier;
        acte.registre = registre;
        acte.statut = StatutActeNaissance.fromString(acteNaissanceDto.getStatut());
        acte.typeNaissance = TypeNaissance.fromString(acteNaissanceDto.getTypeNaissance());
        
        acte.persist();
        
        
    }
 
    public ActeNaissanceDto mapToDto(ActeNaissance acte){
        ActeNaissanceDto dto = new ActeNaissanceDto();
        
        dto.setCreated(acte.created);
        dto.setUpdated(acte.updated);
        dto.setDateDeclaration(acte.dateDeclaration);
        dto.setDateDressage(acte.dateDressage);
        dto.setDateEnregistrement(acte.dateEnregistrement);
        
        dto.setDeclarantDateNaissance(acte.declarant.dateNaissance);
        dto.setDeclarantDatePiece(acte.declarant.datePiece);
        dto.setDeclarantLieuNaissance(acte.declarant.lieuNaissance);
        dto.setDeclarantLieuPiece(acte.declarant.lieuPiece);
        dto.setDeclarantLocalite(acte.declarant.localite);
        dto.setDeclarantNationalite(acte.declarant.nationalite.name());
        dto.setDeclarantNom(acte.declarant.nom);
        dto.setDeclarantNumeroPiece(acte.declarant.numeroPiece);
        dto.setDeclarantPrenoms(acte.declarant.prenoms);
        dto.setDeclarantProfession(acte.declarant.profession);
        dto.setDeclarantTypePiece(acte.declarant.typePiece.name());
        
        dto.setEnfantDateNaissance(acte.enfant.dateNaissance);
        dto.setEnfantLieuNaissance(acte.enfant.lieuNaissance);
        dto.setEnfantLocalite(acte.enfant.lieuNaissance);
        dto.setEnfantNationalite(acte.enfant.nationalite.name());
        dto.setEnfantNom(acte.enfant.nom);
        dto.setEnfantPrenoms(acte.enfant.prenoms);
        dto.setEnfantSexe(acte.enfant.sexe.name());
        
        dto.setInterpreteDateNaissance(acte.interprete.dateNaissance);
        dto.setInterpreteDomicile(acte.interprete.domicile);
        dto.setInterpreteLangue(acte.interprete.langue);
        dto.setInterpreteNom(acte.interprete.nom);
        dto.setInterpretePrenoms(acte.interprete.prenoms);
        dto.setInterpreteProfession(acte.interprete.profession);
        
        dto.setJugementDate(acte.jugement.date);
        dto.setJugementNumero(acte.jugement.numero);
        dto.setJugementTribunal(acte.jugement.tribunal);
        
        dto.setMereDateDeces(acte.mere.dateDeces);
        dto.setMereDateNaissance(acte.mere.dateNaissance);
        dto.setMereDatePiece(acte.mere.datePiece);
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
        
        dto.setPereDateDeces(acte.pere.dateDeces);
        dto.setPereDateNaissance(acte.pere.dateNaissance);
        dto.setPereDatePiece(acte.pere.datePiece);
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
        
        dto.setModeDeclaration(acte.modeDeclaration.name());
        dto.setMotifAnnulation(acte.motifAnnulation);
        dto.setNombreCopiesIntegrales(acte.nombreCopiesIntegrales);
        dto.setNombreExtraits(acte.nombreExtraits);
        
        dto.setNumero(acte.numero);
        dto.setOfficierEtatCivilID(acte.officierEtatCivil.id);
        dto.setRegistreID(acte.registre.id);
        dto.setStatut(acte.statut.name());
        
        
        
        
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
