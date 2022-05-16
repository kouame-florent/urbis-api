/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.param.api;

import io.urbis.param.dto.LocaliteDto;
import io.urbis.param.service.LocaliteService;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import org.jboss.logging.Logger;

/**
 *
 * @author florent
 */
@Path("/localites")
public class LocaliteResource {
    
    @Inject
    Logger log;
    
    @Inject
    LocaliteService localiteService;
    
    //@Inject
    //SecurityContext securityContext;
    
    //@Inject
    //JsonWebToken token;
    
   // @RolesAllowed("USER")
    @Transactional
    @POST
    public void create(LocaliteDto dto){
        localiteService.create(dto);
    }
    
    @RolesAllowed("USER")
    @Transactional
    @PUT @Path("{id}")
    public void update(@PathParam("id") String id, LocaliteDto dto){
        //log.infof("--- RAW TOKEN : %s",token.getRawToken());
        localiteService.update(id, dto);
    }
    
    @RolesAllowed("USER")
    @Transactional
    @DELETE @Path("{id}")
    public boolean delete(@PathParam("id") String id){
       return localiteService.delete(id);
    }
    
    @GET
    public List<LocaliteDto> findAll(){
        return localiteService.findAll();
    }
    
    @GET @Path("active")
    public LocaliteDto findActive(){
        return localiteService.findActive();
    }
    
    @GET @Path("count")
    public long count(){
        return localiteService.count();
    }
    
    
}
