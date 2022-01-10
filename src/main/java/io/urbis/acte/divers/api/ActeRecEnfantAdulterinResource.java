/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.acte.divers.api;

import io.urbis.acte.divers.dto.ActeRecEnfantAdulterinDto;
import io.urbis.acte.divers.service.ActeRecEnfantAdulterinService;
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
@Path("/divers/reconnaissance-enfant-adulterin")
@Tag(name = "acte divers reconnaissance enfant ulterin")
public class ActeRecEnfantAdulterinResource {
    
    @Inject
    ActeRecEnfantAdulterinService acteRecEnfantAdulterinService;
    
    @Transactional
    @POST
    public void create(ActeRecEnfantAdulterinDto dto){
        acteRecEnfantAdulterinService.creer(dto);
    }
    
    @Transactional
    @PUT @Path("{id}")
    public void update(@PathParam("id") String id,ActeRecEnfantAdulterinDto dto){
        acteRecEnfantAdulterinService.modifier(id, dto);
    }
    
    @GET @Path("{id}")
    public ActeRecEnfantAdulterinDto findById(@PathParam("id") String id){
        return acteRecEnfantAdulterinService.findById(id);
    }
    
    @Transactional
    @DELETE @Path("{id}")
    public boolean delete(@PathParam("id") String id){
        return acteRecEnfantAdulterinService.supprimer(id);
    }
    
    @GET
    public List<ActeRecEnfantAdulterinDto> findWithFilters(@QueryParam("offset") int offset, 
            @QueryParam("page-size") int pageSize,@QueryParam("registre-id") String registreID){
        return acteRecEnfantAdulterinService.findWithFilters(offset, pageSize,registreID);
    }
    
    @GET @Path("/count")
    public int count(){
        return acteRecEnfantAdulterinService.count();
    }
    
    @GET
    @Path("/numero-acte/{id}")
    public int numeroActe(@PathParam("id") String registreID){
       return acteRecEnfantAdulterinService.numeroActe(registreID);
    }
}
