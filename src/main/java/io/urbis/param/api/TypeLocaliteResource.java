/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.param.api;

import io.urbis.param.dto.TypeLocaliteDto;
import io.urbis.param.service.TypeLocaliteService;
import java.util.List;
import javax.annotation.security.PermitAll;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import org.jboss.logging.Logger;

/**
 *
 * @author florent
 */
@Path("/types-localite")
public class TypeLocaliteResource {
    
    @Inject
    Logger log;
    
    @Inject
    TypeLocaliteService typeLocaliteService;
    
    @PermitAll
    @GET
    public List<TypeLocaliteDto> findAll(){
        //LOG.log(Level.INFO, "---USER: {0}", );
       // log.infof("-- USER: %s", sec.getUserPrincipal().getName());
        return this.typeLocaliteService.findAll();
    }
}
