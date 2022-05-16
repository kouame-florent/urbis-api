/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.demande.service;

import io.urbis.acte.Acte;
import io.urbis.acte.deces.domain.ActeDeces;
import io.urbis.acte.deces.service.ActeDecesService;
import io.urbis.acte.divers.service.ActeDiversService;
import io.urbis.acte.mariage.service.ActeMariageService;
import io.urbis.acte.naissance.service.ActeNaissanceService;
import io.urbis.common.domain.TypePiece;
import io.urbis.common.domain.TypeRegistre;
import io.urbis.demande.domain.Demande;
import io.urbis.demande.domain.Demandeur;
import io.urbis.demande.dto.DemandeurDto;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
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
    
        
    public void creer(@NotNull DemandeurDto dto){
        
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
        
    
    }
}
