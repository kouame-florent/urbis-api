/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.acte.divers.api;

import io.urbis.acte.divers.dto.ActeConsReconnaissanceDto;
import io.urbis.acte.divers.service.ActeConsReconnaissanceService;
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
@Path("/divers/consentement-reconnaissance")
@Tag(name = "acte divers consentement reconnaissance")
public class ActeConsReconnaissanceResource {
    
    @Inject
    ActeConsReconnaissanceService acteConsReconnaissanceService;
    
    @Transactional
    @POST
    public void create(ActeConsReconnaissanceDto dto){
        acteConsReconnaissanceService.creer(dto);
    }
    
    @Transactional
    @PUT @Path("{id}")
    public void update(@PathParam("id") String id,ActeConsReconnaissanceDto dto){
        acteConsReconnaissanceService.modifier(id, dto);
    }
    
    @GET @Path("{id}")
    public ActeConsReconnaissanceDto findById(@PathParam("id") String id){
        return acteConsReconnaissanceService.findById(id);
    }
    
    @Transactional
    @DELETE @Path("{id}")
    public boolean delete(@PathParam("id") String id){
        return acteConsReconnaissanceService.supprimer(id);
    }
    
    @GET
    public List<ActeConsReconnaissanceDto> findWithFilters(@QueryParam("offset") int offset, 
            @QueryParam("page-size") int pageSize,@QueryParam("registre-id") String registreID){
        return acteConsReconnaissanceService.findWithFilters(offset, pageSize,registreID);
    }
    
    @GET @Path("/count")
    public int count(){
        return acteConsReconnaissanceService.count();
    }
    
    @GET
    @Path("/numero-acte/{id}")
    public int numeroActe(@PathParam("id") String registreID){
       return acteConsReconnaissanceService.numeroActe(registreID);
    }
}
