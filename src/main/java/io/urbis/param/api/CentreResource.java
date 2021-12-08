/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.param.api;

import io.urbis.param.dto.CentreDto;
import io.urbis.param.service.CentreService;
import java.util.List;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 *
 * @author florent
 */
@Path("/centres")
public class CentreResource {
    
    @Inject
    CentreService centreService;
    
    @Transactional
    @POST
    public void create(CentreDto dto){
        centreService.create(dto);
    }
    
    @Transactional
    @PUT @Path("{id}")
    public void update(@PathParam("id") String id, CentreDto dto){
        centreService.update(id, dto);
    }
    
    @GET
    public List<CentreDto> findAll(){
        return centreService.findAll();
    }
    
    @Transactional
    @DELETE @Path("{id}")
    public boolean delete(@PathParam("id") String id){
       return centreService.delete(id);
    }
    
    
    @GET @Path("active")
    public CentreDto findActive(){
        return centreService.findActive();
    }
    
    @GET @Path("count")
    public long count(){
        return centreService.count();
    }
    
}
