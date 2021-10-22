/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.naissance.api;

import io.urbis.naissance.dto.ActeNaissanceDto;
import io.urbis.naissance.service.ActeNaissanceService;
import java.util.List;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

/**
 *
 * @author florent
 */
@Path("/naissances")
public class ActeNaissanceResource {
    
    @Inject
    ActeNaissanceService naissanceService;
    
    @Transactional
    @POST
    public void create(ActeNaissanceDto acteNaissanceDto){
        naissanceService.creerActe(acteNaissanceDto);
        
    }
    
    @Transactional
    @PUT @Path("{id}")
    public void update(@PathParam("id")String id, ActeNaissanceDto acteNaissanceDto){
        naissanceService.updateActe(id, acteNaissanceDto);
    }
    
    @Transactional
    @GET @Path("/{registre-id}")
    public List<ActeNaissanceDto> findWithFilters(@QueryParam("offset") int offset, 
            @QueryParam("page-size") int pageSize,@PathParam("registre-id") String registreID){
        return naissanceService.findWithFilters(offset, pageSize,registreID);
    }
    
    @GET @Path("/count")
    public int count(){
        return naissanceService.count();
    }
    
    
    @GET
    @Path("/numero-acte/{id}")
    public int numeroActe(@PathParam("id") String registreID){
       return naissanceService.numeroActe(registreID);
    }
    
}
