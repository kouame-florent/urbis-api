/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.registre.api;

import io.urbis.registre.dto.LocaliteDto;
import io.urbis.registre.service.LocaliteService;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 *
 * @author florent
 */
@Path("/localites")
public class LocaliteResource {
    
    @Inject
    LocaliteService localiteService;
    
    @GET @Path("active")
    public LocaliteDto findActive(){
        return localiteService.findActiveLocalite();
    }
    
}
