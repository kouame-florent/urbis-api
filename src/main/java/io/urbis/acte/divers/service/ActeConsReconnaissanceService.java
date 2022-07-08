/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.acte.divers.service;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Sort;
import io.urbis.acte.divers.domain.ActeConsReconnaissance;
import io.urbis.acte.divers.domain.Operation;
import io.urbis.acte.divers.domain.StatutActeDivers;
import io.urbis.acte.divers.dto.ActeConsReconnaissanceDto;
import io.urbis.param.domain.OfficierEtatCivil;
import io.urbis.registre.domain.Registre;
import io.urbis.registre.domain.StatutRegistre;
import io.urbis.security.service.AuthenticationContext;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
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
@Transactional
public class ActeConsReconnaissanceService {
    
    @Inject
    Logger log;
    
    @Inject
    AuthenticationContext authenticationContext;
    
    public ActeConsReconnaissanceDto findById(@NotBlank String id){
        return ActeConsReconnaissance.findByIdOptional(id)
                .map(p -> (ActeConsReconnaissance)p)
                .map(this::mapToDto)
                .orElseThrow(() -> new WebApplicationException("acte 'reconnaissance enfant adulterin' not found",Response.status(Response.Status.NOT_FOUND).build()));
       
    }
    
    public void creer(@NotNull ActeConsReconnaissanceDto dto){
        
        Registre registre = Registre.findById(dto.getRegistreID());
        if(registre == null){
           throw new EntityNotFoundException("Registre not found");
           
        }
        
        OfficierEtatCivil officier = OfficierEtatCivil.findById(dto.getOfficierEtatCivilID());
        if(registre == null){
           throw new EntityNotFoundException("Officier not found");
           //throw new WebApplicationException("Officier not found", Response.Status.NOT_FOUND);
        }
        
        ActeConsReconnaissance acte = new ActeConsReconnaissance();
        
        acte.numero = dto.getNumero();
        acte.registre = registre;
        acte.officierEtatCivil = officier;
        acte.statut = StatutActeDivers.PROJET;
        acte.updatedBy = authenticationContext.userLogin();
        
        acte.consentementDate = dto.getConsentementDate();
        acte.consentementDomicile = dto.getConsentementDomicile();
        acte.consentementNatureCirconscription = dto.getConsentementNatureCirconscription();
        acte.consentementNom = dto.getConsentementNom();
        acte.consentementNomCirconscription = dto.getConsentementNomCirconscription();
        acte.consentementPrenoms = dto.getConsentementPrenoms();
        acte.contractantProfession = dto.getContractantProfession();
        
        acte.contractantDomicile = dto.getContractantDomicile();
        acte.contractantNom = dto.getContractantNom();
        acte.contractantPrenoms = dto.getContractantPrenoms();
        acte.contractantProfession = dto.getContractantProfession();
        
        acte.enfantConsentiDateNaissance = dto.getEnfantConsentiDateNaissance();
        acte.enfantConsentiDomicile = dto.getEnfantConsentiDomicile();
        acte.enfantConsentiNom = dto.getEnfantConsentiNom();
        acte.enfantConsentiPrenoms = dto.getEnfantConsentiPrenoms();
        acte.enfantConsentiProfession = dto.getEnfantConsentiProfession();
        
                
        Operation operation = Operation.fromString(dto.getOperation());
        validerActe(registre, dto,operation);
        
        acte.persist();
                
        if(operation == Operation.DECLARATION){
            //incrementer l'index du registre
            if(registre.numeroProchainActe == dto.getNumero()){
                registre.numeroProchainActe += 1;
            }else{
                //permet de changer l'index du registre en entrant le numero voulu
                registre.numeroProchainActe = dto.getNumero() + 1;
            }
            
        }
    
    }
    
    public void modifier(@NotBlank String id,@NotNull ActeConsReconnaissanceDto dto){
         
       ActeConsReconnaissance acte = ActeConsReconnaissance.findById(id);
        
         
        Registre registre = Registre.findById(dto.getRegistreID());
        if(registre == null){
            throw  new EntityNotFoundException("Registre not found");
        }
        
        if(registre.statut != StatutRegistre.VALIDE){
            throw new ValidationException("le registre n'a pas le statut 'validé'");
        }
                
        OfficierEtatCivil officier = OfficierEtatCivil.findById(dto.getOfficierEtatCivilID());
        if(officier == null){
            throw  new EntityNotFoundException("OfficierEtatCivil not found");
        }
       
        Operation op = Operation.fromString(dto.getOperation());
        validerActe(registre, dto,op);
        
        acte.numero = dto.getNumero();
        acte.registre = registre;
        acte.officierEtatCivil = officier;
        acte.statut = StatutActeDivers.fromString(dto.getStatut());
        acte.updatedBy = authenticationContext.userLogin();
        
        acte.consentementDate = dto.getConsentementDate();
        acte.consentementDomicile = dto.getConsentementDomicile();
        acte.consentementNatureCirconscription = dto.getConsentementNatureCirconscription();
        acte.consentementNom = dto.getConsentementNom();
        acte.consentementNomCirconscription = dto.getConsentementNomCirconscription();
        acte.consentementPrenoms = dto.getConsentementPrenoms();
        acte.consentementProfession = dto.getConsentementProfession();
        
        acte.contractantDomicile = dto.getContractantDomicile();
        acte.contractantNom = dto.getContractantNom();
        acte.contractantPrenoms = dto.getContractantPrenoms();
        acte.contractantProfession = dto.getContractantProfession();
        
        acte.enfantConsentiDateNaissance = dto.getEnfantConsentiDateNaissance();
        acte.enfantConsentiDomicile = dto.getEnfantConsentiDomicile();
        acte.enfantConsentiNom = dto.getEnfantConsentiNom();
        acte.enfantConsentiPrenoms = dto.getEnfantConsentiPrenoms();
        acte.enfantConsentiProfession = dto.getEnfantConsentiProfession();
        
        
    }
    
    public ActeConsReconnaissanceDto mapToDto(@NotNull ActeConsReconnaissance acte){
        ActeConsReconnaissanceDto dto = new ActeConsReconnaissanceDto();
        
        dto.setId(acte.id);
        dto.setCreated(acte.created);
        dto.setUpdated(acte.updated);
        dto.setNumero(acte.numero);
        dto.setStatut(acte.statut.name());
        
        dto.setOfficierEtatCivilID(acte.officierEtatCivil.id);
        dto.setOfficierEtatCivilNom(acte.officierEtatCivil.nom);
        dto.setOfficierEtatCivilPrenoms(acte.officierEtatCivil.prenoms);
        dto.setOfficierEtatCivilTitre(acte.officierEtatCivil.titre.name());
        
        dto.setRegistreID(acte.registre.id);
        dto.setRegistreNumero(acte.registre.reference.numero);
        dto.setRegistreAnnee(acte.registre.reference.annee);
        
        dto.setConsentementDate(acte.consentementDate);
        dto.setConsentementDomicile(acte.consentementDomicile);
        dto.setConsentementNatureCirconscription(acte.consentementNatureCirconscription);
        dto.setConsentementNomCirconscription(acte.consentementNatureCirconscription);
        dto.setConsentementNom(acte.consentementNom);
        dto.setConsentementPrenoms(acte.consentementPrenoms);
        dto.setConsentementProfession(acte.consentementProfession);
        
        dto.setContractantDomicile(acte.contractantDomicile);
        dto.setContractantNom(acte.contractantNom);
        dto.setContractantPrenoms(acte.contractantPrenoms);
        dto.setContractantProfession(acte.contractantProfession);
    
        dto.setEnfantConsentiDateNaissance(acte.enfantConsentiDateNaissance);
        dto.setEnfantConsentiDomicile(acte.consentementDomicile);
        dto.setEnfantConsentiNom(acte.enfantConsentiNom);
        dto.setEnfantConsentiPrenoms(acte.enfantConsentiPrenoms);
        dto.setEnfantConsentiProfession(acte.enfantConsentiProfession);
        
        
        
        return dto;
        
    }
    
    public boolean supprimer(@NotBlank String id){
        return ActeConsReconnaissance.deleteById(id);
    }
    
    public void validerActe(Registre registre,ActeConsReconnaissanceDto acte,Operation operation){
        if(operation == Operation.DECLARATION){
            verifierNumero(registre, acte);
        }
        validerBorneInferieure(registre, acte.getNumero());
        validerBorneSuperieure(registre, acte.getNumero());
   
    }
    
    public void verifierNumero(Registre registre,ActeConsReconnaissanceDto acte){
        while(numeroExist(registre, acte.getNumero())){
            acte.setNumero(acte.getNumero() + 1);
            registre.numeroProchainActe += 1;
        }
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
    
    public boolean numeroExist(Registre registre, int numeroActe){
       Optional<ActeConsReconnaissance> optActe = ActeConsReconnaissance.find("registre = ?1 AND numero = ?2",
               registre,numeroActe).firstResultOptional();
       
       return optActe.isPresent();
    }
    
    public int count(){
        return (int)ActeConsReconnaissance.count();
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
    
    public List<ActeConsReconnaissanceDto> findWithFilters(int offset,int pageSize,@NotBlank String registreID){
        log.infof("||-- registre ID : %s", registreID);
        Registre registre = Registre.findById(registreID);
        
        if(registre == null){
            throw new WebApplicationException("Registre not found", Response.Status.NOT_FOUND);
        }
        
        PanacheQuery<ActeConsReconnaissance>  query = ActeConsReconnaissance.find("registre",Sort.by("numero").descending(),registre);
        PanacheQuery<ActeConsReconnaissance> rq =  query.range(offset, offset + (pageSize-1));
        
        return rq.stream().map(this::mapToDto).collect(Collectors.toList());
      
    }
}
