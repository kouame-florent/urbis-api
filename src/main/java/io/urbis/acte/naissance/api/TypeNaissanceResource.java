/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.acte.naissance.api;


import io.urbis.acte.naissance.dto.TypeNaissanceDto;
import io.urbis.acte.naissance.service.TypeNaissanceService;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 *
 * @author florent
 */
@Path("/naissances/types-naissance")
public class TypeNaissanceResource {
    
    @Inject
    TypeNaissanceService typeNaissanceService;
    
    @GET
    public List<TypeNaissanceDto> findAll(){
        return typeNaissanceService.findAll();
    }
}
