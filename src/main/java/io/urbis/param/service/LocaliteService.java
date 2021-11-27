/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.param.service;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.security.identity.SecurityIdentity;
import io.urbis.param.domain.Localite;
import io.urbis.param.domain.StatutParametre;
import io.urbis.param.domain.TypeLocalite;
import io.urbis.param.dto.LocaliteDto;
import java.util.List;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
import javax.ws.rs.NotFoundException;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

/**
 *
 * @author florent
 */
@ApplicationScoped
public class LocaliteService {
    
    @Inject
    Logger log;
    
   // @Inject
   // SecurityIdentity securityIdentity;
    
    //@ConfigProperty(name = "URBIS_LOCALITE")
    //String codeLocalite;
    
    public void create(LocaliteDto dto){
       // log.logf(Logger.Level.INFO, "--- SECURITY IDENTITY: %s", securityIdentity.getPrincipal().getName());
       // PanacheQuery<Localite> query =  Localite.find("statut", StatutParametre.ACTIF);
        if(Localite.count() == 0){
            Localite localite = new Localite(dto.getCode(), 
                dto.getLibelle(),
                TypeLocalite.fromString(dto.getType()) );
        
            localite.persist();
        }else{
            throw new IllegalStateException("une localité 'active' existe déjà");
        }
        
    }
    
    public void update(String localiteID,LocaliteDto dto){
        Localite loc = Localite.findById(localiteID);
        if(loc != null){
            loc.code = dto.getCode();
            loc.libelle = dto.getLibelle();
            loc.typeLocalite = TypeLocalite.fromString(dto.getType());
           // loc.statut = StatutParametre.fromString(dto.getStatut());
            
        }
        else{
            throw new EntityNotFoundException("cannot find entity 'localite'");
        }
    }
    
    public List<LocaliteDto> findAll(){
        List<Localite> locs = Localite.listAll();
        return locs.stream().map(LocaliteService::mapToDto)
                .collect(Collectors.toList());
    }
    
    public LocaliteDto findActive(){
      //PanacheQuery<Localite> query =  Localite.find("statut", StatutParametre.ACTIF);
      PanacheQuery<Localite> query =  Localite.findAll();
      return query.firstResultOptional().map(LocaliteService::mapToDto)
                .orElseThrow(() -> new NotFoundException("aucune 'localité' trouvée"));
      
    }
    
    /*
    public LocaliteDto findActiveLocalite(){
        PanacheQuery<Localite> query =  Localite.find("code", codeLocalite);
        return query.firstResultOptional().map(LocaliteService::mapToDto)
                .orElseThrow(() -> new NotFoundException("aucune localité selectionnée"));
    }
*/
    
    public static LocaliteDto mapToDto(Localite localite){
        return new LocaliteDto(localite.id,
                localite.created,
                localite.updated,
                localite.code, 
                localite.libelle,
                localite.typeLocalite.name(),
                localite.typeLocalite.getLibelle());
                //localite.statut.name());
    
    }
    
}
