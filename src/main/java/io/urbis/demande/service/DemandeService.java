/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.demande.service;

import io.urbis.acte.deces.service.ActeDecesService;
import io.urbis.acte.divers.service.ActeDiversService;
import io.urbis.acte.mariage.dto.ActeMariageDto;
import io.urbis.acte.mariage.service.ActeMariageService;
import io.urbis.acte.naissance.service.ActeNaissanceService;
import io.urbis.common.domain.TypePiece;

import io.urbis.demande.domain.Demande;
import io.urbis.demande.domain.Demandeur;
import io.urbis.demande.dto.DemandeDto;
import io.urbis.registre.domain.TypeRegistre;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 *
 * @author florent
 */
@ApplicationScoped
public class DemandeService {
    
    @Inject
    ActeDecesService acteDecesService;
    
    @Inject
    ActeDiversService acteDiversService;
    
    @Inject
    ActeMariageService acteMariageService;
    
    @Inject
    ActeNaissanceService acteNaissanceService;
    
        
    public void creer(@NotNull DemandeDto dto){
        
        TypeRegistre typeRegistre = TypeRegistre.fromString(dto.getTypeRegistre());
        
        //Acte acte = Acte.findById(dto.getActeID());
        /*
        switch(typeRegistre){
            case DECES:
                
                break;
            case DIVERS:
                break;
            case MARIAGE:
                break;
            case NAISSANCE:
                break;
            case SPECIAL_DECES:
                break;
            case SPECIAL_NAISSANCE:
                break;
        }
        */
        
        Demandeur demandeur = new Demandeur();
        
        Demande demande = new Demande(demandeur);
        
       // demande.acte = acte;
        demande.dateHeureDemande = dto.getDateHeureDemande();
        demande.dateHeureRdvRetrait = dto.getDateHeureRdvRetrait();
        demande.dateOuvertureRegistre = dto.getDateOuvertureRegistre();
        demande.demandeur.email = dto.getDemandeurEmail();
        demande.demandeur.nom = dto.getDemandeurNom();
        demande.demandeur.numero = dto.getDemandeurNumero();
        demande.demandeur.numeroPiece = dto.getDemandeurNumeroPiece();
        demande.demandeur.prenoms = dto.getDemandeurPrenoms();
        demande.demandeur.qualite = dto.getDemandeurQualite();
        demande.demandeur.typePiece = TypePiece.fromString(dto.getDemandeurTypePiece());
        
        demande.nombreCopies = dto.getNombreCopies();
        demande.nombreExtraits = dto.getNombreExtraits();
        demande.numero = dto.getNumero();
        demande.numeroActe = dto.getNumeroActe();
        demande.typeRegistre = TypeRegistre.fromString(dto.getTypeRegistre());
        
        demande.persist();
    
    }
    
    public DemandeDto findById(@NotBlank String id){
       Demande d = Demande.findById(id);
       return mapToDto(d);
    }
    
    public void modifier(@NotBlank String id,@NotNull DemandeDto demande){
    
    }
    
    private DemandeDto mapToDto(@NotNull Demande demande){
        
        DemandeDto dto = new DemandeDto();
        
        dto.setCreated(demande.created);
        dto.setDateHeureDemande(demande.dateHeureDemande);
        dto.setDateHeureRdvRetrait(demande.dateHeureRdvRetrait);
        dto.setDateOuvertureRegistre(demande.dateOuvertureRegistre);
        dto.setDemandeurEmail(demande.demandeur.email);
        dto.setDemandeurNom(demande.demandeur.nom);
        dto.setDemandeurNumero(demande.demandeur.numero);
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
        
        return dto;
    }
}
