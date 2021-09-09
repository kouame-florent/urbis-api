/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.registre.service;

import io.urbis.domain.Centre;
import io.urbis.dto.CentreDto;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import org.jboss.logging.Logger;

/**
 *
 * @author florent
 */
@ApplicationScoped
public class CentreService {
    
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
        Stream<Centre> centres = Centre.findAll().stream();
        return centres.findFirst().map(CentreService::mapToDto)
                .orElseThrow(() -> new NotFoundException("aucun centre configuré "));
    }
    
    public static CentreDto mapToDto(Centre centre){

        return new CentreDto(centre.id, 
                centre.created,
                centre.updated,
                centre.code,
                centre.libelle);

    }
    
}
