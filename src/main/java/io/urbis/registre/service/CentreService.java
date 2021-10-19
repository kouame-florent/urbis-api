/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.registre.service;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.urbis.registre.domain.Centre;
import io.urbis.registre.dto.CentreDto;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

/**
 *
 * @author florent
 */
@ApplicationScoped
public class CentreService {
    
    @ConfigProperty(name = "URBIS_CENTRE")
    String codeCentre;
    
    @Inject
    Logger log;
    
    public List<CentreDto> findAll(){
        log.debug("Request to get all centres");
        
        Stream<Centre> centres = Centre.findAll().stream();
        return centres.map(CentreService::mapToDto).collect(Collectors.toList());       
    
    }
    
    /*
    *  renvoie le seul centre enregistré
    */
    public CentreDto findActiveCentre(){
        PanacheQuery<Centre> query =  Centre.find("code", codeCentre);
        return query.firstResultOptional().map(CentreService::mapToDto)
                .orElseThrow(() -> new NotFoundException("aucune centre selectionnée"));
    }
    
    public static CentreDto mapToDto(Centre centre){

        return new CentreDto(centre.id, 
                centre.created,
                centre.updated,
                centre.code,
                centre.libelle);

    }
    
}
