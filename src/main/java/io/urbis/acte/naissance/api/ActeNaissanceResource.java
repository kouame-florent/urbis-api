/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.acte.naissance.api;



import io.urbis.acte.naissance.dto.ActeNaissanceDto;
import io.urbis.acte.naissance.service.ActeNaissanceService;
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
@Path("/naissances")
@Tag(name = "acte de naissance")
public class ActeNaissanceResource {
    
    @Inject
    ActeNaissanceService acteNaissanceService;
    
    @GET @Path("{id}")
    public ActeNaissanceDto findById(@PathParam("id") String id){
        return acteNaissanceService.findById(id);
    }
    
    @Transactional
    @POST
    public String create(ActeNaissanceDto acteNaissanceDto) throws SQLException{
        return acteNaissanceService.creerActe(acteNaissanceDto);
        
    }
    
    @Transactional
    @PUT @Path("{id}")
    public void update(@PathParam("id")String id, ActeNaissanceDto acteNaissanceDto) throws SQLException{
        acteNaissanceService.updateActe(id, acteNaissanceDto);
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
    
    @Transactional
    @GET
    public List<ActeNaissanceDto> findWithFilters(@QueryParam("offset") int offset, 
            @QueryParam("page-size") int pageSize,@QueryParam("registre-id") String registreID){
        return acteNaissanceService.findWithFilters(offset, pageSize,registreID);
    }
    
    
    @GET @Path("/count")
    public int count(){
        return acteNaissanceService.count();
    }
    
    
    @GET
    @Path("/numero-acte/{id}")
    public int numeroActe(@PathParam("id") String registreID){
       return acteNaissanceService.numeroActe(registreID);
    }
    
    @Transactional
    @DELETE 
    @Path("{id}")
    public boolean delete(@PathParam("id") String id){
        return acteNaissanceService.supprimer(id);
    }
}
