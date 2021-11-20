/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.registre.api;

import io.quarkus.security.identity.SecurityIdentity;
import io.urbis.registre.dto.TypeRegistreDto;
import io.urbis.registre.service.TypeRegistreService;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import org.jboss.logging.Logger;


/**
 *
 * @author florent
 */
@Path("/types-registre")
public class TypeRegistreResource {
    
    @Inject
    Logger log;
    
    //@Inject
    //SecurityIdentity securityIdentity;
    
    @Inject
    TypeRegistreService typeRegistreService;
    
    //@RolesAllowed("USER")
    @GET
    public List<TypeRegistreDto> findAll(@HeaderParam("Authorization") String authorization){
        //log.infof("---- Authorization: %s", authorization);
        return this.typeRegistreService.findAllTypeRegistre();
    }
    
}
