/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.param.api;

import io.urbis.param.dto.CentreDto;
import io.urbis.param.service.CentreService;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 *
 * @author florent
 */
@Path("/centres")
public class CentreResource {
    
    @Inject
    CentreService centreService;
    
    @GET
    public List<CentreDto> findAll(){
        return this.centreService.findAll();
    }
    
    @GET @Path("active")
    public CentreDto findActive(){
        return this.centreService.findActiveCentre();
    }

}