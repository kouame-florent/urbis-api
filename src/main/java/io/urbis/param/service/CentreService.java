/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.param.service;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.urbis.param.domain.Centre;
import io.urbis.param.domain.Localite;
import io.urbis.param.dto.CentreDto;
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
public class CentreService {
    
   // @ConfigProperty(name = "URBIS_CENTRE")
   // String codeCentre;
    
    @Inject
    Logger log;
    
    @Inject
    EntityManager em;
    
    public void create(CentreDto dto){
        
        Localite localite = Localite.findById(dto.getLocaliteID());
        if(localite == null){
            throw new EntityNotFoundException("'localite' not found");
        }
        
        if(Centre.count() == 0){
            Centre centre = new Centre(dto.getCode(), dto.getLibelle(), localite);
            centre.persist();
        }else{
            throw new IllegalStateException("un centre 'actif' existe déjà");
        }
        
        
    }
    
    public void update(String centreID,CentreDto dto){
        Centre cen = Centre.findById(centreID);
        Localite loc = Localite.findById(dto.getLocaliteID());
        if(cen != null){
            cen.code = dto.getCode();
            cen.libelle = dto.getLibelle();
                       
            //cen.statut = StatutParametre.fromString(dto.getStatut());
            
        }
        else{
            throw new EntityNotFoundException("cannot find entity 'localite'");
        }
    }
    
    public boolean delete(String id){
    
        boolean res = false;
        try{
           res = Centre.deleteById(id);
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
    
    public List<CentreDto> findAll(){
        log.debug("Request to get all centres");
        
        Stream<Centre> centres = Centre.findAll().stream();
        return centres.map(CentreService::mapToDto).collect(Collectors.toList());       
    
    }
    
    /*
    *  renvoie le seul centre enregistré
    */
    public CentreDto findActive(){
        PanacheQuery<Centre> query =  Centre.findAll();
        return query.firstResultOptional().map(CentreService::mapToDto)
                .orElseThrow(() -> new NotFoundException("aucun 'centre' trouvé"));
    
    }
    
    public static CentreDto mapToDto(Centre centre){

        return new CentreDto(centre.id, 
                centre.created,
                centre.updated,
                centre.code,
                centre.libelle,
                centre.localite.id,
                centre.localite.libelle);
        

    }
    
}
