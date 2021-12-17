/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.acte.mariage.api;

import io.urbis.acte.mariage.dto.SituationMatrimonialeDto;
import io.urbis.acte.mariage.service.SituationMatrimonialeService;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 *
 * @author florent
 */
@Path("/situations-matrimoniales")
public class SituationMatrimonialeResource {
    
    @Inject
    SituationMatrimonialeService situationMatrimonialeService;
    
    @GET
    public List<SituationMatrimonialeDto> findAll(){
      return  situationMatrimonialeService.findAllSituation();
    }
}
