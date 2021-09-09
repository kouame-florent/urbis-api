/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.registre.api;

import io.urbis.dto.TypeRegistreDto;
import io.urbis.registre.service.TypeRegistreService;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 *
 * @author florent
 */
@Path("/types-registre")
public class TypeRegistreResource {
    
    @Inject
    TypeRegistreService typeRegistreService;
    
    @GET
    public List<TypeRegistreDto> findAll(){
        return this.typeRegistreService.findAllTypeRegistres();
    }
    
}
