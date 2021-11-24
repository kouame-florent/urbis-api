/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.param.api;

import io.urbis.param.dto.LocaliteDto;
import io.urbis.param.service.LocaliteService;
import java.util.List;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.SecurityContext;

/**
 *
 * @author florent
 */
@Path("/localites")
public class LocaliteResource {
    
    @Inject
    LocaliteService localiteService;
    
    //@Inject
    //SecurityContext securityContext;
    
    @Transactional
    @POST
    public void create(LocaliteDto dto){
        localiteService.create(dto);
    }
    
    @Transactional
    @PUT @Path("{id}")
    public void update(@PathParam("id") String id, LocaliteDto dto){
        localiteService.update(id, dto);
    }
    
    @GET
    public List<LocaliteDto> findAll(){
        return localiteService.findAll();
    }
    
    @GET @Path("active")
    public LocaliteDto findActive(){
        return localiteService.findActive();
    }
    
}
