/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.acte.deces.api;


import io.urbis.acte.deces.dto.LienDeclarantDto;
import io.urbis.acte.deces.service.LienDeclarantService;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 *
 * @author florent
 */
@Path("/deces/liens-declarant")
public class LienDeclarantResource {
    
    @Inject
    LienDeclarantService lienDeclarantService;
    
    @GET
    public List<LienDeclarantDto> findAll(){
        return lienDeclarantService.findAllLienDeclarant();
    }
    
}
