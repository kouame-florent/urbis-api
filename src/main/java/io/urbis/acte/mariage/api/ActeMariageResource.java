/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.acte.mariage.api;

import io.urbis.acte.mariage.dto.ActeMariageDto;
import io.urbis.acte.mariage.service.ActeMariageService;
import io.urbis.acte.naissance.dto.ActeNaissanceDto;
import java.util.List;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

/**
 *
 * @author florent
 */
@Path("/mariages")
@Tag(name = "acte de mariage")
public class ActeMariageResource {
    
    @Inject
    ActeMariageService acteMariageService;
    
    @Transactional
    @POST
    public void create(ActeMariageDto dto){
        acteMariageService.creer(dto);
    }
    
    @Transactional
    @PUT @Path("{id}")
    public void update(@PathParam("id") String id,ActeMariageDto dto){
        acteMariageService.modifier(id, dto);
    }
    
    @GET @Path("{id}")
    public ActeMariageDto findById(@PathParam("id") String id){
        return acteMariageService.findById(id);
    }
    
    @GET
    public List<ActeMariageDto> findWithFilters(@QueryParam("offset") int offset, 
            @QueryParam("page-size") int pageSize,@QueryParam("registre-id") String registreID){
        return acteMariageService.findWithFilters(offset, pageSize,registreID);
    }
    
    @GET @Path("/count")
    public int count(){
        return acteMariageService.count();
    }
    
    @GET
    @Path("/numero-acte/{id}")
    public int numeroActe(@PathParam("id") String registreID){
       return acteMariageService.numeroActe(registreID);
    }
    
}
