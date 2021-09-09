/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.registre.api;

import io.urbis.dto.OfficierEtatCivilDto;
import io.urbis.registre.service.OfficierService;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 *
 * @author florent
 */
@Path("/officiers")
public class OfficierResource {
    
    @Inject
    OfficierService officierService;
    
    @GET
    public List<OfficierEtatCivilDto> findAll(){
        return this.officierService.findAll();
    }
}
