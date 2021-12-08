/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.param.api;

import io.urbis.param.dto.OfficierEtatCivilDto;
import io.urbis.param.service.OfficierService;
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
@Path("/officiers")
public class OfficierResource {
    
    @Inject
    OfficierService officierService;
    
    @Transactional
    @POST
    public void create(OfficierEtatCivilDto dto){
        officierService.create(dto);
    }
    
    @Transactional
    @PUT @Path("/{id}")
    public void update(@PathParam("id") String id,OfficierEtatCivilDto dto){
        officierService.update(id, dto);
    }
    
    @GET
    public List<OfficierEtatCivilDto> findAll(){
        return officierService.findAll();
    }
    
    @Transactional
    @DELETE @Path("{id}")
    public boolean delete(@PathParam("id") String id){
       return officierService.delete(id);
    }
    
    @GET @Path("count")
    public long count(){
        return officierService.count();
    }
}
