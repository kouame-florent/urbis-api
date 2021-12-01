/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.param.api;

import io.urbis.param.dto.TribunalDto;
import io.urbis.param.service.TribunalService;
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
@Path("/tribunaux")
public class TribunalResource {
    
    @Inject
    TribunalService tribunalService;
    
    @Transactional
    @POST
    public void create(TribunalDto dto){
        tribunalService.create(dto);
    }
    
    @Transactional
    @PUT @Path("{id}")
    public void update(@PathParam("id") String id, TribunalDto dto){
        tribunalService.update(id, dto);
    }
    
    @GET
    public List<TribunalDto> findAll(){
        return tribunalService.findAll();
    }
    
    @Transactional
    @DELETE @Path("{id}")
    public boolean delete(@PathParam("id") String id){
       return tribunalService.delete(id);
    }
    
    
    @GET @Path("active")
    public TribunalDto findActive(){
        return tribunalService.findActive();
    }

}
