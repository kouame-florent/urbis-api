/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.param.service;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.urbis.param.domain.Localite;
import io.urbis.param.domain.StatutParametre;
import io.urbis.param.domain.TypeLocalite;
import io.urbis.param.dto.LocaliteDto;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.NotFoundException;
import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 *
 * @author florent
 */
@ApplicationScoped
public class LocaliteService {
    
    @ConfigProperty(name = "URBIS_LOCALITE")
    String codeLocalite;
    
    public void create(LocaliteDto dto){
        Localite localite = new Localite(dto.getCode(), 
                dto.getLibelle(),
                TypeLocalite.fromString(dto.getType()), 
                StatutParametre.ACTIF);
        
        localite.persist();
    }
    
    public LocaliteDto findActiveLocalite(){
        PanacheQuery<Localite> query =  Localite.find("code", codeLocalite);
        return query.firstResultOptional().map(LocaliteService::mapToDto)
                .orElseThrow(() -> new NotFoundException("aucune localité selectionnée"));
    }
    
    public static LocaliteDto mapToDto(Localite localite){
        return new LocaliteDto(localite.id,
                localite.created,
                localite.updated,
                localite.code, 
                localite.libelle,
                localite.typeLocalite.name(),
                localite.statut.name());
    
    }
    
}
