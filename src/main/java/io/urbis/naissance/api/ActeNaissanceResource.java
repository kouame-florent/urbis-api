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
    @GET
    public List<ActeNaissanceDto> findWithFilters(@QueryParam("offset") int offset, @QueryParam("page-size") int pageSize){
        return naissanceService.findWithFilters(offset, pageSize);
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
