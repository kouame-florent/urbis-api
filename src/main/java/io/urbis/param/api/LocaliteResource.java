/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.param.api;

import io.urbis.param.dto.LocaliteDto;
import io.urbis.param.service.LocaliteService;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 *
 * @author florent
 */
@Path("/localites")
public class LocaliteResource {
    
    @Inject
    LocaliteService localiteService;
    
    @POST
    public void create(LocaliteDto dto){
        localiteService.create(dto);
    }
    
    @GET @Path("active")
    public LocaliteDto findActive(){
        return localiteService.findActiveLocalite();
    }
    
}
