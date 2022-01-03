/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.acte.divers.service;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Sort;
import io.urbis.acte.divers.domain.ActeRecEnfantNaturel;
import io.urbis.acte.divers.dto.ActeRecEnfantNaturelDto;
import io.urbis.param.domain.OfficierEtatCivil;
import java.util.List;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import org.jboss.logging.Logger;
import io.urbis.registre.domain.Registre;
import io.urbis.acte.divers.domain.Reconnaissance;
import io.urbis.acte.divers.domain.Enfant;
import io.urbis.acte.divers.domain.Operation;
import io.urbis.acte.divers.domain.Reconnaissant;
import io.urbis.acte.divers.domain.Sexe;
import io.urbis.registre.domain.StatutRegistre;
import java.lang.invoke.VarHandle;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import javax.validation.ValidationException;

/**
 *
 * @author florent
 */
@ApplicationScoped
public class ActeRecEnfantNaturelService {
    
    @Inject
    Logger log;
    
    public ActeRecEnfantNaturelDto findById(@NotBlank String id){
        return ActeRecEnfantNaturel.findByIdOptional(id)
                .map(p -> (ActeRecEnfantNaturel)p)
                .map(this::mapToDto)
                .orElseThrow(() -> new WebApplicationException("acte reconnaissance enfant naturel not found",Response.status(Response.Status.NOT_FOUND).build()));
       
    }
    
    public void creer(@NotNull ActeRecEnfantNaturelDto dto){
        
        Registre registre = Registre.findById(dto.getRegistreID());
        if(registre == null){
           throw new EntityNotFoundException("Registre not found");
           
        }
        
        OfficierEtatCivil officier = OfficierEtatCivil.findById(dto.getOfficierEtatCivilID());
        if(registre == null){
           throw new EntityNotFoundException("Officier not found");
           //throw new WebApplicationException("Officier not found", Response.Status.NOT_FOUND);
        }
        
        ActeRecEnfantNaturel acte = new ActeRecEnfantNaturel(new Reconnaissance(), 
                new Enfant(), new Reconnaissant());
        
        acte.numero = dto.getNumero();
        
        acte.mereEnfantDateNaissance = dto.getMereEnfantDateNaissance();
        acte.mereEnfantDomicile = dto.getMereEnfantDomicile();
        acte.mereEnfantNom = dto.getMereEnfantPrenoms();
        acte.mereEnfantPrenoms = dto.getMereEnfantPrenoms();
        acte.mereEnfantProfession = dto.getMereEnfantProfession();
        
        acte.enfant.dateActe = dto.getEnfantDateActe();
        acte.enfant.dateNaissance = dto.getEnfantDateNaissance();
        acte.enfant.lieuNaissance = dto.getEnfantLieuNaissance();
        acte.enfant.nom = dto.getEnfantNom();
        acte.enfant.prenoms = dto.getEnfantPrenoms();
        acte.enfant.numeroActe = dto.getEnfantNumeroActe();
        acte.enfant.sexe = Sexe.fromString(dto.getEnfantSexe());
        
        acte.reconnaissance.date = dto.getReconnaissanceDate();
        acte.reconnaissance.lieu = dto.getReconnaissanceLieu();
        acte.reconnaissance.natureCirconscription = dto.getReconnaissanceNatureCirconscription();
        acte.reconnaissance.nomCirconscription = dto.getReconnaissanceNomCirconscription();
        
        acte.reconnaissant.nom = dto.getReconnaissantNom();
        acte.reconnaissant.prenoms = dto.getReconnaissantPrenoms();
        acte.reconnaissant.profession = dto.getReconnaissantProfession();
        acte.reconnaissant.domicile = dto.getReconnaissantDomicile();
        
        acte.registre = registre;
        acte.officierEtatCivil = officier;
        
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
    
    public void modifier(@NotBlank String id,@NotNull ActeRecEnfantNaturelDto dto){
         ActeRecEnfantNaturel acte = ActeRecEnfantNaturel.findById(id);
         acte.enfant = new Enfant();
         acte.reconnaissance = new Reconnaissance();
         acte.reconnaissant = new Reconnaissant();
         
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
        
        acte.mereEnfantDateNaissance = dto.getMereEnfantDateNaissance();
        acte.mereEnfantDomicile = dto.getMereEnfantDomicile();
        acte.mereEnfantNom = dto.getMereEnfantPrenoms();
        acte.mereEnfantPrenoms = dto.getMereEnfantPrenoms();
        acte.mereEnfantProfession = dto.getMereEnfantProfession();
        
        acte.enfant.dateActe = dto.getEnfantDateActe();
        acte.enfant.dateNaissance = dto.getEnfantDateNaissance();
        acte.enfant.lieuNaissance = dto.getEnfantLieuNaissance();
        acte.enfant.nom = dto.getEnfantNom();
        acte.enfant.prenoms = dto.getEnfantPrenoms();
        acte.enfant.numeroActe = dto.getEnfantNumeroActe();
        acte.enfant.sexe = Sexe.fromString(dto.getEnfantSexe());
        
        acte.reconnaissance.date = dto.getReconnaissanceDate();
        acte.reconnaissance.lieu = dto.getReconnaissanceLieu();
        acte.reconnaissance.natureCirconscription = dto.getReconnaissanceNatureCirconscription();
        acte.reconnaissance.nomCirconscription = dto.getReconnaissanceNomCirconscription();
        
        acte.reconnaissant.dateNaissance = dto.getReconnaissantDateNaissance();
        acte.reconnaissant.nom = dto.getReconnaissantNom();
        acte.reconnaissant.prenoms = dto.getReconnaissantPrenoms();
        acte.reconnaissant.profession = dto.getReconnaissantProfession();
        acte.reconnaissant.domicile = dto.getReconnaissantDomicile();
        
        acte.registre = registre;
        acte.officierEtatCivil = officier;
    
    }
    
    public void validerActe(Registre registre,ActeRecEnfantNaturelDto acte,Operation operation){
        if(operation == Operation.DECLARATION){
            verifierNumero(registre, acte);
        }
        validerBorneInferieure(registre, acte.getNumero());
        validerBorneSuperieure(registre, acte.getNumero());
   
    }
    
    public void verifierNumero(Registre registre,ActeRecEnfantNaturelDto acte){
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
       Optional<ActeRecEnfantNaturel> optActe = ActeRecEnfantNaturel.find("registre = ?1 AND numero = ?2",
               registre,numeroActe).firstResultOptional();
       
       return optActe.isPresent();
    }
    
    public int count(){
        return (int)ActeRecEnfantNaturel.count();
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
    
    public List<ActeRecEnfantNaturelDto> findWithFilters(int offset,int pageSize,@NotBlank String registreID){
        log.infof("||-- registre ID : %s", registreID);
        Registre registre = Registre.findById(registreID);
        
        if(registre == null){
            throw new WebApplicationException("Registre not found", Response.Status.NOT_FOUND);
        }
        
        PanacheQuery<ActeRecEnfantNaturel>  query = ActeRecEnfantNaturel.find("registre",Sort.by("numero").descending(),registre);
        
        return query.stream().map(this::mapToDto).collect(Collectors.toList());
      
    }
    
    public ActeRecEnfantNaturelDto mapToDto(@NotNull ActeRecEnfantNaturel acte){
        ActeRecEnfantNaturelDto dto = new ActeRecEnfantNaturelDto();
        
        dto.setId(acte.id);
        dto.setCreated(acte.created);
        dto.setUpdated(acte.updated);
        dto.setNumero(acte.numero);
        dto.setStatut(acte.statut.name());
        
        dto.setMereEnfantDateNaissance(acte.mereEnfantDateNaissance);
        dto.setMereEnfantDomicile(acte.mereEnfantDomicile);
        dto.setMereEnfantNom(acte.mereEnfantNom);
        dto.setMereEnfantPrenoms(acte.mereEnfantPrenoms);
        dto.setMereEnfantProfession(acte.mereEnfantProfession);
        
        dto.setEnfantDateActe(acte.enfant.dateActe);
        dto.setEnfantDateNaissance(acte.enfant.dateNaissance);
        dto.setEnfantLieuNaissance(acte.enfant.lieuNaissance);
        dto.setEnfantNom(acte.enfant.nom);
        dto.setEnfantNumeroActe(acte.enfant.numeroActe);
        dto.setEnfantPrenoms(acte.enfant.prenoms);
        dto.setEnfantSexe(acte.enfant.sexe.name());
        
        dto.setReconnaissanceDate(acte.reconnaissance.date);
        dto.setReconnaissanceLieu(acte.reconnaissance.lieu);
        dto.setReconnaissanceNatureCirconscription(acte.reconnaissance.natureCirconscription);
        dto.setReconnaissanceNomCirconscription(acte.reconnaissance.nomCirconscription);
        
        dto.setReconnaissantDateNaissance(acte.reconnaissant.dateNaissance);
        dto.setReconnaissantDomicile(acte.reconnaissant.nom);
        dto.setReconnaissantNom(acte.reconnaissant.nom);
        dto.setReconnaissantPrenoms(acte.reconnaissant.prenoms);
        dto.setReconnaissantProfession(acte.reconnaissant.profession);
                
        dto.setOfficierEtatCivilID(acte.officierEtatCivil.id);
        dto.setOfficierEtatCivilNom(acte.officierEtatCivil.nom);
        dto.setOfficierEtatCivilPrenoms(acte.officierEtatCivil.prenoms);
        dto.setOfficierEtatCivilTitre(acte.officierEtatCivil.titre.name());
        
        dto.setRegistreID(acte.registre.id);
        dto.setRegistreNumero(acte.registre.reference.numero);
        dto.setRegistreAnnee(acte.registre.reference.annee);
        
        
        return dto;
        
    }
}
