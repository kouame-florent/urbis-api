/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.registre.api;

import io.urbis.dto.RegistreDto;
import io.urbis.registre.service.LocaliteService;
import io.urbis.registre.service.RegistreService;
import java.util.List;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

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
    
    @Transactional
    @POST
    public RegistreDto create(RegistreDto registreDto){
        return this.registreService.creerRegistre(registreDto);
    }
    
    
    @GET @Path("annee")
    public int annee(){
       return registreService.annee();
    }
    
    @GET @Path("numero/{type}")
    public long numero(@PathParam("type") String typeRegistre){
       return registreService.numeroRegistre(typeRegistre);
    }
    
    @GET @Path("numero-premier-acte/{type}")
    public long numeroPremierActe(@PathParam("type") String typeRegistre){
       return registreService.numeroPremierActe(typeRegistre);
    }
    
}
