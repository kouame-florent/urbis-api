/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.registre.api;

import io.urbis.dto.CentreDto;
import io.urbis.registre.service.CentreService;
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
    
    
    
}
