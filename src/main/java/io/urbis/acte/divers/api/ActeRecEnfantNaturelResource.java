/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.acte.divers.api;

import io.urbis.acte.divers.dto.ActeRecEnfantNaturelDto;
import io.urbis.acte.divers.service.ActeRecEnfantNaturelService;
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
@Path("/divers/reconnaissance-enfant-naturel")
@Tag(name = "acte divers reconnaissance enfant naturel")
public class ActeRecEnfantNaturelResource {
    
    @Inject
    ActeRecEnfantNaturelService acteRecEnfantNaturelService;
    
    @Transactional
    @POST
    public void create(ActeRecEnfantNaturelDto dto){
        acteRecEnfantNaturelService.creer(dto);
    }
    
    @Transactional
    @PUT @Path("{id}")
    public void update(@PathParam("id") String id,ActeRecEnfantNaturelDto dto){
        acteRecEnfantNaturelService.modifier(id, dto);
    }
    
    @GET @Path("{id}")
    public ActeRecEnfantNaturelDto findById(@PathParam("id") String id){
        return acteRecEnfantNaturelService.findById(id);
    }
    
    @Transactional
    @DELETE @Path("{id}")
    public boolean delete(@PathParam("id") String id){
        return acteRecEnfantNaturelService.supprimer(id);
    }
    
    @GET
    public List<ActeRecEnfantNaturelDto> findWithFilters(@QueryParam("offset") int offset, 
            @QueryParam("page-size") int pageSize,@QueryParam("registre-id") String registreID){
        return acteRecEnfantNaturelService.findWithFilters(offset, pageSize,registreID);
    }
    
    @GET @Path("/count")
    public int count(){
        return acteRecEnfantNaturelService.count();
    }
    
    @GET
    @Path("/numero-acte/{id}")
    public int numeroActe(@PathParam("id") String registreID){
       return acteRecEnfantNaturelService.numeroActe(registreID);
    }
}
