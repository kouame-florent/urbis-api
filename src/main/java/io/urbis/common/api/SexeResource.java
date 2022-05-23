/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.common.api;


import io.urbis.common.dto.SexeDto;
import io.urbis.common.service.SexeService;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 *
 * @author florent
 */
@Path("/sexes")
public class SexeResource {
    
    @Inject
    SexeService sexeService;
    
    @GET
    public List<SexeDto> findAll(){
      return  sexeService.findAll();
    }
}
