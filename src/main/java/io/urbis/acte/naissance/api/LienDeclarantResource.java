/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.acte.naissance.api;

import io.urbis.acte.naissance.dto.LienDeclarantDto;
import io.urbis.acte.naissance.service.LienDeclarantService;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 *
 * @author florent
 */
@Path("/liens-declarant")
public class LienDeclarantResource {
    
    @Inject
    LienDeclarantService lienDeclarantService;
    
    @GET
    public List<LienDeclarantDto> findAll(){
        return lienDeclarantService.findAllLienDeclarant();
    }
    
}
