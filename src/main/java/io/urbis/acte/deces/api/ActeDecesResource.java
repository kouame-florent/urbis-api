/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.acte.deces.api;

import io.urbis.acte.deces.dto.ActeDecesDto;
import io.urbis.acte.deces.service.ActeDecesService;
import java.sql.SQLException;
import java.util.List;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.DELETE;
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
@Path("/deces")
@Tag(name = "acte de naissance")
public class ActeDecesResource {
    
    @Inject
    ActeDecesService acteDecesService;
    
    @GET @Path("{id}")
    public ActeDecesDto findById(@PathParam("id") String id){
        return acteDecesService.findById(id);
    }
    
    @Transactional
    @POST
    public String create(ActeDecesDto ActeDecesDto) throws SQLException{
        return acteDecesService.creer(ActeDecesDto);
        
    }
    
    @Transactional
    @PUT @Path("{id}")
    public void update(@PathParam("id")String id, ActeDecesDto ActeDecesDto) throws SQLException{
        acteDecesService.modifier(id, ActeDecesDto);
    }
    
    @Transactional
    @GET
    public List<ActeDecesDto> findWithFilters(@QueryParam("offset") int offset, 
            @QueryParam("page-size") int pageSize,@QueryParam("registre-id") String registreID){
        return acteDecesService.findWithFilters(offset, pageSize,registreID);
    }
    
    @GET @Path("/count")
    public int count(){
        return acteDecesService.count();
    }
    
    
    @GET
    @Path("/numero-acte/{id}")
    public int numeroActe(@PathParam("id") String registreID){
       return acteDecesService.numeroActe(registreID);
    }
    
    @Transactional
    @DELETE 
    @Path("{id}")
    public boolean delete(@PathParam("id") String id){
        return acteDecesService.supprimer(id);
    }
}
