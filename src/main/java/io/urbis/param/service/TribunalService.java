/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.param.service;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.urbis.param.domain.Tribunal;
import io.urbis.param.dto.TribunalDto;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;
import javax.ws.rs.NotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.jboss.logging.Logger;

/**
 *
 * @author florent
 */
@ApplicationScoped
public class TribunalService {
    
    @Inject
    Logger log;
    
    @Inject
    EntityManager em;
    
    //@ConfigProperty(name = "URBIS_TRIBUNAL")
   // String codeTribunal;
    
    public void create(TribunalDto dto){
 
        Tribunal tribunal = new Tribunal(dto.getCode(), dto.getLibelle());
        tribunal.persist();
       
    }
    
    public void update(String tribunalID,TribunalDto dto){
        Tribunal tribunal = Tribunal.findById(dto.getId());
        if(tribunal != null){
            tribunal.code = dto.getCode();
            tribunal.libelle = dto.getLibelle();
        }
        else{
            throw new EntityNotFoundException("cannot find entity 'tribunal'");
        }
    }
    
    public boolean delete(String id){
    
        boolean res = false;
        try{
           res = Tribunal.deleteById(id);
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
    
    public List<TribunalDto> findAll(){
        log.debug("Request to get all tribunaux");
        
        Stream<Tribunal> tribunaux = Tribunal.findAll().stream();
        return tribunaux.map(TribunalService::mapToDto).collect(Collectors.toList());       
    
    }
    
    
    public TribunalDto findActive(){
        PanacheQuery<Tribunal> query = Tribunal.findAll();
        return query.firstResultOptional().map(TribunalService::mapToDto)
                .orElseThrow(() -> new NotFoundException("aucun 'tribunal' trouvé"));
    }

    
     
    public static TribunalDto mapToDto(Tribunal tribunal){
        return new TribunalDto(tribunal.id, 
                tribunal.created, 
                tribunal.updated, 
                tribunal.code, 
                tribunal.libelle);
                //tribunal.statut.name());
    }
}
