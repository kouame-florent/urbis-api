/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.acte.mariage.api;

import io.urbis.common.dto.SituationMatrimonialeDto;
import io.urbis.common.service.SituationMatrimonialeService;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

/**
 *
 * @author florent
 */
@Path("/situations-matrimoniales")
@Tag(name = "acte de mariage")
public class SituationMatrimonialeResource {
    
    @Inject
    SituationMatrimonialeService situationMatrimonialeService;
    
    @GET
    public List<SituationMatrimonialeDto> findAll(){
      return  situationMatrimonialeService.findAll();
    }
}
