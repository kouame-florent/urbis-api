/**
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.acte.naissance.service;

import io.agroal.api.AgroalDataSource;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Sort;
import io.urbis.acte.naissance.dto.MentionAdoptionDto;
import io.urbis.acte.naissance.dto.MentionDecesDto;
import io.urbis.acte.naissance.dto.MentionDissolutionMariageDto;
import io.urbis.acte.naissance.dto.MentionLegitimationDto;
import io.urbis.acte.naissance.dto.MentionMariageDto;
import io.urbis.acte.naissance.dto.MentionReconnaissanceDto;
import io.urbis.acte.naissance.dto.MentionRectificationDto;
import io.urbis.acte.naissance.domain.ActeNaissance;
import io.urbis.common.domain.Sexe;
import io.urbis.common.domain.Nationalite;
import io.urbis.acte.naissance.domain.Mere;
import io.urbis.acte.naissance.domain.Pere;
import io.urbis.acte.naissance.domain.Enfant;
import io.urbis.acte.naissance.domain.Jugement;
import io.urbis.acte.naissance.domain.Declarant;
import io.urbis.acte.naissance.domain.Interprete;
import io.urbis.acte.naissance.domain.LienDeclarant;
import io.urbis.acte.naissance.domain.Temoins;
import io.urbis.acte.naissance.domain.Operation;
import io.urbis.common.domain.TypePiece;
import io.urbis.acte.naissance.domain.TypeNaissance;
import io.urbis.acte.naissance.domain.StatutActeNaissance;
import io.urbis.acte.naissance.dto.ActeNaissanceDto;
import io.urbis.acte.naissance.domain.ModeDeclaration;
import io.urbis.acte.naissance.dto.MentionAnnulationDto;
import io.urbis.demande.domain.Demande;
import io.urbis.demande.domain.Demandeur;
import io.urbis.demande.domain.StatutActe;
import io.urbis.demande.domain.StatutDemande;
import io.urbis.demande.dto.DemandeDto;
import io.urbis.demande.service.Helper;
import io.urbis.param.domain.OfficierEtatCivil;
import io.urbis.registre.domain.Registre;
import io.urbis.registre.domain.StatutRegistre;
import io.urbis.registre.domain.TypeRegistre;
import io.urbis.security.service.AuthenticationContext;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.ValidationException;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.jboss.logging.Logger;

/**
 *
 * @author florent
 */
@ApplicationScoped
@Transactional
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
    MentionAnnulationService mentionAnnulationService;
    
    @Inject
    ActeNaissanceEtatService acteNaissanceEtatService;
    
       
    @Inject
    AgroalDataSource defaultDataSource;
    
    @Inject
    AuthenticationContext authenticationContext;
    
    @Inject
    EntityManager em;
    
    @Inject
    Helper helper;
    
    final long MAX_DELAI = 3;
   
    
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
        log.infof("-- DECLARANT LIEN: %s", acteNaissanceDto.getDeclarantLien());
        
        ActeNaissance acte = new ActeNaissance(new Enfant(), new Jugement(), new Pere(), 
                new Mere(), new Declarant(), new Interprete(), new Temoins());
        acte.updatedBy = authenticationContext.userLogin();
        
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
        acte.nombreCopiesIntegrales = acteNaissanceDto.getNombreCopiesIntegrales();
        acte.nombreExtraits = acteNaissanceDto.getNombreExtraits();
        
        if(acteNaissanceDto.getDateDeclaration() != null){
            acte.dateDeclaration = acteNaissanceDto.getDateDeclaration();
            
        }
        
        
        if(acteNaissanceDto.getDateDressage() != null ){
            acte.dateDressage = acteNaissanceDto.getDateDressage();
        }
        
       // acte.dateEnregistrement = DateTimeUtils.fromStringToDateTime(acteNaissanceDto.getDateEnregistrement());
        if(acteNaissanceDto.getEnfantDateNaissance() != null){
            acte.enfant.dateNaissance = acteNaissanceDto.getEnfantDateNaissance() ;
           // acte.enfant.dateNaissanceLettre = dateEnLettre(acte.enfant.dateNaissance);
           // acte.enfant.heureNaissanceLettre = heureEnLettre(acte.enfant.dateNaissance);
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
        if(acteNaissanceDto.getDeclarantDateNaissance() != null){
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
       // acte.nombreCopiesIntegrales = acteNaissanceDto.getNombreCopiesIntegrales();
       // acte.nombreExtraits = acteNaissanceDto.getNombreExtraits(); 
                
        
        if(acteNaissanceDto.getStatut() != null && !acteNaissanceDto.getStatut().isBlank()){
            acte.statut = StatutActeNaissance.fromString(acteNaissanceDto.getStatut());
        }
        
        if(acteNaissanceDto.getTypeNaissance() != null && !acteNaissanceDto.getTypeNaissance().isBlank()){
            acte.typeNaissance = TypeNaissance.fromString(acteNaissanceDto.getTypeNaissance());
        }
        
        acte.statut = StatutActeNaissance.PROJET;
        
        //acte.extraitTexte = new javax.sql.rowset.serial.SerialClob(extraitTexte(acte).toCharArray());
        //acte.copieTexte = new javax.sql.rowset.serial.SerialClob((copieIntegraleTexte(acte).toCharArray()));
        
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
        acteNaissanceEtatService.creer(acte.id);
        creerDemande(acte, registre);
       
        return acte.id;
    }
    
    private String creerDemande(@NotNull ActeNaissance acte,@NotNull Registre reg){
        
                    
        Demandeur demandeur = new Demandeur();

        Demande demande = new Demande(demandeur);
        
        demande.statutActe = StatutActe.ACTE_PRESENT;
        demande.statutDemande = StatutDemande.PRIS_EN_CHARGE;
        demande.acte = acte;
        
               
        demande.dateHeureDemande = acte.dateDressage;
        demande.dateHeureRdvRetrait = LocalDateTime.now().plusDays(MAX_DELAI);
        demande.dateOuvertureRegistre = reg.dateOuverture;
        demande.demandeur.email = "";
        
        if(acte.declarant.lien.equals(LienDeclarant.PERE.name())){
            demande.demandeur.nom = acte.pere.nom;
            demande.demandeur.prenoms = acte.pere.prenoms;
            demande.demandeur.numeroTelephone = "";
            demande.demandeur.numeroPiece = acte.pere.numeroPiece;
            
            demande.demandeur.qualite = acte.declarant.lien;
            demande.demandeur.typePiece = acte.pere.typePiece;
        
        }
        
        if(acte.declarant.lien.equals(LienDeclarant.MERE.name())){
            demande.demandeur.nom = acte.mere.nom;
            demande.demandeur.prenoms = acte.mere.prenoms;
            demande.demandeur.numeroTelephone = "";
            demande.demandeur.numeroPiece = acte.mere.numeroPiece;
            
            demande.demandeur.qualite = acte.declarant.lien;
            demande.demandeur.typePiece = acte.mere.typePiece;
        
        }
        
        if(acte.declarant.lien.equals(LienDeclarant.AUTRES.name())){
            demande.demandeur.nom = acte.declarant.nom;
            demande.demandeur.prenoms = acte.declarant.prenoms;
            demande.demandeur.numeroTelephone = "";
            demande.demandeur.numeroPiece = acte.declarant.numeroPiece;
            
            demande.demandeur.qualite = acte.declarant.lien;
            demande.demandeur.typePiece = acte.declarant.typePiece;
        
        }
        
        

        demande.nombreCopies = acte.nombreCopiesIntegrales;
        demande.nombreExtraits = acte.nombreExtraits;
        demande.numero = helper.numeroDemande();
        demande.numeroActe = acte.numero;
        demande.typeRegistre = TypeRegistre.fromString(reg.typeRegistre.name());
        demande.updatedBy = authenticationContext.userLogin();

        demande.persist();
        
        
        return demande.id;
        
        
    
    }

    public void creerMentions(ActeNaissanceDto acteNaissanceDto,ActeNaissance acte){
        
        log.infof("-- MENTIONS MARIAGE SIZE: %d", acteNaissanceDto.getMentionMariageDtos().size());
        
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
        
        acteNaissanceDto.getMentionAnnulationDtos().forEach(mm -> {
            mm.setActeNaissanceID(acte.id);
            mentionAnnulationService.createMention(mm);
        });
    }
    
    
    public void valider(@NotBlank String id){
    
        ActeNaissance acte = ActeNaissance.findById(id);
        acte.statut = StatutActeNaissance.VALIDE;
    }

    public void modifier(@NotBlank String id, @NotNull ActeNaissanceDto acteNaissanceDto) throws SQLException{
        
        ActeNaissance acte = ActeNaissance.findById(id);
        acte.updatedBy = authenticationContext.userLogin();
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
       // acte.nombreCopiesIntegrales = acteNaissanceDto.getNombreCopiesIntegrales();
       // acte.nombreExtraits = acteNaissanceDto.getNombreExtraits();
                
        
        if(acteNaissanceDto.getStatut() != null && !acteNaissanceDto.getStatut().isBlank()){
            acte.statut = StatutActeNaissance.fromString(acteNaissanceDto.getStatut());
        }
        
        if(acteNaissanceDto.getTypeNaissance() != null && !acteNaissanceDto.getTypeNaissance().isBlank()){
            acte.typeNaissance = TypeNaissance.fromString(acteNaissanceDto.getTypeNaissance());
        }
        
             
        //acte.extraitTexte = new javax.sql.rowset.serial.SerialClob(extraitTexte(acte).toCharArray());
        //acte.copieTexte = new javax.sql.rowset.serial.SerialClob((copieIntegraleTexte(acte).toCharArray()));
       
        modifierMentions(acteNaissanceDto, acte);
        acteNaissanceEtatService.modifier(acte.id);
    }
    
    
    public void modifierMentions(ActeNaissanceDto acteNaissanceDto,ActeNaissance acte){
        
        log.infof("-- MENTIONS MARIAGE SIZE: %d", acteNaissanceDto.getMentionMariageDtos().size());
        
        //mariage
        Set<MentionMariageDto> dm = new HashSet<>(mentionMariageService.findByActeNaissance(acte.id));
        dm.removeAll(acteNaissanceDto.getMentionMariageDtos()); //différence entre le Set de la BD et le Set envoyé qui donne les elements supprimés
        
        log.infof("-- DIFFERENCE SET SIZE: %d", dm.size());
        
        dm.forEach(mm -> {
            mentionMariageService.deleteMention(mm.getId());
        });
        
        acteNaissanceDto.getMentionMariageDtos().forEach(mm -> {
            mm.setActeNaissanceID(acte.id);
            mentionMariageService.modifierMention(mm);
        });
        
        //adoption
        Set<MentionAdoptionDto> da = new HashSet<>(mentionAdoptionService.findByActeNaissance(acte.id));
        da.removeAll(acteNaissanceDto.getMentionAdoptionDtos());
        
        da.forEach(mm -> {
            mentionAdoptionService.deleteMention(mm.getId());
        });
        
        acteNaissanceDto.getMentionAdoptionDtos().forEach(mm -> {
            mm.setActeNaissanceID(acte.id);
            mentionAdoptionService.modifierMention(mm);
        });
        
        //dissolution
        Set<MentionDissolutionMariageDto> dd = new HashSet<>(mentionDissolutionMariageService.findByActeNaissance(acte.id));
        dd.removeAll(acteNaissanceDto.getMentionDissolutionMariageDtos());
        
        dd.forEach(mm -> {
            mentionDissolutionMariageService.deleteMention(mm.getId());
        });
        
        acteNaissanceDto.getMentionDissolutionMariageDtos().forEach(mm -> {
            mm.setActeNaissanceID(acte.id);
            mentionDissolutionMariageService.modifierMention(mm);
        });
        
        //legitimation
        Set<MentionLegitimationDto> dl = new HashSet<>(mentionLegitimationService.findByActeNaissance(acte.id));
        dl.removeAll(acteNaissanceDto.getMentionLegitimationDtos());
        
        dl.forEach(mm -> {
            mentionLegitimationService.deleteMention(mm.getId());
        });
        
        acteNaissanceDto.getMentionLegitimationDtos().forEach(mm -> {
            mm.setActeNaissanceID(acte.id);
            mentionLegitimationService.modifierMention(mm);
        });
        
        //reconnaissance
        Set<MentionReconnaissanceDto> dr = new HashSet<>(mentionReconnaissanceService.findByActeNaissance(acte.id));
        dr.removeAll(acteNaissanceDto.getMentionReconnaissanceDtos());
        
        dr.forEach(mm -> {
            mentionReconnaissanceService.deleteMention(mm.getId());
        });
        
        acteNaissanceDto.getMentionReconnaissanceDtos().forEach(mm -> {
            mm.setActeNaissanceID(acte.id);
            mentionReconnaissanceService.modifierMention(mm);
        });
        
        //rectification
        Set<MentionRectificationDto> dre = new HashSet<>(mentionRectificationService.findByActeNaissance(acte.id));
        dre.removeAll(acteNaissanceDto.getMentionRectificationDtos());
        
        dre.forEach(mm -> {
            mentionReconnaissanceService.deleteMention(mm.getId());
        });
        
        acteNaissanceDto.getMentionRectificationDtos().forEach(mm -> {
            mm.setActeNaissanceID(acte.id);
            mentionRectificationService.modifierMention(mm);
        });
        
        //décès
        Set<MentionDecesDto> ddc = new HashSet<>(mentionDecesService.findByActeNaissance(acte.id));
        ddc.removeAll(acteNaissanceDto.getMentionDecesDtos());
        
        ddc.forEach(mm -> {
            mentionDecesService.deleteMention(mm.getId());
        });
        
        acteNaissanceDto.getMentionDecesDtos().forEach(mm -> {
            mm.setActeNaissanceID(acte.id);
            mentionDecesService.modifierMention(mm);
        });
        
        //annulation
        Set<MentionAnnulationDto> mans = new HashSet<>(mentionAnnulationService.findByActeNaissance(acte.id));
        ddc.removeAll(acteNaissanceDto.getMentionAnnulationDtos());
        
        mans.forEach(mm -> {
            mentionAnnulationService.deleteMention(mm.getId());
        });
        
        acteNaissanceDto.getMentionAnnulationDtos().forEach(mm -> {
            mm.setActeNaissanceID(acte.id);
            mentionAnnulationService.modifierMention(mm);
        });
        
        
       
    }
    
  
   
    public void validerActe(Registre registre,ActeNaissanceDto dto,Operation operation){
        if(operation == Operation.DECLARATION_JUGEMENT){
            verifierNumero(registre, dto);
        }
        
        validerBorneInferieure(registre, dto.getNumero());
        validerBorneSuperieure(registre, dto.getNumero());
        //validerNombreDefeuillets(registre);
    }
    
   
    public void verifierNumero(Registre registre,ActeNaissanceDto dto){
        while(numeroExist(registre, dto.getNumero())){
            dto.setNumero(dto.getNumero() + 1);
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
    
    
    public List<ActeNaissanceDto> findWithFilters(int offset,int pageSize,@NotNull String registreID){
        log.infof("||-- REGISTRE ID : %s", registreID);
        Registre registre = Registre.findById(registreID);
        
        if(registre == null){
            throw new WebApplicationException("ActeNaissance not found", Response.Status.NOT_FOUND);
        }
        
        
        PanacheQuery<ActeNaissance>  query = ActeNaissance.find("registre",Sort.by("numero").descending(),registre);
        PanacheQuery<ActeNaissance> rq =  query.range(offset, offset + (pageSize-1));
        
        return rq.stream().map(this::mapToDto).collect(Collectors.toList());
        
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
        dto.setMentionAnnulationDtos(mentionAnnulationService.findByActeNaissance(acte.id));
        
        
        
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
        if(ActeNaissanceDto.count() > 0){
            return registre.numeroProchainActe + 1;
        }else{
            return registre.numeroPremierActe;
        }
        */
      
    }
    

    public boolean supprimer(String id){
        return ActeNaissance.deleteById(id);
    }
    
    public String printCopie(String acteID) throws SQLException, JRException, FileNotFoundException{
       return doPrint(acteID, "/META-INF/resources/report/acte_naissance_ci.jasper");
       
   }
    
    public String print(String acteID) throws SQLException, JRException, FileNotFoundException{
        
        return doPrint(acteID, "/META-INF/resources/report/acte_naissance.jasper");
              
   }
    
    
   private String doPrint(String acteID,String resource) throws SQLException, JRException, FileNotFoundException{
     
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream reportStream = loader.getResourceAsStream(resource);
        log.infof("-- REPORT INPUT: %s", reportStream);
        
        
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("ACTE_NAISSANCE_ID", acteID);
        JasperPrint jasperPrint = JasperFillManager.fillReport(reportStream, parameters, defaultDataSource.getConnection());
       
        JRPdfExporter exporter = new JRPdfExporter();
        

        String reportFilePath = filePath(acteID);
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput( new SimpleOutputStreamExporterOutput(reportFilePath));
        
        exporter.exportReport();
        
        return reportFilePath;
   
   }
   
   
   private String filePath(String acteID){
      ActeNaissance acte = ActeNaissance.findById(acteID);
      if( acte == null){
          throw new EntityNotFoundException("ActeNaissance not found");
      }
      String name = acte.enfant.nom + " " + acte.enfant.prenoms +"-"+ LocalDateTime.now().toString() + ".pdf";
      
      return "/tmp/" + name.replaceAll(" ", "-");
   }
}
