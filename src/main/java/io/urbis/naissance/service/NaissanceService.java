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
public class NaissanceService {
    
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
        acte.mere.typePiece = acteNaissanceDto.getMereTypePiece();
        
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
        acte.pere.typePiece = acteNaissanceDto.getPereTypePiece();
        
       
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
        acte.declarant.typePiece = acteNaissanceDto.getDeclarantTypePiece();
        
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
