/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.param.service;

import io.quarkus.panache.common.Sort;
import io.urbis.param.domain.OfficierEtatCivil;
import io.urbis.param.domain.TitreOfficier;
import io.urbis.param.dto.OfficierEtatCivilDto;
import io.urbis.security.service.AuthenticationContext;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import org.hibernate.exception.ConstraintViolationException;
import org.jboss.logging.Logger;

/**
 *
 * @author florent
 */
@ApplicationScoped
@Transactional
public class OfficierService {
    
    @Inject
    Logger log;
    
    @Inject
    EntityManager em;
    
    @Inject
    AuthenticationContext authenticationContext;
    
    public void create(OfficierEtatCivilDto dto){
        
        OfficierEtatCivil officier = new OfficierEtatCivil();
        officier.nom = dto.getNom();
        officier.prenoms = dto.getPrenoms();
        officier.profession = dto.getProfession();
        officier.titre = TitreOfficier.fromString(dto.getTitre());
        officier.actif = dto.isActif();
        officier.updatedBy = authenticationContext.userLogin();
        
        officier.persist();
        
    }
    
    public void update(String centreID,OfficierEtatCivilDto dto){
        OfficierEtatCivil officier = OfficierEtatCivil.findById(dto.getId());
        if(officier != null){
            officier.nom = dto.getNom();
            officier.prenoms = dto.getPrenoms();
            officier.profession = dto.getProfession();
            officier.titre = TitreOfficier.fromString(dto.getTitre());
            officier.actif = dto.isActif();
            officier.updatedBy = authenticationContext.userLogin();
        }else{
            throw new EntityNotFoundException("cannot find entity 'officier'");
        }
    }
    
    public boolean delete(String id){
    
        boolean res = false;
        try{
           res = OfficierEtatCivil.deleteById(id);
           em.flush();
        }catch(PersistenceException  e){
            Throwable t = e.getCause();
            while ((t != null) && !(t instanceof ConstraintViolationException)) {
                t = t.getCause();
            }
            if (t instanceof ConstraintViolationException) {
                log.logf(Logger.Level.ERROR,e.getMessage() );
                throw (ConstraintViolationException)t;
            }
            
        }
        return res;
        
    }
    
    public List<OfficierEtatCivilDto> findAll(){
        Stream<OfficierEtatCivil> officiers = OfficierEtatCivil.streamAll(Sort.by("actif").descending().and("titre").and("nom"));
        return officiers.map(OfficierService::mapToDto).collect(Collectors.toList());      
        
    }
    
    public long count(){
        return OfficierEtatCivil.count();
    }
    
    public static OfficierEtatCivilDto mapToDto(OfficierEtatCivil officier){
        return new OfficierEtatCivilDto(
                officier.id, 
                officier.created,
                officier.updated,
                officier.nom, 
                officier.prenoms,
                officier.profession,
                officier.titre.name(), 
                officier.actif);
           
    }
}
