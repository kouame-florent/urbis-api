/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.demande.api;

import io.urbis.demande.dto.DemandeDto;
import io.urbis.demande.service.DemandeService;
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

/**
 *
 * @author florent
 */
@Path("/demandes")
public class DemandeResource {
    
    @Inject
    DemandeService demandeService ;
    
    @GET @Path("{id}")
    public DemandeDto findById(@PathParam("id") String id){
        return demandeService.findById(id);
    }
    
    @Transactional
    @POST
    public void create(DemandeDto demandeDto) throws SQLException{
         demandeService.creer(demandeDto);
        
    }
    
    @Transactional
    @PUT @Path("{id}")
    public void update(@PathParam("id")String id, DemandeDto demandeDto) throws SQLException{
        demandeService.modifier(id, demandeDto);
    }
    
    /*
    @Transactional
    @PATCH @Path("{id}")
    public void patch(@PathParam("id") String id,ActeNaissancePatchDto patchDto){
      StatutActeNaissance statutActe = StatutActeNaissance.fromString(patchDto.getStatut());
      switch(statutActe){
          case VALIDE:
             acteNaissanceService.validerActe(id);
             break;
          case ANNULE:
             acteNaissanceService.annulerActe(id);
             break;
         
      }
      
    }
    */
    
    /*
    @Transactional
    @GET
    public List<DemandeDto> findWithFilters(@QueryParam("offset") int offset, 
            @QueryParam("page-size") int pageSize,@QueryParam("registre-id") String registreID){
        return demandeService.findWithFilters(offset, pageSize,registreID);
    }
    
    
    @GET @Path("/count")
    public int count(){
        return demandeService.count();
    }
    
    
    
    
    @Transactional
    @DELETE 
    @Path("{id}")
    public boolean delete(@PathParam("id") String id){
        return demandeService.supprimer(id);
    }
*/
}
