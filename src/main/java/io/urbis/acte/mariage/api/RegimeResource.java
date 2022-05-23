/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.acte.mariage.api;

import io.urbis.acte.mariage.dto.RegimeDto;
import io.urbis.acte.mariage.service.RegimeService;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

/**
 *
 * @author florent
 */
@Path("/regimes")
@Tag(name = "acte de mariage")
public class RegimeResource {
    @Inject
    RegimeService regimeService;
    
    @GET
    public List<RegimeDto> findAll(){
      return  regimeService.findAll();
    }
}
