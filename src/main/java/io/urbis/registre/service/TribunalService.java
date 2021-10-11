/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.registre.service;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.urbis.registre.domain.Tribunal;
import io.urbis.registre.dto.TribunalDto;
import java.time.LocalDateTime;
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
public class TribunalService {
    
    @Inject
    Logger log;
    
    @ConfigProperty(name = "URBIS_TRIBUNAL")
    String codeTribunal;
    
    public List<TribunalDto> findAll(){
        log.debug("Request to get all tribunaux");
        
        Stream<Tribunal> tribunaux = Tribunal.findAll().stream();
        return tribunaux.map(TribunalService::mapToDto).collect(Collectors.toList());       
    
    }
    
    public TribunalDto findActiveTribunal(){
        PanacheQuery<Tribunal> query =  Tribunal.find("code", codeTribunal);
        return query.firstResultOptional().map(TribunalService::mapToDto)
                .orElseThrow(() -> new NotFoundException("aucun tribunal selectionnée"));
    }
    
     
    public static TribunalDto mapToDto(Tribunal tribunal){
        return new TribunalDto(tribunal.id, 
                tribunal.created, 
                tribunal.updated, 
                tribunal.code, 
                tribunal.libelle);
    }
}
