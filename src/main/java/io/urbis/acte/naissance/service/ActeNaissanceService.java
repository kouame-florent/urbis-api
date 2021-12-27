/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.acte.naissance.service;

import com.ibm.icu.text.RuleBasedNumberFormat;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Sort;
import io.urbis.mention.dto.MentionAdoptionDto;
import io.urbis.mention.dto.MentionDecesDto;
import io.urbis.mention.dto.MentionDissolutionMariageDto;
import io.urbis.mention.dto.MentionLegitimationDto;
import io.urbis.mention.dto.MentionMariageDto;
import io.urbis.mention.dto.MentionReconnaissanceDto;
import io.urbis.mention.dto.MentionRectificationDto;
import io.urbis.mention.service.MentionAdoptionService;
import io.urbis.mention.service.MentionDecesService;
import io.urbis.mention.service.MentionDissolutionMariageService;
import io.urbis.mention.service.MentionLegitimationService;
import io.urbis.mention.service.MentionMariageService;
import io.urbis.mention.service.MentionReconnaissanceService;
import io.urbis.mention.service.MentionRectificationService;
import io.urbis.acte.naissance.domain.ActeNaissance;
import io.urbis.acte.naissance.domain.Sexe;
import io.urbis.acte.naissance.domain.Nationalite;
import io.urbis.acte.naissance.domain.Mere;
import io.urbis.acte.naissance.domain.Pere;
import io.urbis.acte.naissance.domain.Enfant;
import io.urbis.acte.naissance.domain.Jugement;
import io.urbis.acte.naissance.domain.Declarant;
import io.urbis.acte.naissance.domain.Interprete;
import io.urbis.acte.naissance.domain.Temoins;
import io.urbis.acte.naissance.domain.Operation;
import io.urbis.acte.naissance.domain.TypePiece;
import io.urbis.acte.naissance.domain.TypeNaissance;
import io.urbis.acte.naissance.domain.StatutActeNaissance;
import io.urbis.acte.naissance.dto.ActeNaissanceDto;
import io.urbis.acte.naissance.domain.ModeDeclaration;
import io.urbis.param.domain.OfficierEtatCivil;
import io.urbis.param.service.LocaliteService;
import io.urbis.registre.domain.Registre;
import io.urbis.registre.domain.StatutRegistre;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
import javax.validation.ValidationException;
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
    LocaliteService localiteService;
    
    
    public ActeNaissanceDto findById(@NotBlank String id){
        return Optional.ofNullable(ActeNaissance.findById(id))
                .map(p -> (ActeNaissance)p)
                .map(this::mapToDto)
                .orElseThrow(() -> new WebApplicationException("acte naissance not found",Response.status(Response.Status.NOT_FOUND).build()));
       
    }
    
    
    public String creer(@NotNull ActeNaissanceDto acteNaissanceDto) throws SQLException{
        
        log.infof("-- DECLARE: %s", acteNaissanceDto.getEnfantNom() + " " + acteNaissanceDto.getEnfantPrenoms());
        log.infof("-- REGISTRE ID: %s", acteNaissanceDto.getRegistreID());
        log.infof("-- OFFICIER ID: %s", acteNaissanceDto.getOfficierEtatCivilID());
        
        ActeNaissance acte = new ActeNaissance(new Enfant(), new Jugement(), new Pere(), 
                new Mere(), new Declarant(), new Interprete(), new Temoins());
        
        Registre registre = Registre.findById(acteNaissanceDto.getRegistreID());
        if(registre == null){
           throw new EntityNotFoundException("Registre not found");
           
        }
        if(registre.statut != StatutRegistre.VALIDE){
            throw new ValidationException("impossible de créer l'acte, le registre n'a pas le statut VALID");
        }
        
        OfficierEtatCivil officier = OfficierEtatCivil.findById(acteNaissanceDto.getOfficierEtatCivilID());
        if(officier == null){
           throw new EntityNotFoundException("Officier not found");
           //throw new WebApplicationException("Officier not found", Response.Status.NOT_FOUND);
       }
        
        Operation op = Operation.fromString(acteNaissanceDto.getOperation());
        validerActe(registre, acteNaissanceDto,op);
        
        acte.officierEtatCivil = officier;
        acte.registre = registre;
        
        acte.numero = acteNaissanceDto.getNumero();
        
        if(acteNaissanceDto.getDateDeclaration() != null){
            acte.dateDeclaration = acteNaissanceDto.getDateDeclaration();
            
        }
        
        
        if(acteNaissanceDto.getDateDressage() != null ){
            acte.dateDressage = acteNaissanceDto.getDateDressage();
        }
        
       // acte.dateEnregistrement = DateTimeUtils.fromStringToDateTime(acteNaissanceDto.getDateEnregistrement());
        if(acteNaissanceDto.getEnfantDateNaissance() != null){
            acte.enfant.dateNaissance = acteNaissanceDto.getEnfantDateNaissance() ;
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
        
        if(acteNaissanceDto.getJugementDate() != null){
            acte.jugement.date = acteNaissanceDto.getJugementDate(); 
        }
        
        acte.jugement.numero = acteNaissanceDto.getJugementNumero();
        acte.jugement.tribunal = acteNaissanceDto.getJugementTribunal();
        
        if(acteNaissanceDto.getMereDateDeces() != null){
            acte.mere.dateDeces = acteNaissanceDto.getMereDateDeces();
        }
        if(acteNaissanceDto.getMereDateNaissance() != null){
            acte.mere.dateNaissance = acteNaissanceDto.getMereDateNaissance();
        }
        if(acteNaissanceDto.getMereDatePiece() != null){
            acte.mere.datePiece = acteNaissanceDto.getMereDatePiece();
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
        
        if(acteNaissanceDto.getPereDateDeces() != null){
             acte.pere.dateDeces = acteNaissanceDto.getPereDateDeces();
        }
        if(acteNaissanceDto.getPereDateNaissance() != null){
            acte.pere.dateNaissance = acteNaissanceDto.getPereDateNaissance();
        }
        if(acteNaissanceDto.getPereDatePiece() != null){
            acte.pere.datePiece = acteNaissanceDto.getPereDatePiece();
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
        if(acteNaissanceDto.getDeclarantDatePiece() != null){
            acte.declarant.dateNaissance = acteNaissanceDto.getDeclarantDateNaissance();
        }
        if(acteNaissanceDto.getDeclarantDatePiece() != null){
            acte.declarant.datePiece = acteNaissanceDto.getDeclarantDatePiece();
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
        
        if(acteNaissanceDto.getInterpreteDateNaissance() != null){
            acte.interprete.dateNaissance = acteNaissanceDto.getInterpreteDateNaissance();
        }
        
        acte.interprete.domicile = acteNaissanceDto.getInterpreteDomicile();
        acte.interprete.langue = acteNaissanceDto.getInterpreteLangue();
        acte.interprete.nom = acteNaissanceDto.getInterpreteNom();
        acte.interprete.prenoms = acteNaissanceDto.getInterpretePrenoms();
        acte.interprete.profession = acteNaissanceDto.getInterpreteProfession();
        
        if(acteNaissanceDto.getTemoinPremierDateNaissance() != null){
            acte.temoins.premierDateNaissance = acteNaissanceDto.getTemoinPremierDateNaissance();
        }
        
        acte.temoins.premierDomicile = acteNaissanceDto.getTemoinPremierDomicile();
        acte.temoins.premierNom = acteNaissanceDto.getTemoinPremierNom();
        acte.temoins.premierPrenoms = acteNaissanceDto.getTemoinPremierPrenoms();
        acte.temoins.premierProfession = acteNaissanceDto.getTemoinPremierProfession();
        
        if(acteNaissanceDto.getTemoinDeuxiemeDateNaissance() != null){
            acte.temoins.deuxiemeDateNaissance = acteNaissanceDto.getTemoinDeuxiemeDateNaissance();
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
        
        acte.extraitTexte = new javax.sql.rowset.serial.SerialClob(extraitTexte(acte).toCharArray());
        acte.copieTexte = new javax.sql.rowset.serial.SerialClob((copieText(acte).toCharArray()));
        
        acte.persist();
        
        Operation operation = Operation.fromString(acteNaissanceDto.getOperation());
        
        if(operation == Operation.DECLARATION_JUGEMENT){
            //incrementer l'index du registre
            if(registre.numeroProchainActe == acteNaissanceDto.getNumero()){
                registre.numeroProchainActe += 1;
            }else{
                registre.numeroProchainActe = acteNaissanceDto.getNumero() + 1;
            }
            
        }
        
        creerMentions(acteNaissanceDto, acte);
       
       
        return acte.id;
    }
    
    public void creerMentions(ActeNaissanceDto acteNaissanceDto,ActeNaissance acte){
        acteNaissanceDto.getMentionMariageDtos().forEach(mm -> {
            mm.setActeNaissanceID(acte.id);
            mentionMariageService.createMention(mm);
        });
        
        acteNaissanceDto.getMentionAdoptionDtos().forEach(ma -> {
            ma.setActeNaissanceID(acte.id);
            mentionAdoptionService.createMention(ma);
        });
        
        acteNaissanceDto.getMentionDecesDtos().forEach(mm -> {
            mm.setActeNaissanceID(acte.id);
            mentionDecesService.createMention(mm);
        });
        
        acteNaissanceDto.getMentionDissolutionMariageDtos().forEach(mm -> {
            mm.setActeNaissanceID(acte.id);
            mentionDissolutionMariageService.createMention(mm);
        });
        
        acteNaissanceDto.getMentionLegitimationDtos().forEach(mm -> {
            mm.setActeNaissanceID(acte.id);
            mentionLegitimationService.createMention(mm);
        });
        
        acteNaissanceDto.getMentionReconnaissanceDtos().forEach(mm -> {
            mm.setActeNaissanceID(acte.id);
            mentionReconnaissanceService.createMention(mm);
        });
        
        acteNaissanceDto.getMentionRectificationDtos().forEach(mm -> {
            mm.setActeNaissanceID(acte.id);
            mentionRectificationService.createMention(mm);
        });
    }
    
    public void modifier(@NotBlank String id,@NotNull ActeNaissanceDto acteNaissanceDto) throws SQLException{
        
        ActeNaissance acte = ActeNaissance.findById(id);
        acte.enfant =  new Enfant();
        acte.jugement = new Jugement(); 
        acte.pere = new Pere(); 
        acte.mere =  new Mere();
        acte.declarant = new Declarant();
        acte.interprete = new Interprete();
        acte.temoins = new Temoins();
        
        
        Registre registre = Registre.findById(acteNaissanceDto.getRegistreID());
        if(registre == null){
            throw  new EntityNotFoundException("Registre not found");
        }
        
        if(registre.statut != StatutRegistre.VALIDE){
            throw new ValidationException("le registre n'a pas le statut 'validé'");
        }
                
        OfficierEtatCivil officier = OfficierEtatCivil.findById(acteNaissanceDto.getOfficierEtatCivilID());
        if(officier == null){
            throw  new EntityNotFoundException("OfficierEtatCivil not found");
        }
        
        
        
        Operation op = Operation.fromString(acteNaissanceDto.getOperation());
        validerActe(registre, acteNaissanceDto,op);
        
        acte.officierEtatCivil = officier;
        acte.registre = registre;
        
        log.infof("-- NUMERO DTO: %d", acteNaissanceDto.getNumero());
        
        acte.numero = acteNaissanceDto.getNumero();
        acte.statut = StatutActeNaissance.fromString(acteNaissanceDto.getStatut());
        
        if(acteNaissanceDto.getDateDeclaration() != null){
            acte.dateDeclaration = acteNaissanceDto.getDateDeclaration();
            
        }
       
        if(acteNaissanceDto.getDateDressage() != null ){
            acte.dateDressage = acteNaissanceDto.getDateDressage();
        }
        
       // acte.dateEnregistrement = DateTimeUtils.fromStringToDateTime(acteNaissanceDto.getDateEnregistrement());
        if(acteNaissanceDto.getEnfantDateNaissance() != null){
            acte.enfant.dateNaissance = acteNaissanceDto.getEnfantDateNaissance() ;
         
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
        
                
        if(acteNaissanceDto.getJugementDate() != null){
            acte.jugement.date = acteNaissanceDto.getJugementDate();
        }
        
        Optional.ofNullable(acte.jugement).ifPresent(j -> {
            acte.jugement.numero = acteNaissanceDto.getJugementNumero();
            acte.jugement.tribunal = acteNaissanceDto.getJugementTribunal();
        });
        
        
        if(acteNaissanceDto.getMereDateDeces() != null){
            acte.mere.dateDeces = acteNaissanceDto.getMereDateDeces();
        }
        if(acteNaissanceDto.getMereDateNaissance() != null){
            acte.mere.dateNaissance = acteNaissanceDto.getMereDateNaissance();
        }
        if(acteNaissanceDto.getMereDatePiece() != null){
            acte.mere.datePiece = acteNaissanceDto.getMereDatePiece();
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
        
        if(acteNaissanceDto.getPereDateDeces() != null){
             acte.pere.dateDeces = acteNaissanceDto.getPereDateDeces();
        }
        if(acteNaissanceDto.getPereDateNaissance() != null){
            acte.pere.dateNaissance = acteNaissanceDto.getPereDateNaissance();
        }
        if(acteNaissanceDto.getPereDatePiece() != null){
            acte.pere.datePiece = acteNaissanceDto.getPereDatePiece();
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
        if(acteNaissanceDto.getDeclarantDatePiece() != null){
            acte.declarant.dateNaissance = acteNaissanceDto.getDeclarantDateNaissance();
        }
        if(acteNaissanceDto.getDeclarantDatePiece() != null){
            acte.declarant.datePiece = acteNaissanceDto.getDeclarantDatePiece();
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
        
        if(acteNaissanceDto.getInterpreteDateNaissance() != null){
            acte.interprete.dateNaissance = acteNaissanceDto.getInterpreteDateNaissance();
        }
        
        acte.interprete.domicile = acteNaissanceDto.getInterpreteDomicile();
        acte.interprete.langue = acteNaissanceDto.getInterpreteLangue();
        acte.interprete.nom = acteNaissanceDto.getInterpreteNom();
        acte.interprete.prenoms = acteNaissanceDto.getInterpretePrenoms();
        acte.interprete.profession = acteNaissanceDto.getInterpreteProfession();
        
        if(acteNaissanceDto.getTemoinPremierDateNaissance() != null){
            acte.temoins.premierDateNaissance = acteNaissanceDto.getTemoinPremierDateNaissance();
        }
        
        acte.temoins.premierDomicile = acteNaissanceDto.getTemoinPremierDomicile();
        acte.temoins.premierNom = acteNaissanceDto.getTemoinPremierNom();
        acte.temoins.premierPrenoms = acteNaissanceDto.getTemoinPremierPrenoms();
        acte.temoins.premierProfession = acteNaissanceDto.getTemoinPremierProfession();
        
        if(acteNaissanceDto.getTemoinDeuxiemeDateNaissance() != null){
            acte.temoins.deuxiemeDateNaissance = acteNaissanceDto.getTemoinDeuxiemeDateNaissance();
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
        
             
        acte.extraitTexte = new javax.sql.rowset.serial.SerialClob(extraitTexte(acte).toCharArray());
        acte.copieTexte = new javax.sql.rowset.serial.SerialClob((copieText(acte).toCharArray()));
       
        
    }
    
    
    
    public void modifierMentions(ActeNaissanceDto acteNaissanceDto,ActeNaissance acte){
        //mariage
        Set<MentionMariageDto> dm = new HashSet<>(mentionMariageService.findByActeNaissance(acte.id));
        dm.removeAll(acteNaissanceDto.getMentionMariageDtos());
        
        dm.forEach(mm -> {
            mentionMariageService.deleteMention(mm.getId());
        });
        
        acteNaissanceDto.getMentionMariageDtos().forEach(mm -> {
            mm.setActeNaissanceID(acte.id);
            mentionMariageService.createMention(mm);
        });
        
        //adoption
        Set<MentionAdoptionDto> da = new HashSet<>(mentionAdoptionService.findByActeNaissance(acte.id));
        da.removeAll(acteNaissanceDto.getMentionAdoptionDtos());
        
        da.forEach(mm -> {
            mentionAdoptionService.deleteMention(mm.getId());
        });
        
        acteNaissanceDto.getMentionAdoptionDtos().forEach(mm -> {
            mm.setActeNaissanceID(acte.id);
            mentionAdoptionService.createMention(mm);
        });
        
        //dissolution
        Set<MentionDissolutionMariageDto> dd = new HashSet<>(mentionDissolutionMariageService.findByActeNaissance(acte.id));
        dd.removeAll(acteNaissanceDto.getMentionDissolutionMariageDtos());
        
        dd.forEach(mm -> {
            mentionDissolutionMariageService.deleteMention(mm.getId());
        });
        
        acteNaissanceDto.getMentionDissolutionMariageDtos().forEach(mm -> {
            mm.setActeNaissanceID(acte.id);
            mentionDissolutionMariageService.createMention(mm);
        });
        
        //legitimation
        Set<MentionLegitimationDto> dl = new HashSet<>(mentionLegitimationService.findByActeNaissance(acte.id));
        dl.removeAll(acteNaissanceDto.getMentionLegitimationDtos());
        
        dl.forEach(mm -> {
            mentionLegitimationService.deleteMention(mm.getId());
        });
        
        acteNaissanceDto.getMentionLegitimationDtos().forEach(mm -> {
            mm.setActeNaissanceID(acte.id);
            mentionLegitimationService.createMention(mm);
        });
        
        //reconnaissance
        Set<MentionReconnaissanceDto> dr = new HashSet<>(mentionReconnaissanceService.findByActeNaissance(acte.id));
        dr.removeAll(acteNaissanceDto.getMentionReconnaissanceDtos());
        
        dr.forEach(mm -> {
            mentionReconnaissanceService.deleteMention(mm.getId());
        });
        
        acteNaissanceDto.getMentionReconnaissanceDtos().forEach(mm -> {
            mm.setActeNaissanceID(acte.id);
            mentionReconnaissanceService.createMention(mm);
        });
        
        //rectification
        Set<MentionRectificationDto> dre = new HashSet<>(mentionRectificationService.findByActeNaissance(acte.id));
        dre.removeAll(acteNaissanceDto.getMentionRectificationDtos());
        
        dre.forEach(mm -> {
            mentionReconnaissanceService.deleteMention(mm.getId());
        });
        
        acteNaissanceDto.getMentionRectificationDtos().forEach(mm -> {
            mm.setActeNaissanceID(acte.id);
            mentionRectificationService.createMention(mm);
        });
        
        //décès
        Set<MentionDecesDto> ddc = new HashSet<>(mentionDecesService.findByActeNaissance(acte.id));
        ddc.removeAll(acteNaissanceDto.getMentionDecesDtos());
        
        ddc.forEach(mm -> {
            mentionDecesService.deleteMention(mm.getId());
        });
        
        acteNaissanceDto.getMentionDecesDtos().forEach(mm -> {
            mm.setActeNaissanceID(acte.id);
            mentionDecesService.createMention(mm);
        });
        
        
       
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
    
    public String copieText(ActeNaissance acte){
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
        log.infof("||-- REGISTRE ID : %s", registreID);
        Registre registre = Registre.findById(registreID);
        
        if(registre == null){
            throw new WebApplicationException("Registre not found", Response.Status.NOT_FOUND);
        }
        
        PanacheQuery<ActeNaissance>  query = ActeNaissance.find("registre",Sort.by("numero").descending(),registre);
        
        return query.stream().map(this::mapToDto).collect(Collectors.toList());
        
        //return List.of();
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
            dto.setDateDeclaration(acte.dateDeclaration);
        }
        if(acte.dateDressage != null){
            dto.setDateDressage(acte.dateDressage);
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
        
        Optional.ofNullable(acte.declarant).map(d -> d.datePiece).ifPresent(dp -> {
            dto.setDeclarantDatePiece(dp);
        });
        
        Optional.ofNullable(acte.declarant).map(d -> d.dateNaissance).ifPresent(dn -> {
            dto.setDeclarantDateNaissance(dn);
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
        
        Optional.ofNullable(acte.enfant).map(e -> e.dateNaissance).ifPresent(dn -> {
            dto.setEnfantDateNaissance(dn); 
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
        
        Optional.ofNullable(acte.interprete).map(i -> i.dateNaissance).ifPresent(di -> {
            dto.setInterpreteDateNaissance(di);
        });
        
        Optional.ofNullable(acte.jugement).ifPresent(j -> {
            dto.setJugementNumero(j.numero);
            dto.setJugementTribunal(j.tribunal);
        
        });
        
        Optional.ofNullable(acte.jugement).map(j -> j.date).ifPresent(dj -> {
            dto.setJugementDate(dj);
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
        
         Optional.ofNullable(acte.mere).map(m -> m.dateDeces).ifPresent(de -> {
             dto.setMereDateDeces(de);
         });
       
        Optional.ofNullable(acte.mere).map(m -> m.dateNaissance).ifPresent(de -> {
             dto.setMereDateNaissance(de);
         });
        
        Optional.ofNullable(acte.mere).map(m -> m.datePiece).ifPresent(de -> {
             dto.setMereDatePiece(de);
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
        
         Optional.ofNullable(acte.pere).map(p -> p.dateDeces).ifPresent(ds -> {
             dto.setPereDateDeces(ds);
         });
       
        Optional.ofNullable(acte.pere).map(p -> p.dateNaissance).ifPresent(ds -> {
             dto.setPereDateNaissance(ds);
         });
        
        Optional.ofNullable(acte.pere).map(p -> p.datePiece).ifPresent(ds -> {
             dto.setPereDatePiece(ds);
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
        
        Optional.ofNullable(acte.temoins).map(t -> t.premierDateNaissance).ifPresent(dp -> {
            dto.setTemoinPremierDateNaissance(dp);
        });
        
        Optional.ofNullable(acte.temoins).map(t -> t.deuxiemeDateNaissance).ifPresent(dd -> {
            dto.setTemoinPremierDateNaissance(dd);
        });
        
        
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
        //dto.setOfficierEtatCivilQualite(acte.officierEtatCivil.qualite);
        dto.setOfficierEtatCivilTitre(acte.officierEtatCivil.titre.name());
        
        
        dto.setRegistreNumero(acte.registre.reference.numero);
        dto.setRegistreAnnee(acte.registre.reference.annee);
        
        dto.setMentionMariageDtos(mentionMariageService.findByActeNaissance(acte.id));
        dto.setMentionAdoptionDtos(mentionAdoptionService.findByActeNaissance(acte.id));
        dto.setMentionDecesDtos(mentionDecesService.findByActeNaissance(acte.id));
        dto.setMentionDissolutionMariageDtos(mentionDissolutionMariageService.findByActeNaissance(acte.id));
        dto.setMentionLegitimationDtos(mentionLegitimationService.findByActeNaissance(acte.id));
        dto.setMentionReconnaissanceDtos(mentionReconnaissanceService.findByActeNaissance(acte.id));
        dto.setMentionRectificationDtos(mentionRectificationService.findByActeNaissance(acte.id));
        
        return dto;
    }
    
    public int numeroActe(@NotBlank String registreID){
        Registre registre = Registre.findById(registreID);
        if(registre == null){
            throw new WebApplicationException("Registre not found", Response.Status.NOT_FOUND);
        }
        
        while(numeroExist(registre, registre.numeroProchainActe)){
            registre.numeroProchainActe += 1;
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
    
    
    public boolean supprimer(String id){
        return ActeNaissance.deleteById(id);
    }
}