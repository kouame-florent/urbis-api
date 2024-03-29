/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.registre.api;

import io.urbis.registre.domain.StatutRegistre;
import io.urbis.registre.dto.RegistrePatchDto;
import io.urbis.registre.dto.RegistreDto;
import io.urbis.registre.service.RegistreService;
import java.time.LocalDate;
import java.util.List;
import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

/**
 *
 * @author florent
 */

@Path("/registres")
@Tag(name = "registre", description = "All the registre methods")
public class RegistreResource {
    
    @Inject 
    RegistreService registreService;
    
    
    @GET @Path("/count/{type}")
    public int count(@PathParam("type") String typeRegistre,@QueryParam("annee") int annee,
            @QueryParam("numero") int numero){
        return this.registreService.count(typeRegistre,annee,numero);
    }
    
    //@PermitAll
    @Transactional
    @GET
    public List<RegistreDto> findWithFilters(@QueryParam("offset") int offset, @QueryParam("pageSize") int pageSize,
            @QueryParam("type") String typeRegistre, @QueryParam("annee") int annee,
            @QueryParam("numero") int numero){
        return registreService.findWithFilters(offset, pageSize, typeRegistre, annee, numero,LocalDate.now());
    }
    
    //@RolesAllowed("CHEF_ETAT_CIVIL")
    @Transactional
    @POST
    public void create(RegistreDto registreDto){
         registreService.creer(registreDto);
    }
    
    
   
    @Transactional
    @PATCH @Path("{id}")
    public void patch(@PathParam("id") String id,RegistrePatchDto patchDto){
      StatutRegistre statutRegistre = StatutRegistre.fromString(patchDto.getStatut());
      switch(statutRegistre){
          case VALIDE:
             registreService.validerRegistre(id,patchDto);
             break;
          case ANNULE:
             registreService.annulerRegistre(id, patchDto.getMotifAnnulation());
             break;
          case CLOTURE:
              registreService.cloturerRegistre(id);
              break;
      }
      
    }
    
    /*
    @Transactional
    @PATCH @Path("{id}")
    public void annuler(@PathParam("id") String id,RegistrePatchDto annulation){
      Registre reg = Registre.findById(id);
      registreService.annulerRegistre(id, annulation.getMotifAnnulation());
    }
*/
    
    
    @GET @Path("{id}")
    public RegistreDto findById(@PathParam("id") String id){
       return registreService.findById(id);
    }
    
    @Transactional
    @DELETE @Path("{id}")
    public void delete(@PathParam("id") String id){
        registreService.supprimer(id);
    }
    
    @GET @Path("/courant")
    public RegistreDto courant(@QueryParam("type") String type){      
       return registreService.registreCourant(type);
    }
    
    @GET @Path("annee-courante")
    public int anneeCourante(){
       return registreService.anneeCourante();
    }
    
    @GET @Path("numero/{type}")
    public int numeroRegistre(@PathParam("type") String typeRegistre, @QueryParam("annee") int annee){
       return registreService.numeroRegistre(typeRegistre,annee);
    }
    
    @GET @Path("numero-premier-acte-courant/{type}")
    public int numeroPremierActeCourant(@PathParam("type") String typeRegistre,@QueryParam("annee") int annee){
       return registreService.numeroPremierActeCourant(typeRegistre,annee);
    }
    
    @GET @Path("numero-premier-acte/{type}")
    public int numeroPremierActe(@PathParam("type") String typeRegistre,@QueryParam("annee") int annee,
            @QueryParam("numero") int numero){
       return registreService.numeroPremierActe(typeRegistre,annee,numero);
    }
    
    @GET @Path("numero-dernier-acte/{type}")
    public int numeroDernierActe(@PathParam("type") String typeRegistre,@QueryParam("annee") int annee,
            @QueryParam("numero") int numero,@QueryParam("numero-premier-acte") int numeroPremierActe,
            @QueryParam("nombre-feuillets") int nombreFeuillets){
       return registreService.numeroDernierActe(typeRegistre,annee,numero,numeroPremierActe,nombreFeuillets);
    }
    
}
