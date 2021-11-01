/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.naissance.service;

import com.ibm.icu.text.RuleBasedNumberFormat;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
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
import io.urbis.naissance.domain.Operation;
import io.urbis.naissance.domain.Pere;
import io.urbis.naissance.domain.Sexe;
import io.urbis.naissance.domain.StatutActeNaissance;
import io.urbis.naissance.domain.Temoins;
import io.urbis.naissance.domain.TypeNaissance;
import io.urbis.naissance.domain.TypePiece;
import io.urbis.registre.domain.OfficierEtatCivil;
import io.urbis.registre.domain.Registre;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
    
    
    public ActeNaissanceDto findById(@NotBlank String id){
        return Optional.ofNullable(ActeNaissance.findById(id))
                .map(p -> (ActeNaissance)p)
                .map(this::mapToDto)
                .orElseThrow(() -> new WebApplicationException(Response.status(Response.Status.NOT_FOUND).build()));
       
    }
    
    
    public String creerActe(@NotNull ActeNaissanceDto acteNaissanceDto){
        
        log.infof("-- DECLARE: %s", acteNaissanceDto.getEnfantNom() + " " + acteNaissanceDto.getEnfantPrenoms());
        log.infof("-- REGISTRE ID: %s", acteNaissanceDto.getRegistreID());
        log.infof("-- OFFICIER ID: %s", acteNaissanceDto.getOfficierEtatCivilID());
        
        ActeNaissance acte = new ActeNaissance(new Enfant(), new Jugement(), new Pere(), 
                new Mere(), new Declarant(), new Interprete(), new Temoins());
        
        Registre registre = Registre.findById(acteNaissanceDto.getRegistreID());
        OfficierEtatCivil officier = OfficierEtatCivil.findById(acteNaissanceDto.getOfficierEtatCivilID());
        
        Operation op = Operation.fromString(acteNaissanceDto.getOperation());
        validerActe(registre, acteNaissanceDto,op);
        
        acte.officierEtatCivil = officier;
        acte.registre = registre;
        
        acte.numero = acteNaissanceDto.getNumero();
        
        if(acteNaissanceDto.getDateDeclaration() != null && !acteNaissanceDto.getDateDeclaration().isBlank()){
            acte.dateDeclaration = DateTimeUtils.fromStringToDateTime(acteNaissanceDto.getDateDeclaration());
            
        }
        
        
        if(acteNaissanceDto.getDateDressage() != null && !acteNaissanceDto.getDateDressage().isBlank()){
            acte.dateDressage = DateTimeUtils.fromStringToDateTime(acteNaissanceDto.getDateDressage());
        }
        
       // acte.dateEnregistrement = DateTimeUtils.fromStringToDateTime(acteNaissanceDto.getDateEnregistrement());
        if(acteNaissanceDto.getEnfantDateNaissance() != null && !acteNaissanceDto.getEnfantDateNaissance().isBlank()){
            acte.enfant.dateNaissance = DateTimeUtils.fromStringToDateTime(acteNaissanceDto.getEnfantDateNaissance()) ;
            //acte.enfant.dateNaissanceLettre = dateNaissanceEnLettre(acte.enfant.dateNaissance);
            //acte.enfant.heureNaissanceLettre = heureNaissanceEnLettre(acte.enfant.dateNaissance);
        }      
        acte.enfant.lieuNaissance = acteNaissanceDto.getEnfantLieuNaissance();
        acte.enfant.localite = acteNaissanceDto.getEnfantLocalite();
        
        if(acteNaissanceDto.getEnfantNationalite() != null && !acteNaissanceDto.getEnfantNationalite().isBlank()){
            acte.enfant.nationalite = Nationalite.fromString(acteNaissanceDto.getEnfantNationalite());
        }
        
        acte.enfant.nom = acteNaissanceDto.getEnfantNom();
        acte.enfant.prenoms = acteNaissanceDto.getEnfantPrenoms();
        acte.enfant.nomComplet = acteNaissanceDto.getEnfantNom() + " " + acteNaissanceDto.getEnfantPrenoms();
        
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
        acte.mere.nomComplet = acte.mere.nom + " " + acteNaissanceDto.getMerePrenoms();
        
        
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
        acte.pere.nomComplet = acte.pere.nom + " " + acte.pere.prenoms;
        
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
                
        
        if(acteNaissanceDto.getStatut() != null && !acteNaissanceDto.getStatut().isBlank()){
            acte.statut = StatutActeNaissance.fromString(acteNaissanceDto.getStatut());
        }
        
        if(acteNaissanceDto.getTypeNaissance() != null && !acteNaissanceDto.getTypeNaissance().isBlank()){
            acte.typeNaissance = TypeNaissance.fromString(acteNaissanceDto.getTypeNaissance());
        }
        
        acte.statut = StatutActeNaissance.PROJET;
        
        acte.extraitTexte = extraitTexte(acte);
        
        acte.persist();
        
        Operation operation = Operation.fromString(acteNaissanceDto.getOperation());
        
        if(operation == Operation.DECLARATION_JUGEMENT){
            //incrementer l'index du registre
            registre.numeroProchainActe += 1;
            //registre.persist();
        }
     
        return acte.id;
    }
    
    public void updateActe(@NotBlank String id,@NotNull ActeNaissanceDto acteNaissanceDto){
        
        ActeNaissance acte = ActeNaissance.findById(id);
        acte.enfant =  new Enfant();
        acte.jugement = new Jugement();
        acte.pere = new Pere(); 
        acte.mere =  new Mere();
        acte.declarant = new Declarant();
        acte.interprete = new Interprete();
        acte.temoins = new Temoins();
        
        
        Registre registre = Registre.findById(acteNaissanceDto.getRegistreID());
        OfficierEtatCivil officier = OfficierEtatCivil.findById(acteNaissanceDto.getOfficierEtatCivilID());
        
        Operation op = Operation.fromString(acteNaissanceDto.getOperation());
        validerActe(registre, acteNaissanceDto,op);
        
        acte.officierEtatCivil = officier;
        acte.registre = registre;
        
        log.infof("-- NUMERO DTO: %d", acteNaissanceDto.getNumero());
        
        acte.numero = acteNaissanceDto.getNumero();
        
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
        acte.enfant.nomComplet = acteNaissanceDto.getEnfantNom() + " " + acteNaissanceDto.getEnfantPrenoms();
        
        if(acteNaissanceDto.getEnfantSexe() != null && !acteNaissanceDto.getEnfantSexe().isBlank()){
            acte.enfant.sexe = Sexe.fromString(acteNaissanceDto.getEnfantSexe());
        }
        
                
        if(acteNaissanceDto.getJugementDate() != null && !acteNaissanceDto.getJugementDate().isBlank()){
            acte.jugement.date = DateTimeUtils.fromStringToDateTime(acteNaissanceDto.getJugementDate());
        }
        
        Optional.ofNullable(acte.jugement).ifPresent(j -> {
            acte.jugement.numero = acteNaissanceDto.getJugementNumero();
            acte.jugement.tribunal = acteNaissanceDto.getJugementTribunal();
        });
        
        
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
        acte.mere.nomComplet = acte.mere.nom + " " + acteNaissanceDto.getMerePrenoms();
        
        
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
        acte.pere.nomComplet = acte.pere.nom + " " + acte.pere.prenoms;
        
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
                
        
        if(acteNaissanceDto.getStatut() != null && !acteNaissanceDto.getStatut().isBlank()){
            acte.statut = StatutActeNaissance.fromString(acteNaissanceDto.getStatut());
        }
        
        if(acteNaissanceDto.getTypeNaissance() != null && !acteNaissanceDto.getTypeNaissance().isBlank()){
            acte.typeNaissance = TypeNaissance.fromString(acteNaissanceDto.getTypeNaissance());
        }
        
        acte.statut = StatutActeNaissance.fromString(acteNaissanceDto.getStatut());
        
        acte.extraitTexte = extraitTexte(acte);
        
                
        //incrementer l'index du registre
        //registre.numeroProchainActe += 1;
        //registre.persist();
        
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
    
    public void validerActe(Registre registre,ActeNaissanceDto acte,Operation operation){
        if(operation == Operation.DECLARATION_JUGEMENT){
            verifierNumero(registre, acte);
        }
        
        validerBorneInferieure(registre, acte.getNumero());
        validerBorneSuperieure(registre, acte.getNumero());
        //validerNombreDefeuillets(registre);
    }
    
    public void verifierNumero(Registre registre,ActeNaissanceDto acte){
        while(numeroExist(registre, acte.getNumero())){
            acte.setNumero(acte.getNumero() + 1);
            registre.numeroProchainActe += 1;
        }
    }
    
    /*
    public void validerNombreDefeuillets(Registre registre){
        if(registre.numeroProchainActe == registre.numeroDernierActe){
            Response res = Response.status(Response.Status.EXPECTATION_FAILED)
                    .entity(new Exception("le maximum de feuillets du registre est atteint"))
                    .build();
            throw new WebApplicationException(res);
        }
    
    }
    */
    
    public void validerBorneSuperieure(Registre registre,int numeroActe){
        if(numeroActe > registre.numeroDernierActe){
            Response res = Response.status(Response.Status.PRECONDITION_FAILED)
                   .entity("le numero de l'acte ne peut être supérieur au numéro du dernier acte du registre")
                   .build();
            throw new WebApplicationException(res);
        }
            
    }
    
    public void validerBorneInferieure(Registre registre,int numeroActe){
        if(numeroActe < registre.numeroPremierActe){
            Response res = Response.status(Response.Status.PRECONDITION_FAILED)
                   .entity("le numero de l'acte ne peut être inférieur au numéro du premier acte du registre")
                   .build();
            throw new WebApplicationException(res);
        }
            
    }
    
    public boolean numeroExist(Registre registre, int numeroActe){
       Optional<ActeNaissance> optActe = ActeNaissance.find("registre = ?1 AND numero = ?2",
               registre,numeroActe).firstResultOptional();
       
        return optActe.isPresent();
    }
    
    
    public List<ActeNaissanceDto> findWithFilters(int offset,int pageSize,@NotBlank String registreID){
        Registre registre = Registre.findById(registreID);
        PanacheQuery<ActeNaissance>  query = ActeNaissance.find("registre", registre);
        return query.stream().map(this::mapToDto).collect(Collectors.toList());
    }
    
    public int count(){
        return (int)ActeNaissance.count();
    }
    
    
 
    public ActeNaissanceDto mapToDto(@NotNull ActeNaissance acte){
        ActeNaissanceDto dto = new ActeNaissanceDto();
        
        dto.setCreated(acte.created);
        dto.setUpdated(acte.updated);
        
        dto.setId(acte.id);
        
        if(acte.dateDeclaration != null){
            dto.setDateDeclaration(DateTimeUtils.fromDateTimeToString(acte.dateDeclaration));
        }
        if(acte.dateDressage != null){
            dto.setDateDressage(DateTimeUtils.fromDateTimeToString(acte.dateDressage));
        }
         
        if(acte.declarant.dateNaissance != null){
            dto.setDeclarantDateNaissance(DateTimeUtils.fromDateTimeToString(acte.declarant.dateNaissance));
        }
        if(acte.declarant.datePiece != null){
            dto.setDeclarantDatePiece(DateTimeUtils.fromDateTimeToString(acte.declarant.datePiece));
        }
     
        Optional.ofNullable(acte.declarant).ifPresent(d -> {
            dto.setDeclarantLien(d.lien);
            dto.setDeclarantLieuNaissance(d.lieuNaissance);
            dto.setDeclarantLieuPiece(d.lieuPiece);
            dto.setDeclarantLocalite(d.localite);
            dto.setDeclarantNom(d.nom);
            dto.setDeclarantNumeroPiece(d.numeroPiece);
            dto.setDeclarantPrenoms(d.prenoms);
            dto.setDeclarantProfession(d.profession);
        });
     
        Optional.ofNullable(acte.declarant).map(d -> d.nationalite).ifPresent(n -> {
            dto.setDeclarantNationalite(n.name()); 
        });
        
        Optional.ofNullable(acte.declarant).map(d -> d.typePiece).ifPresent(t -> {
            dto.setDeclarantTypePiece(t.name()); 
        });
    
        Optional.ofNullable(acte.enfant).ifPresent(e -> {
            dto.setEnfantNom(acte.enfant.nom);
            dto.setEnfantPrenoms(acte.enfant.prenoms);
            dto.setEnfantLieuNaissance(acte.enfant.lieuNaissance);
            dto.setEnfantLocalite(acte.enfant.localite);
        });
        
        Optional.ofNullable(acte.enfant).map(e -> e.dateNaissance).ifPresent(d -> {
            dto.setEnfantDateNaissance(DateTimeUtils.fromDateTimeToString(d));
        });
        
        Optional.ofNullable(acte.enfant).map(e -> e.nationalite).ifPresent(n -> {
            dto.setEnfantNationalite(n.name());
        });
        
        Optional.ofNullable(acte.enfant).map(e -> e.sexe).ifPresent(s -> {
            dto.setEnfantSexe(s.name());
        });
     
        Optional.ofNullable(acte.interprete).ifPresent(i -> {
            dto.setInterpreteDomicile(i.domicile);
            dto.setInterpreteLangue(i.langue);
            dto.setInterpreteNom(i.nom);
            dto.setInterpretePrenoms(i.prenoms);
            dto.setInterpreteProfession(i.profession);
        });
        
        Optional.ofNullable(acte.interprete).map(i -> i.dateNaissance).ifPresent(d -> {
            dto.setInterpreteDateNaissance(DateTimeUtils.fromDateTimeToString(acte.interprete.dateNaissance));
        });
        
        Optional.ofNullable(acte.jugement).ifPresent(j -> {
            dto.setJugementNumero(j.numero);
            dto.setJugementTribunal(j.tribunal);
        
        });
        
        Optional.ofNullable(acte.jugement).map(j -> j.date).ifPresent(d -> {
            dto.setJugementDate(DateTimeUtils.fromDateTimeToString(acte.jugement.date));
        });
        
        //mere
        Optional.ofNullable(acte.mere).ifPresent(m -> {
            dto.setMereLieuDeces(m.lieuDeces);
            dto.setMereLieuNaissance(m.lieuNaissance);
            dto.setMereLieuPiece(m.lieuPiece);
            dto.setMereLocalite(m.localite);
            dto.setMereNom(m.nom);
            dto.setMereNumeroPiece(m.numeroPiece);
            dto.setMerePrenoms(m.prenoms);
            dto.setMereProfession(m.profession);
        
        });
        
         Optional.ofNullable(acte.mere).map(m -> m.dateDeces).ifPresent(d -> {
             dto.setMereDateDeces(DateTimeUtils.fromDateTimeToString(d));
         });
       
        Optional.ofNullable(acte.mere).map(m -> m.dateNaissance).ifPresent(d -> {
             dto.setMereDateNaissance(DateTimeUtils.fromDateTimeToString(d));
         });
        
        Optional.ofNullable(acte.mere).map(m -> m.datePiece).ifPresent(d -> {
             dto.setMereDatePiece(DateTimeUtils.fromDateTimeToString(d));
         });
        
        Optional.ofNullable(acte.mere).map(m -> m.nationalite).ifPresent(n -> {
             dto.setMereNationalite(n.name());
         });
        
        Optional.ofNullable(acte.mere).map(m -> m.typePiece).ifPresent(t -> {
             dto.setMereTypePiece(t.name());
         });
       
        //pere   
        Optional.ofNullable(acte.pere).ifPresent(p -> {
            dto.setPereLieuDeces(p.lieuDeces);
            dto.setPereLieuNaissance(p.lieuNaissance);
            dto.setPereLieuPiece(p.lieuPiece);
            dto.setPereLocalite(p.localite);
            dto.setPereNom(p.nom);
            dto.setPereNumeroPiece(p.numeroPiece);
            dto.setPerePrenoms(p.prenoms);
            dto.setPereProfession(p.profession);
        
        });
        
         Optional.ofNullable(acte.pere).map(p -> p.dateDeces).ifPresent(d -> {
             dto.setPereDateDeces(DateTimeUtils.fromDateTimeToString(d));
         });
       
        Optional.ofNullable(acte.pere).map(p -> p.dateNaissance).ifPresent(d -> {
             dto.setPereDateNaissance(DateTimeUtils.fromDateTimeToString(d));
         });
        
        Optional.ofNullable(acte.pere).map(p -> p.datePiece).ifPresent(d -> {
             dto.setPereDatePiece(DateTimeUtils.fromDateTimeToString(d));
         });
        
        Optional.ofNullable(acte.pere).map(p -> p.nationalite).ifPresent(n -> {
             dto.setPereNationalite(n.name());
         });
        
        Optional.ofNullable(acte.pere).map(p -> p.typePiece).ifPresent(t -> {
             dto.setPereTypePiece(t.name());
         });
        
        //temoins
        Optional.ofNullable(acte.temoins).ifPresent(t -> {
            dto.setTemoinPremierDomicile(acte.temoins.premierDomicile);
            dto.setTemoinPremierNom(acte.temoins.premierNom);
            dto.setTemoinPremierPrenoms(acte.temoins.premierPrenoms);
            dto.setTemoinPremierProfession(acte.temoins.premierProfession);
            
            dto.setTemoinDeuxiemeDomicile(acte.temoins.deuxiemeDomicile);
            dto.setTemoinDeuxiemeNom(acte.temoins.deuxiemeNom);
            dto.setTemoinDeuxiemePrenoms(acte.temoins.deuxiemePrenoms);
            dto.setTemoinDeuxiemeProfession(acte.temoins.deuxiemeProfession);
        });
        
        Optional.ofNullable(acte.temoins).map(t -> t.premierDateNaissance).ifPresent(d -> {
            dto.setTemoinPremierDateNaissance(DateTimeUtils.fromDateTimeToString(acte.temoins.premierDateNaissance));
        });
        if(acte.temoins.premierDateNaissance != null){
            
        }
        
        if(acte.temoins.deuxiemeDateNaissance != null){
            dto.setTemoinDeuxiemeDateNaissance(DateTimeUtils.fromDateTimeToString(acte.temoins.deuxiemeDateNaissance));
        }
     
        
        Optional.ofNullable(acte.modeDeclaration).ifPresent(m -> {
            dto.setModeDeclaration(acte.modeDeclaration.name());
        });
        
        Optional.ofNullable(acte.statut).ifPresent(m -> {
            dto.setStatut(acte.statut.name());
        });
        
        Optional.ofNullable(acte.typeNaissance).ifPresent(m -> {
            dto.setTypeNaissance(acte.typeNaissance.name());
        });
        
        dto.setMotifAnnulation(acte.motifAnnulation);
        dto.setNombreCopiesIntegrales(acte.nombreCopiesIntegrales);
        dto.setNombreExtraits(acte.nombreExtraits);
        
        dto.setNumero(acte.numero);
        dto.setRegistreID(acte.registre.id);
        
        dto.setOfficierEtatCivilID(acte.officierEtatCivil.id);
        dto.setOfficierEtatCivilNom(acte.officierEtatCivil.nom);
        dto.setOfficierEtatCivilPrenoms(acte.officierEtatCivil.prenoms);
        dto.setOfficierEtatCivilQualite(acte.officierEtatCivil.qualite);
        dto.setOfficierEtatCivilTitre(acte.officierEtatCivil.titre);
        
        
        dto.setRegistreNumero(acte.registre.reference.numero);
        dto.setRegistreAnnee(acte.registre.reference.annee);
        
        return dto;
    }
    
    public int numeroActe(@NotBlank String registreID){
        Registre registre = Registre.findById(registreID);
        if(registre == null){
            throw new WebApplicationException("Registre not found", Response.Status.NOT_FOUND);
        }
        
        return registre.numeroProchainActe;
        /*
        if(ActeNaissance.count() > 0){
            return registre.numeroProchainActe + 1;
        }else{
            return registre.numeroPremierActe;
        }
        */
      
    }
}
