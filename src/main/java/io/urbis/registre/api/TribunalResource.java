/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.registre.api;

import io.urbis.registre.dto.TribunalDto;
import io.urbis.registre.service.TribunalService;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 *
 * @author florent
 */
@Path("/tribunaux")
public class TribunalResource {
    
    @Inject
    TribunalService tribunalService;
    
    @GET
    public List<TribunalDto> findAll(){
        return tribunalService.findAll();
    }
    
    @GET @Path("active")
    public TribunalDto findActive(){
        return this.tribunalService.findActiveTribunal();
    }
}
