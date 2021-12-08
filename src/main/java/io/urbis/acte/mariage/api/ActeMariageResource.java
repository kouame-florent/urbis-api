/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.acte.mariage.api;

import io.urbis.acte.mariage.dto.ActeMariageDto;
import io.urbis.acte.mariage.service.ActeMariageService;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

/**
 *
 * @author florent
 */
@Path("/mariages")
@Tag(name = "acte de mariage")
public class ActeMariageResource {
    
    @Inject
    ActeMariageService acteMariageService;
    
    @Transactional
    @POST
    public void create(ActeMariageDto dto){
        acteMariageService.create(dto);
    }
    
}
