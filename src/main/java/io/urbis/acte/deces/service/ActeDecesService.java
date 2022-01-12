/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.acte.deces.service;

import com.ibm.icu.text.RuleBasedNumberFormat;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Sort;
import io.urbis.acte.deces.domain.Operation;
import io.urbis.acte.deces.dto.ActeDecesDto;
import io.urbis.param.service.LocaliteService;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
import javax.validation.ValidationException;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.jboss.logging.Logger;
import io.urbis.acte.deces.domain.ActeDeces;
import io.urbis.acte.deces.domain.StatutActeDeces;
import io.urbis.acte.deces.domain.Defunt;
import io.urbis.acte.deces.domain.Mere;
import io.urbis.acte.deces.domain.Pere;
import io.urbis.acte.deces.domain.Jugement;
import io.urbis.acte.deces.domain.Declarant;
import io.urbis.acte.deces.domain.Interprete;
import io.urbis.acte.deces.domain.Temoins;
import io.urbis.common.domain.Sexe;
import io.urbis.common.domain.SituationMatrimoniale;
import io.urbis.registre.domain.Registre;
import io.urbis.registre.domain.StatutRegistre;
import io.urbis.param.domain.OfficierEtatCivil;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 *
 * @author florent
 */
@ApplicationScoped
public class ActeDecesService {
    
    @Inject
    Logger log;
    
    
    @Inject
    LocaliteService localiteService;
    
    
    public ActeDecesDto findById(@NotBlank String id){
        return Optional.ofNullable(ActeDeces.findById(id))
                .map(p -> (ActeDeces)p)
                .map(this::mapToDto)
                .orElseThrow(() -> new WebApplicationException("acte naissance not found",Response.status(Response.Status.NOT_FOUND).build()));
       
    }
    
    
    public String creer(@NotNull ActeDecesDto acteDecesDto) throws SQLException{
        
        log.infof("-- DECLARE: %s", acteDecesDto.getDefuntNom() + " " + acteDecesDto.getDefuntPrenoms());
        log.infof("-- REGISTRE ID: %s", acteDecesDto.getRegistreID());
        log.infof("-- OFFICIER ID: %s", acteDecesDto.getOfficierEtatCivilID());
        
        ActeDeces acte = new ActeDeces(new Defunt(), new Jugement(), new Pere(), 
                new Mere(), new Declarant(), new Interprete(), new Temoins());
        
        Registre registre = Registre.findById(acteDecesDto.getRegistreID());
        if(registre == null){
           throw new EntityNotFoundException("Registre not found");
           
        }
        if(registre.statut != StatutRegistre.VALIDE){
            throw new ValidationException("impossible de créer l'acte, le registre n'a pas le statut VALID");
        }
        
        OfficierEtatCivil  officier = OfficierEtatCivil.findById(acteDecesDto.getOfficierEtatCivilID());
        if(officier == null){
           throw new EntityNotFoundException("Officier not found");
           //throw new WebApplicationException("Officier not found", Response.Status.NOT_FOUND);
       }
        
        Operation op = Operation.fromString(acteDecesDto.getOperation());
        validerActe(registre, acteDecesDto,op);
        
        acte.officierEtatCivil = officier;
        acte.registre = registre;
        acte.numero = acteDecesDto.getNumero();
       
        
        if(acteDecesDto.getDateDressage() != null ){
            acte.dateDressage = acteDecesDto.getDateDressage();
        }
        

        if(acteDecesDto.getDefuntDateNaissance() != null){
            acte.defunt.dateNaissance = acteDecesDto.getDefuntDateNaissance() ;
            
        }  
        
        acte.defunt.nom = acteDecesDto.getDefuntNom();
        acte.defunt.prenoms = acteDecesDto.getDefuntPrenoms();
        
        acte.defunt.lieuNaissance = acteDecesDto.getDefuntLieuNaissance();
        acte.defunt.localite = acteDecesDto.getDefuntLocalite();
        
        acte.defunt.profession = acteDecesDto.getDefuntProfession();
        acte.defunt.dateDeces = acteDecesDto.getDefuntDateDeces();
        acte.defunt.lieuDeces = acteDecesDto.getDefuntLieuDeces();
        acte.defunt.domicile = acteDecesDto.getDefuntDomicile();
        acte.defunt.dateNaissance = acteDecesDto.getDefuntDateNaissance();
        acte.defunt.situationMatrimoniale = 
                SituationMatrimoniale.fromString(acteDecesDto.getDefuntSituationMatrimoniale());
       
        if(acteDecesDto.getDefuntSexe() != null && !acteDecesDto.getDefuntSexe().isBlank()){
            acte.defunt.sexe = Sexe.fromString(acteDecesDto.getDefuntSexe());
        } 
        
        if(acteDecesDto.getJugementDate() != null){
            acte.jugement.date = acteDecesDto.getJugementDate(); 
        }
        
        acte.jugement.numero = acteDecesDto.getJugementNumero();
        acte.jugement.tribunal = acteDecesDto.getJugementTribunal();
        
        if(acteDecesDto.getMereDateDeces() != null){
            acte.mere.dateDeces = acteDecesDto.getMereDateDeces();
        }
        if(acteDecesDto.getMereDateNaissance() != null){
            acte.mere.dateNaissance = acteDecesDto.getMereDateNaissance();
        }
        if(acteDecesDto.getMereDatePiece() != null){
            acte.mere.datePiece = acteDecesDto.getMereDatePiece();
        }
        
        acte.mere.decedee = acteDecesDto.isMereDecedee();
        acte.mere.lieuDeces = acteDecesDto.getMereLieuDeces();
        acte.mere.lieuNaissance = acteDecesDto.getMereLieuNaissance();
        acte.mere.lieuPiece = acteDecesDto.getMereLieuPiece();
        acte.mere.localite = acteDecesDto.getMereLocalite();
        
        acte.mere.nom = acteDecesDto.getMereNom();
        acte.mere.numeroPiece = acteDecesDto.getMereNumeroPiece();
        acte.mere.prenoms = acteDecesDto.getMerePrenoms();
        acte.mere.profession = acteDecesDto.getMereProfession();
        acte.mere.nomComplet = acte.mere.nom + " " + acteDecesDto.getMerePrenoms();
       
        
        if(acteDecesDto.getPereDateDeces() != null){
             acte.pere.dateDeces = acteDecesDto.getPereDateDeces();
        }
        if(acteDecesDto.getPereDateNaissance() != null){
            acte.pere.dateNaissance = acteDecesDto.getPereDateNaissance();
        }
        if(acteDecesDto.getPereDatePiece() != null){
            acte.pere.datePiece = acteDecesDto.getPereDatePiece();
        }
        
        acte.pere.decedee = acteDecesDto.isPereDecedee();
        acte.pere.lieuDeces = acteDecesDto.getPereLieuDeces();
        acte.pere.lieuNaissance = acteDecesDto.getPereLieuNaissance();
        acte.pere.lieuPiece = acteDecesDto.getPereLieuPiece();
        acte.pere.localite = acteDecesDto.getPereLocalite();
         
        acte.pere.nom = acteDecesDto.getPereNom();
        acte.pere.numeroPiece = acteDecesDto.getPereNumeroPiece();
        acte.pere.prenoms = acteDecesDto.getPerePrenoms();
        acte.pere.profession = acteDecesDto.getPereProfession();
        acte.pere.nomComplet = acte.pere.nom + " " + acte.pere.prenoms;
        
        acte.declarant.lien = acteDecesDto.getDeclarantLien();
        if(acteDecesDto.getDeclarantDatePiece() != null){
            acte.declarant.dateNaissance = acteDecesDto.getDeclarantDateNaissance();
        }
        if(acteDecesDto.getDeclarantDatePiece() != null){
            acte.declarant.datePiece = acteDecesDto.getDeclarantDatePiece();
        }
        
        acte.declarant.lieuNaissance = acteDecesDto.getDeclarantLieuNaissance();
        acte.declarant.lieuPiece = acteDecesDto.getDeclarantLieuPiece();
        acte.declarant.localite = acteDecesDto.getDeclarantLocalite();
        
        acte.declarant.nom = acteDecesDto.getDeclarantNom();
        acte.declarant.numeroPiece = acteDecesDto.getDeclarantNumeroPiece();
        acte.declarant.prenoms = acteDecesDto.getDeclarantPrenoms();
        acte.declarant.profession = acteDecesDto.getDeclarantProfession();
        
                
        if(acteDecesDto.getInterpreteDateNaissance() != null){
            acte.interprete.dateNaissance = acteDecesDto.getInterpreteDateNaissance();
        }
        
        acte.interprete.domicile = acteDecesDto.getInterpreteDomicile();
        acte.interprete.langue = acteDecesDto.getInterpreteLangue();
        acte.interprete.nom = acteDecesDto.getInterpreteNom();
        acte.interprete.prenoms = acteDecesDto.getInterpretePrenoms();
        acte.interprete.profession = acteDecesDto.getInterpreteProfession();
        
        if(acteDecesDto.getTemoinPremierDateNaissance() != null){
            acte.temoins.premierDateNaissance = acteDecesDto.getTemoinPremierDateNaissance();
        }
        
        acte.temoins.premierDomicile = acteDecesDto.getTemoinPremierDomicile();
        acte.temoins.premierNom = acteDecesDto.getTemoinPremierNom();
        acte.temoins.premierPrenoms = acteDecesDto.getTemoinPremierPrenoms();
        acte.temoins.premierProfession = acteDecesDto.getTemoinPremierProfession();
        
        if(acteDecesDto.getTemoinDeuxiemeDateNaissance() != null){
            acte.temoins.deuxiemeDateNaissance = acteDecesDto.getTemoinDeuxiemeDateNaissance();
        }
        
        acte.temoins.deuxiemeDomicile = acteDecesDto.getTemoinDeuxiemeDomicile();
        acte.temoins.deuxiemeNom = acteDecesDto.getTemoinDeuxiemeNom();
        acte.temoins.deuxiemePrenoms = acteDecesDto.getTemoinDeuxiemePrenoms();
        acte.temoins.deuxiemeProfession = acteDecesDto.getTemoinPremierProfession();
        
        
           
        if(acteDecesDto.getStatut() != null && !acteDecesDto.getStatut().isBlank()){
            acte.statut = StatutActeDeces.fromString(acteDecesDto.getStatut());
        }
        
        acte.statut = StatutActeDeces.PROJET;
        
        acte.extraitTexte = new javax.sql.rowset.serial.SerialClob(extraitTexte(acte).toCharArray());
        acte.copieTexte = new javax.sql.rowset.serial.SerialClob((copieText(acte).toCharArray()));
        
        acte.persist();
        
        Operation operation = Operation.fromString(acteDecesDto.getOperation());
        
        if(operation == Operation.DECLARATION_JUGEMENT){
            //incrementer l'index du registre
            if(registre.numeroProchainActe == acteDecesDto.getNumero()){
                registre.numeroProchainActe += 1;
            }else{
                registre.numeroProchainActe = acteDecesDto.getNumero() + 1;
            }
            
        }
       
        return acte.id;
    }
    
   
    public void modifier(@NotBlank String id, @NotNull ActeDecesDto acteDecesDto) throws SQLException{
        
        ActeDeces acte = ActeDeces.findById(id); 
        acte.defunt =  new Defunt();
        acte.jugement = new Jugement(); 
        acte.pere = new Pere(); 
        acte.mere =  new Mere();
        acte.declarant = new Declarant();
        acte.interprete = new Interprete();
        acte.temoins = new Temoins();
        
        
        Registre registre = Registre.findById(acteDecesDto.getRegistreID());
        if(registre == null){
            throw  new EntityNotFoundException("Registre not found");
        }
        
        if(registre.statut != StatutRegistre.VALIDE){
            throw new ValidationException("le registre n'a pas le statut 'validé'");
        }
                
        OfficierEtatCivil officier = OfficierEtatCivil.findById(acteDecesDto.getOfficierEtatCivilID());
        if(officier == null){
            throw  new EntityNotFoundException("OfficierEtatCivil not found");
        }
        
        
        
        Operation op = Operation.fromString(acteDecesDto.getOperation());
        validerActe(registre, acteDecesDto,op);
        
        acte.officierEtatCivil = officier;
        acte.registre = registre;
        
        log.infof("-- NUMERO DTO: %d", acteDecesDto.getNumero());
        
        acte.numero = acteDecesDto.getNumero();
        acte.statut = StatutActeDeces.fromString(acteDecesDto.getStatut());
        
        
        if(acteDecesDto.getDateDressage() != null ){
            acte.dateDressage = acteDecesDto.getDateDressage();
        }
        
       // acte.dateEnregistrement = DateTimeUtils.fromStringToDateTime(acteDecesDto.getDateEnregistrement());
        if(acteDecesDto.getDefuntDateNaissance() != null){
            acte.defunt.dateNaissance = acteDecesDto.getDefuntDateNaissance() ;
         
        }      
        acte.defunt.lieuNaissance = acteDecesDto.getDefuntLieuNaissance();
        acte.defunt.localite = acteDecesDto.getDefuntLocalite();
        
        acte.defunt.nom = acteDecesDto.getDefuntNom();
        acte.defunt.prenoms = acteDecesDto.getDefuntPrenoms();
        
        acte.defunt.profession = acteDecesDto.getDefuntProfession();
        acte.defunt.dateDeces = acteDecesDto.getDefuntDateDeces();
        acte.defunt.lieuDeces = acteDecesDto.getDefuntLieuDeces();
        acte.defunt.domicile = acteDecesDto.getDefuntDomicile();
        acte.defunt.dateNaissance = acteDecesDto.getDefuntDateNaissance();
        acte.defunt.situationMatrimoniale = 
                SituationMatrimoniale.fromString(acteDecesDto.getDefuntSituationMatrimoniale());
        
        if(acteDecesDto.getDefuntSexe() != null && !acteDecesDto.getDefuntSexe().isBlank()){
            acte.defunt.sexe = Sexe.fromString(acteDecesDto.getDefuntSexe());
        }
        
                
        if(acteDecesDto.getJugementDate() != null){
            acte.jugement.date = acteDecesDto.getJugementDate();
        }
        
        Optional.ofNullable(acte.jugement).ifPresent(j -> {
            acte.jugement.numero = acteDecesDto.getJugementNumero();
            acte.jugement.tribunal = acteDecesDto.getJugementTribunal();
        });
        
        
        if(acteDecesDto.getMereDateDeces() != null){
            acte.mere.dateDeces = acteDecesDto.getMereDateDeces();
        }
        if(acteDecesDto.getMereDateNaissance() != null){
            acte.mere.dateNaissance = acteDecesDto.getMereDateNaissance();
        }
        if(acteDecesDto.getMereDatePiece() != null){
            acte.mere.datePiece = acteDecesDto.getMereDatePiece();
        }
        
        acte.mere.lieuDeces = acteDecesDto.getMereLieuDeces();
        acte.mere.lieuNaissance = acteDecesDto.getMereLieuNaissance();
        acte.mere.lieuPiece = acteDecesDto.getMereLieuPiece();
        acte.mere.localite = acteDecesDto.getMereLocalite();
        
       
        
        acte.mere.nom = acteDecesDto.getMereNom();
        acte.mere.numeroPiece = acteDecesDto.getMereNumeroPiece();
        acte.mere.prenoms = acteDecesDto.getMerePrenoms();
        acte.mere.profession = acteDecesDto.getMereProfession();
        acte.mere.nomComplet = acte.mere.nom + " " + acteDecesDto.getMerePrenoms();
       
        
        if(acteDecesDto.getPereDateDeces() != null){
             acte.pere.dateDeces = acteDecesDto.getPereDateDeces();
        }
        if(acteDecesDto.getPereDateNaissance() != null){
            acte.pere.dateNaissance = acteDecesDto.getPereDateNaissance();
        }
        if(acteDecesDto.getPereDatePiece() != null){
            acte.pere.datePiece = acteDecesDto.getPereDatePiece();
        }
        
        acte.pere.lieuDeces = acteDecesDto.getPereLieuDeces();
        acte.pere.lieuNaissance = acteDecesDto.getPereLieuNaissance();
        acte.pere.lieuPiece = acteDecesDto.getPereLieuPiece();
        acte.pere.localite = acteDecesDto.getPereLocalite();
        
       
        acte.pere.nom = acteDecesDto.getPereNom();
        acte.pere.numeroPiece = acteDecesDto.getPereNumeroPiece();
        acte.pere.prenoms = acteDecesDto.getPerePrenoms();
        acte.pere.profession = acteDecesDto.getPereProfession();
        acte.pere.nomComplet = acte.pere.nom + " " + acte.pere.prenoms;
        
        
            
        acte.declarant.lien = acteDecesDto.getDeclarantLien();
        if(acteDecesDto.getDeclarantDatePiece() != null){
            acte.declarant.dateNaissance = acteDecesDto.getDeclarantDateNaissance();
        }
        if(acteDecesDto.getDeclarantDatePiece() != null){
            acte.declarant.datePiece = acteDecesDto.getDeclarantDatePiece();
        }
        
        acte.declarant.lieuNaissance = acteDecesDto.getDeclarantLieuNaissance();
        acte.declarant.lieuPiece = acteDecesDto.getDeclarantLieuPiece();
        acte.declarant.localite = acteDecesDto.getDeclarantLocalite();
        
        acte.declarant.nom = acteDecesDto.getDeclarantNom();
        acte.declarant.numeroPiece = acteDecesDto.getDeclarantNumeroPiece();
        acte.declarant.prenoms = acteDecesDto.getDeclarantPrenoms();
        acte.declarant.profession = acteDecesDto.getDeclarantProfession();
        
        
        if(acteDecesDto.getInterpreteDateNaissance() != null){
            acte.interprete.dateNaissance = acteDecesDto.getInterpreteDateNaissance();
        }
        
        acte.interprete.domicile = acteDecesDto.getInterpreteDomicile();
        acte.interprete.langue = acteDecesDto.getInterpreteLangue();
        acte.interprete.nom = acteDecesDto.getInterpreteNom();
        acte.interprete.prenoms = acteDecesDto.getInterpretePrenoms();
        acte.interprete.profession = acteDecesDto.getInterpreteProfession();
        
        if(acteDecesDto.getTemoinPremierDateNaissance() != null){
            acte.temoins.premierDateNaissance = acteDecesDto.getTemoinPremierDateNaissance();
        }
        
        acte.temoins.premierDomicile = acteDecesDto.getTemoinPremierDomicile();
        acte.temoins.premierNom = acteDecesDto.getTemoinPremierNom();
        acte.temoins.premierPrenoms = acteDecesDto.getTemoinPremierPrenoms();
        acte.temoins.premierProfession = acteDecesDto.getTemoinPremierProfession();
        
        if(acteDecesDto.getTemoinDeuxiemeDateNaissance() != null){
            acte.temoins.deuxiemeDateNaissance = acteDecesDto.getTemoinDeuxiemeDateNaissance();
        }
        
        acte.temoins.deuxiemeDomicile = acteDecesDto.getTemoinDeuxiemeDomicile();
        acte.temoins.deuxiemeNom = acteDecesDto.getTemoinDeuxiemeNom();
        acte.temoins.deuxiemePrenoms = acteDecesDto.getTemoinDeuxiemePrenoms();
        acte.temoins.deuxiemeProfession = acteDecesDto.getTemoinPremierProfession();
          
        
        if(acteDecesDto.getStatut() != null && !acteDecesDto.getStatut().isBlank()){
            acte.statut = StatutActeDeces.fromString(acteDecesDto.getStatut());
        }
        
            
        acte.extraitTexte = new javax.sql.rowset.serial.SerialClob(extraitTexte(acte).toCharArray());
        acte.copieTexte = new javax.sql.rowset.serial.SerialClob((copieText(acte).toCharArray()));
       
        
    }
    
    
    
    
    public String dateNaissanceEnLettre(LocalDateTime localDateTime){
      
        int dayOfMonth = localDateTime.getDayOfMonth();
        String month = localDateTime.getMonth().getDisplayName(TextStyle.FULL, new Locale("fr","FR"));
        int year = localDateTime.getYear();
        
       String laDate ;
        
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
    
    public String extraitTexte(ActeDeces acte){
        StringBuilder sb = new StringBuilder();
       
        return sb.toString();
    }
    
    public String copieText(ActeDeces acte){
        StringBuilder sb = new StringBuilder();
       
        
        return sb.toString();
    }
    
    public void validerActe(Registre registre,ActeDecesDto acte,Operation operation){
        if(operation == Operation.DECLARATION_JUGEMENT){
            verifierNumero(registre, acte);
        }
        
        validerBorneInferieure(registre, acte.getNumero());
        validerBorneSuperieure(registre, acte.getNumero());
        //validerNombreDefeuillets(registre);
    }
    
    
    public void verifierNumero(Registre registre,ActeDecesDto acte){
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
       Optional<ActeDeces> optActe = ActeDeces.find("registre = ?1 AND numero = ?2",
               registre,numeroActe).firstResultOptional();
       
        return optActe.isPresent();
    }
    
    
    public List<ActeDecesDto> findWithFilters(int offset,int pageSize,@NotBlank String registreID){
        log.infof("||-- REGISTRE ID : %s", registreID);
        Registre registre = Registre.findById(registreID);
        
        if(registre == null){
            throw new WebApplicationException("Registre not found", Response.Status.NOT_FOUND);
        }
        
        PanacheQuery<ActeDeces>  query = ActeDeces.find("registre",Sort.by("numero").descending(),registre);
        
        return query.stream().map(this::mapToDto).collect(Collectors.toList());
        
        //return List.of();
    }
    
    public int count(){
        return (int)ActeDeces.count();
    }
    
    
 
    public ActeDecesDto mapToDto(@NotNull ActeDeces acte){
        ActeDecesDto dto = new ActeDecesDto();
        
        dto.setCreated(acte.created);
        dto.setUpdated(acte.updated);
        
        dto.setId(acte.id);
        
        
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
    
        Optional.ofNullable(acte.defunt).ifPresent(a -> {
            dto.setDefuntNom(a.nom);
            dto.setDefuntPrenoms(a.prenoms);
            dto.setDefuntLieuNaissance(a.lieuNaissance);
            dto.setDefuntLocalite(a.localite);
            dto.setDefuntProfession(acte.defunt.profession);
            dto.setDefuntDateDeces(acte.defunt.dateDeces);
            dto.setDefuntLieuDeces(acte.defunt.lieuDeces);
            dto.setDefuntDomicile(acte.defunt.domicile);
            dto.setDefuntDateNaissance(acte.defunt.dateNaissance);
            dto.setDefuntSituationMatrimoniale(acte.defunt.situationMatrimoniale.name());
          
        });
        
        
        
        Optional.ofNullable(acte.defunt).map(e -> e.dateNaissance).ifPresent(dn -> {
            dto.setDefuntDateNaissance(dn); 
        });
        
        
        
        Optional.ofNullable(acte.defunt).map(e -> e.sexe).ifPresent(s -> {
            dto.setDefuntSexe(s.name());
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
        
        
        
        Optional.ofNullable(acte.statut).ifPresent(m -> {
            dto.setStatut(acte.statut.name());
        });
        
       
        
        dto.setNumero(acte.numero);
        dto.setRegistreID(acte.registre.id);
        
        dto.setOfficierEtatCivilID(acte.officierEtatCivil.id);
        dto.setOfficierEtatCivilNom(acte.officierEtatCivil.nom);
        dto.setOfficierEtatCivilPrenoms(acte.officierEtatCivil.prenoms);
        //dto.setOfficierEtatCivilQualite(acte.officierEtatCivil.qualite);
        dto.setOfficierEtatCivilTitre(acte.officierEtatCivil.titre.name());
        
        
        dto.setRegistreNumero(acte.registre.reference.numero);
        dto.setRegistreAnnee(acte.registre.reference.annee);
        
        
        
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
        if(ActeDeces.count() > 0){
            return registre.numeroProchainActe + 1;
        }else{
            return registre.numeroPremierActe;
        }
        */
      
    }
    
    
    public boolean supprimer(String id){
        return ActeDeces.deleteById(id);
    }
}
