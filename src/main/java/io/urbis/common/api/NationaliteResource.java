/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.common.api;


import io.urbis.common.dto.NationaliteDto;
import io.urbis.common.service.NationaliteService;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 *
 * @author florent
 */
@Path("/nationalites")
public class NationaliteResource {
    
    @Inject
    NationaliteService nationaliteService;
    
    @GET
    public List<NationaliteDto> findAll(){
      return nationaliteService.findAll();
    }
}
