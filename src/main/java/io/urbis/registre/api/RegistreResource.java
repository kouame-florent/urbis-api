/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.registre.api;

import io.urbis.domain.Registre;
import io.urbis.domain.StatutRegistre;
import io.urbis.dto.RegistreDto;
import io.urbis.registre.service.LocaliteService;
import io.urbis.registre.service.RegistreService;
import java.util.List;
import javax.annotation.security.RolesAllowed;
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
@Path("/registres")
public class RegistreResource {
    
    @Inject 
    RegistreService registreService;
    
   
    @GET
    public List<RegistreDto> findAll(){
        return this.registreService.findAll();
    } 
    
    @GET @Path("/count/{type}")
    public int count(@PathParam("type") String typeRegistre,@QueryParam("annee") int annee,
            @QueryParam("numero") int numero){
        return this.registreService.count(typeRegistre,annee,numero);
    }
    
    @Transactional
    @GET @Path("{type}")
    public List<RegistreDto> findWithFilters(@QueryParam("offset") int offset, @QueryParam("pageSize") int pageSize,
            @PathParam("type") String typeRegistre, @QueryParam("annee") int annee, @QueryParam("numero") int numero){
        return this.registreService.findWithFilters(offset, pageSize, typeRegistre, annee, numero);
    }
    
    //@RolesAllowed("CHEF_ETAT_CIVIL")
    @Transactional
    @POST
    public RegistreDto create(RegistreDto registreDto){
        return this.registreService.creerRegistre(registreDto);
    }
    
    @RolesAllowed("CHEF_ETAT_CIVIL")
    @Transactional
    @PUT @Path("{id}")
    public void valider(@PathParam("id") String id){
      Registre reg = Registre.findById(id);
      registreService.validerRegistre(id);
    }
    
    
    @GET @Path("annee")
    public int annee(){
       return registreService.annee();
    }
    
    @GET @Path("numero/{type}/{annee}")
    public int numero(@PathParam("type") String typeRegistre, @PathParam("annee") int annee){
       return registreService.numeroRegistre(typeRegistre,annee);
    }
    
    @GET @Path("numero-premier-acte/{type}")
    public int numeroPremierActe(@PathParam("type") String typeRegistre){
       return registreService.numeroPremierActe(typeRegistre);
    }
    
}
