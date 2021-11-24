/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.param.api;

import io.urbis.param.dto.TitreOfficierDto;
import io.urbis.param.service.TitreOfficierService;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 *
 * @author florent
 */
@Path("/titres-officiers")
public class TitreOfficierResource {
    
    @Inject
    TitreOfficierService titreOfficierService;
   
    @GET
    public List<TitreOfficierDto> findAll(){
        return titreOfficierService.findAll();
    }
}
