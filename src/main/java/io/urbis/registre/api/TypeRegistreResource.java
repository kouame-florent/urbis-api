/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.registre.api;

import io.quarkus.security.Authenticated;
import io.quarkus.security.identity.SecurityIdentity;
import io.urbis.registre.dto.TypeRegistreDto;
import io.urbis.registre.service.TypeRegistreService;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import org.jboss.logging.Logger;


/**
 *
 * @author florent
 */
@Path("/types-registre")
public class TypeRegistreResource {
    
    @Inject
    Logger log;
    
    @Inject
    SecurityIdentity secId;
    
    @Inject
    TypeRegistreService typeRegistreService;
    
    @RolesAllowed("USER")
    //@Authenticated
    @GET
    public List<TypeRegistreDto> findAll(@HeaderParam("Authorization") String authorization,@Context SecurityContext sec){
        //log.infof("---- Authorization: %s", authorization);
        log.infof("-- USER: %s", sec.getUserPrincipal().getName());
        log.infof("-- USER FROM SEC ID: %s", secId.getPrincipal().getName());
        return this.typeRegistreService.findAllTypeRegistre();
    }
    
}
