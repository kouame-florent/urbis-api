/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.acte.mariage.service;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Sort;
import io.urbis.acte.mariage.domain.ActeMariage;
import io.urbis.acte.mariage.domain.Conjoint;
import io.urbis.acte.mariage.domain.Epouse;
import io.urbis.acte.mariage.domain.Epoux;
import io.urbis.acte.mariage.domain.Mere;
import io.urbis.acte.mariage.domain.Operation;
import io.urbis.acte.mariage.domain.Pere;
import io.urbis.acte.mariage.domain.Regime;
import io.urbis.acte.mariage.domain.StatutActeMariage;
import io.urbis.acte.mariage.domain.Temoin;
import io.urbis.acte.mariage.dto.ActeMariageDto;
import io.urbis.common.domain.SituationMatrimoniale;
import io.urbis.registre.domain.Registre;
import io.urbis.param.domain.OfficierEtatCivil;
import io.urbis.registre.domain.StatutRegistre;
import java.util.List;
import java.util.Optional;
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
public class ActeMariageService {
    
    @Inject
    Logger log;
    
    public ActeMariageDto findById(@NotBlank String id){
        return ActeMariage.findByIdOptional(id)
                .map(p -> (ActeMariage)p)
                .map(this::mapToDto)
                .orElseThrow(() -> new WebApplicationException("acte naissance not found",Response.status(Response.Status.NOT_FOUND).build()));
       
    }
    
    public void creer(@NotNull ActeMariageDto acteMariageDto){
        
        Epouse eps = new Epouse(new Conjoint(), new Pere(), new Mere(), new Temoin());
        Epoux epx = new Epoux(new Conjoint(), new Pere(), new Mere(), new Temoin());
        
        Registre registre = Registre.findById(acteMariageDto.getRegistreID());
        if(registre == null){
           throw new EntityNotFoundException("Registre not found");
           
        }
        
        OfficierEtatCivil officier = OfficierEtatCivil.findById(acteMariageDto.getOfficierEtatCivilID());
        if(registre == null){
           throw new EntityNotFoundException("Officier not found");
           //throw new WebApplicationException("Officier not found", Response.Status.NOT_FOUND);
        }
        
        ActeMariage acte = new ActeMariage(epx, eps);
        
        acte.dateMariage = acteMariageDto.getDateMariage();
        acte.lieuMariage = acteMariageDto.getLieuMariage();
        acte.regime = Regime.fromString(acteMariageDto.getRegime());
        acte.officierEtatCivil = officier;
        acte.registre = registre;
        acte.numero = acteMariageDto.getNumero();
        acte.statut = StatutActeMariage.PROJET;
        
        acte.epoux.conjoint.dateNaissance = acteMariageDto.getEpouxConjointDateNaissance();
        acte.epoux.conjoint.nom = acteMariageDto.getEpouxConjointNom();
        acte.epoux.conjoint.prenoms = acteMariageDto.getEpouxConjointPrenoms();
        acte.epoux.conjoint.profession = acteMariageDto.getEpouxConjointProfession();
        acte.epoux.conjoint.lieuNaissance = acteMariageDto.getEpouxConjointLieuNaissance();
        acte.epoux.conjoint.domicile = acteMariageDto.getEpouxConjointDomicile();
        acte.epoux.conjoint.situationMatrimoniale = 
                SituationMatrimoniale.fromString(acteMariageDto.getEpouxConjointSituationMatrimoniale());
        
        acte.epoux.pere.nom = acteMariageDto.getEpouxPereNom();
        acte.epoux.pere.prenoms = acteMariageDto.getEpouxPerePrenoms();
        acte.epoux.pere.domicile = acteMariageDto.getEpouxPereDomicile();
        acte.epoux.pere.profession = acteMariageDto.getEpouxPereProfession();
        acte.epoux.pere.decede = acteMariageDto.isEpouxPereDecede();
        acte.epoux.pere.dateDeces = acteMariageDto.getEpouxPereDateDeces();
        acte.epoux.pere.lieuDeces = acteMariageDto.getEpouxPereLieuDeces();
        
        acte.epoux.mere.nom = acteMariageDto.getEpouxMereNom();
        acte.epoux.mere.prenoms = acteMariageDto.getEpouxMerePrenoms();
        acte.epoux.mere.profession = acteMariageDto.getEpouxMereProfession();
        acte.epoux.mere.domicile = acteMariageDto.getEpouxMereDomicile();
        acte.epoux.mere.decede = acteMariageDto.isEpouxMereDecede();
        acte.epoux.mere.dateDeces = acteMariageDto.getEpouxMereDateDeces();
        acte.epoux.mere.lieuDeces = acteMariageDto.getEpouxMereLieuDeces();
        
        acte.epoux.temoin.nom = acteMariageDto.getEpouxTemoinNom();
        acte.epoux.temoin.prenoms = acteMariageDto.getEpouxTemoinPrenoms();
        acte.epoux.temoin.profession = acteMariageDto.getEpouxTemoinProfession();
        acte.epoux.temoin.domicile = acteMariageDto.getEpouxTemoinDomicile();
        acte.epoux.temoin.dateNaissance = acteMariageDto.getEpouxTemoinDateNaissance();
        
        //---- epouse ---
        
        acte.epouse.conjoint.dateNaissance = acteMariageDto.getEpouseConjointDateNaissance();
        acte.epouse.conjoint.nom = acteMariageDto.getEpouseConjointNom();
        acte.epouse.conjoint.prenoms = acteMariageDto.getEpouseConjointPrenoms();
        acte.epouse.conjoint.profession = acteMariageDto.getEpouseConjointProfession();
        acte.epouse.conjoint.lieuNaissance = acteMariageDto.getEpouseConjointLieuNaissance();
        acte.epouse.conjoint.domicile = acteMariageDto.getEpouseConjointDomicile();
        acte.epouse.conjoint.situationMatrimoniale = 
                SituationMatrimoniale.fromString(acteMariageDto.getEpouseConjointSituationMatrimoniale());
        
        acte.epouse.pere.nom = acteMariageDto.getEpousePereNom();
        acte.epouse.pere.prenoms = acteMariageDto.getEpousePerePrenoms();
        acte.epouse.pere.domicile = acteMariageDto.getEpousePereDomicile();
        acte.epouse.pere.profession = acteMariageDto.getEpousePereProfession();
        acte.epouse.pere.decede = acteMariageDto.isEpousePereDecede();
        acte.epouse.pere.dateDeces = acteMariageDto.getEpousePereDateDeces();
        acte.epouse.pere.lieuDeces = acteMariageDto.getEpousePereLieuDeces();
        
        acte.epouse.mere.nom = acteMariageDto.getEpouseMereNom();
        acte.epouse.mere.prenoms = acteMariageDto.getEpouseMerePrenoms();
        acte.epouse.mere.profession = acteMariageDto.getEpouseMereProfession();
        acte.epouse.mere.domicile = acteMariageDto.getEpouseMereDomicile();
        acte.epouse.mere.decede = acteMariageDto.isEpouxMereDecede();
        acte.epouse.mere.dateDeces = acteMariageDto.getEpouseMereDateDeces();
        acte.epouse.mere.lieuDeces = acteMariageDto.getEpouseMereLieuDeces();
       
        
        acte.epouse.temoin.nom = acteMariageDto.getEpouseTemoinNom();
        acte.epouse.temoin.prenoms = acteMariageDto.getEpouseTemoinPrenoms();
        acte.epouse.temoin.profession = acteMariageDto.getEpouseTemoinProfession();
        acte.epouse.temoin.domicile = acteMariageDto.getEpouseTemoinDomicile();
        acte.epouse.temoin.dateNaissance = acteMariageDto.getEpouseTemoinDateNaissance();
           
        Operation operation = Operation.fromString(acteMariageDto.getOperation());
        validerActe(registre, acteMariageDto,operation);
        
        acte.persist();
        
        if(operation == Operation.DECLARATION){
            //incrementer l'index du registre
            if(registre.numeroProchainActe == acteMariageDto.getNumero()){
                registre.numeroProchainActe += 1;
            }else{
                //permet de changer l'index du registre en entrant le numero voulu
                registre.numeroProchainActe = acteMariageDto.getNumero() + 1;
            }
            
        }
        
    }
    
    public void modifier(@NotBlank String id,@NotNull ActeMariageDto acteMariageDto){
        
        ActeMariage acte = ActeMariage.findById(id);
        acte.epoux =  new Epoux(new Conjoint(), new Pere(), new Mere(), new Temoin());
        acte.epouse = new Epouse(new Conjoint(), new Pere(), new Mere(), new Temoin()); 
                
        
        Registre registre = Registre.findById(acteMariageDto.getRegistreID());
        if(registre == null){
            throw  new EntityNotFoundException("Registre not found");
        }
        
        if(registre.statut != StatutRegistre.VALIDE){
            throw new ValidationException("le registre n'a pas le statut 'validé'");
        }
                
        OfficierEtatCivil officier = OfficierEtatCivil.findById(acteMariageDto.getOfficierEtatCivilID());
        if(officier == null){
            throw  new EntityNotFoundException("OfficierEtatCivil not found");
        }
       
        Operation op = Operation.fromString(acteMariageDto.getOperation());
        validerActe(registre, acteMariageDto,op);
        
        acte.officierEtatCivil = officier;
        acte.registre = registre;
        
        acte.numero = acteMariageDto.getNumero();
        acte.lieuMariage = acteMariageDto.getLieuMariage();
        acte.statut = StatutActeMariage.fromString(acteMariageDto.getStatut());
        acte.dateMariage = acteMariageDto.getDateMariage();
        
        acte.epoux.conjoint.dateNaissance = acteMariageDto.getEpouxConjointDateNaissance();
        acte.epoux.conjoint.lieuNaissance = acteMariageDto.getEpouxConjointLieuNaissance();
        acte.epoux.conjoint.nom = acteMariageDto.getEpouxConjointNom();
        acte.epoux.conjoint.prenoms = acteMariageDto.getEpouxConjointPrenoms();
        acte.epoux.conjoint.profession = acteMariageDto.getEpouxConjointProfession();
        acte.epoux.conjoint.domicile = acteMariageDto.getEpouxConjointDomicile();
        acte.epoux.conjoint.situationMatrimoniale = 
                SituationMatrimoniale.fromString(acteMariageDto.getEpouxConjointSituationMatrimoniale());
        
        acte.epoux.mere.dateDeces = acteMariageDto.getEpouxMereDateDeces();
        acte.epoux.mere.decede = acteMariageDto.isEpouxMereDecede();
        acte.epoux.mere.domicile = acteMariageDto.getEpouxMereDomicile();
        acte.epoux.mere.lieuDeces = acteMariageDto.getEpouxMereLieuDeces();
        acte.epoux.mere.nom = acteMariageDto.getEpouxMereNom();
        acte.epoux.mere.prenoms = acteMariageDto.getEpouxMerePrenoms();
        acte.epoux.mere.profession = acteMariageDto.getEpouxMereProfession();
        
        acte.epoux.pere.dateDeces = acteMariageDto.getEpouxPereDateDeces();
        acte.epoux.pere.decede = acteMariageDto.isEpouxPereDecede();
        acte.epoux.pere.domicile = acteMariageDto.getEpouxPereDomicile();
        acte.epoux.pere.lieuDeces = acteMariageDto.getEpouxPereLieuDeces();
        acte.epoux.pere.nom = acteMariageDto.getEpouxPereNom();
        acte.epoux.pere.prenoms = acteMariageDto.getEpouxPerePrenoms();
        acte.epoux.pere.profession = acteMariageDto.getEpouxPereProfession();
        
        acte.epoux.temoin.dateNaissance =  acteMariageDto.getEpouxTemoinDateNaissance();
        acte.epoux.temoin.domicile = acteMariageDto.getEpouxTemoinDomicile();
        acte.epoux.temoin.nom = acteMariageDto.getEpouxTemoinNom();
        acte.epoux.temoin.prenoms = acteMariageDto.getEpouxTemoinPrenoms();
        acte.epoux.temoin.profession = acteMariageDto.getEpouxTemoinProfession();
        
        //------- Epouse ---------
        
        acte.epouse.conjoint.dateNaissance = acteMariageDto.getEpouseConjointDateNaissance();
        acte.epouse.conjoint.lieuNaissance = acteMariageDto.getEpouseConjointLieuNaissance();
        acte.epouse.conjoint.nom = acteMariageDto.getEpouseConjointNom();
        acte.epouse.conjoint.prenoms = acteMariageDto.getEpouseConjointPrenoms();
        acte.epouse.conjoint.profession = acteMariageDto.getEpouseConjointProfession();
        acte.epouse.conjoint.domicile = acteMariageDto.getEpouxMereDomicile();
        acte.epouse.conjoint.situationMatrimoniale = 
                SituationMatrimoniale.fromString(acteMariageDto.getEpouseConjointSituationMatrimoniale());
        
        acte.epouse.mere.dateDeces = acteMariageDto.getEpouseMereDateDeces();
        acte.epouse.mere.decede = acteMariageDto.isEpouseMereDecede();
        acte.epouse.mere.domicile = acteMariageDto.getEpouseMereDomicile();
        acte.epouse.mere.lieuDeces = acteMariageDto.getEpouseMereLieuDeces();
        acte.epouse.mere.nom = acteMariageDto.getEpouseMereNom();
        acte.epouse.mere.prenoms = acteMariageDto.getEpouseMerePrenoms();
        acte.epouse.mere.profession = acteMariageDto.getEpouseMereProfession();
        
        acte.epouse.pere.dateDeces = acteMariageDto.getEpousePereDateDeces();
        acte.epouse.pere.decede = acteMariageDto.isEpousePereDecede();
        acte.epouse.pere.domicile = acteMariageDto.getEpousePereDomicile();
        acte.epouse.pere.lieuDeces = acteMariageDto.getEpousePereLieuDeces();
        acte.epouse.pere.nom = acteMariageDto.getEpousePereNom();
        acte.epouse.pere.prenoms = acteMariageDto.getEpousePerePrenoms();
        acte.epouse.pere.profession = acteMariageDto.getEpousePereProfession();
        
        acte.epouse.temoin.dateNaissance =  acteMariageDto.getEpouseTemoinDateNaissance();
        acte.epouse.temoin.domicile = acteMariageDto.getEpouseTemoinDomicile();
        acte.epouse.temoin.nom = acteMariageDto.getEpouseTemoinNom();
        acte.epouse.temoin.prenoms = acteMariageDto.getEpouseTemoinPrenoms();
        acte.epouse.temoin.profession = acteMariageDto.getEpouseTemoinProfession();
        
          
    }
    
    public void validerActe(Registre registre,ActeMariageDto acte,Operation operation){
        if(operation == Operation.DECLARATION){
            verifierNumero(registre, acte);
        }
        
        validerBorneInferieure(registre, acte.getNumero());
        validerBorneSuperieure(registre, acte.getNumero());
    }
    
    public boolean supprimer(@NotBlank String id){
        return ActeMariage.deleteById(id);
    }
    
   /*
    public void validerActe(Registre registre,ActeMariageDto acte){
        verifierNumero(registre, acte);
        validerBorneInferieure(registre, acte.getNumero());
        validerBorneSuperieure(registre, acte.getNumero());
   
    }
    */
    
    public void verifierNumero(Registre registre,ActeMariageDto acte){
        while(numeroExist(registre, acte.getNumero())){
            acte.setNumero(acte.getNumero() + 1);
            registre.numeroProchainActe += 1;
        }
    }
    
    
    
    public List<ActeMariageDto> findWithFilters(int offset,int pageSize,@NotBlank String registreID){
        log.infof("||-- REGISTRE ID : %s", registreID);
        Registre registre = Registre.findById(registreID);
        
        if(registre == null){
            throw new WebApplicationException("Registre not found", Response.Status.NOT_FOUND);
        }
        
        PanacheQuery<ActeMariage>  query = ActeMariage.find("registre",Sort.by("numero").descending(),registre);
        
        return query.stream().map(this::mapToDto).collect(Collectors.toList());
      
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
    }
    
    public int count(){
        return (int)ActeMariage.count();
    }
    
   
    
       
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
       Optional<ActeMariage> optActe = ActeMariage.find("registre = ?1 AND numero = ?2",
               registre,numeroActe).firstResultOptional();
       
       return optActe.isPresent();
    }
    
    public ActeMariageDto mapToDto(@NotNull ActeMariage acte){
        ActeMariageDto dto = new ActeMariageDto();
        
        dto.setId(acte.id);
        dto.setCreated(acte.created);
        dto.setUpdated(acte.updated);
        dto.setDateMariage(acte.dateMariage);
        dto.setLieuMariage(acte.lieuMariage);
        dto.setRegime(acte.regime.name());
        dto.setOfficierEtatCivilID(acte.officierEtatCivil.id);
        dto.setOfficierEtatCivilNom(acte.officierEtatCivil.nom);
        dto.setOfficierEtatCivilPrenoms(acte.officierEtatCivil.prenoms);
        dto.setOfficierEtatCivilTitre(acte.officierEtatCivil.titre.name());
        dto.setRegistreID(acte.registre.id);
        dto.setRegistreNumero(acte.registre.reference.numero);
        dto.setRegistreAnnee(acte.registre.reference.annee);
        dto.setNumero(acte.numero);
        dto.setStatut(acte.statut.name());
        
        //epoux
        
        dto.setEpouxConjointDateNaissance(acte.epoux.conjoint.dateNaissance);
        dto.setEpouxConjointLieuNaissance(acte.epoux.conjoint.lieuNaissance);
        dto.setEpouxConjointDomicile(acte.epoux.conjoint.domicile);
        dto.setEpouxConjointNom(acte.epoux.conjoint.nom);
        dto.setEpouxConjointPrenoms(acte.epoux.conjoint.prenoms);
        dto.setEpouxConjointProfession(acte.epoux.conjoint.profession);
        dto.setEpouxConjointSituationMatrimoniale(acte.epoux.conjoint.situationMatrimoniale.name());
        
        dto.setEpouxPereNom(acte.epoux.pere.nom);
        dto.setEpouxPerePrenoms(acte.epoux.pere.prenoms);
        dto.setEpouxPereProfession(acte.epoux.pere.profession);
        dto.setEpouxPereDomicile(acte.epoux.pere.domicile);
        dto.setEpouxPereDecede(acte.epoux.pere.decede);
        dto.setEpouxPereDateDeces(acte.epoux.pere.dateDeces);
        dto.setEpouxPereLieuDeces(acte.epoux.pere.lieuDeces);
        
        dto.setEpouxMereNom(acte.epoux.mere.nom);
        dto.setEpouxMerePrenoms(acte.epoux.mere.prenoms);
        dto.setEpouxMereProfession(acte.epoux.mere.profession);
        dto.setEpouxMereDomicile(acte.epoux.mere.domicile);
        dto.setEpouxMereDecede(acte.epoux.mere.decede);
        dto.setEpouxMereDateDeces(acte.epoux.mere.dateDeces);
        dto.setEpouxMereLieuDeces(acte.epoux.mere.lieuDeces);
        
        dto.setEpouxTemoinDateNaissance(acte.epoux.temoin.dateNaissance);
        dto.setEpouxTemoinNom(acte.epoux.temoin.nom);
        dto.setEpouxTemoinPrenoms(acte.epoux.temoin.prenoms);
        dto.setEpouxTemoinProfession(acte.epoux.temoin.profession);
        dto.setEpouxTemoinDomicile(acte.epoux.temoin.domicile);
        
        //epouse
        
        dto.setEpouseConjointDateNaissance(acte.epouse.conjoint.dateNaissance);
        dto.setEpouseConjointLieuNaissance(acte.epouse.conjoint.lieuNaissance);
        dto.setEpouseConjointDomicile(acte.epouse.conjoint.domicile);
        dto.setEpouseConjointNom(acte.epouse.conjoint.nom);
        dto.setEpouseConjointPrenoms(acte.epouse.conjoint.prenoms);
        dto.setEpouseConjointProfession(acte.epouse.conjoint.profession);
        dto.setEpouseConjointSituationMatrimoniale(acte.epouse.conjoint.situationMatrimoniale.name());
        
        dto.setEpousePereNom(acte.epouse.pere.nom);
        dto.setEpousePerePrenoms(acte.epouse.pere.prenoms);
        dto.setEpousePereProfession(acte.epouse.pere.profession);
        dto.setEpousePereDomicile(acte.epouse.pere.domicile);
        dto.setEpousePereDecede(acte.epouse.pere.decede);
        dto.setEpousePereDateDeces(acte.epouse.pere.dateDeces);
        dto.setEpousePereLieuDeces(acte.epouse.pere.lieuDeces);
        
        dto.setEpouseMereNom(acte.epouse.mere.nom);
        dto.setEpouseMerePrenoms(acte.epouse.mere.prenoms);
        dto.setEpouseMereProfession(acte.epouse.mere.profession);
        dto.setEpouseMereDomicile(acte.epouse.mere.domicile);
        dto.setEpouseMereDecede(acte.epouse.mere.decede);
        dto.setEpouseMereDateDeces(acte.epouse.mere.dateDeces);
        dto.setEpouseMereLieuDeces(acte.epouse.mere.lieuDeces);
        
        dto.setEpouseTemoinDateNaissance(acte.epouse.temoin.dateNaissance);
        dto.setEpouseTemoinNom(acte.epouse.temoin.nom);
        dto.setEpouseTemoinPrenoms(acte.epouse.temoin.prenoms);
        dto.setEpouseTemoinProfession(acte.epouse.temoin.profession);
        dto.setEpouseTemoinDomicile(acte.epouse.temoin.domicile);
        
        return dto;
    }
    
    
}
