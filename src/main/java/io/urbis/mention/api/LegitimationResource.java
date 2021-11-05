/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.mention.api;

import io.urbis.mention.dto.LegitimationDto;
import io.urbis.mention.service.MentionLegitimationService;
import java.util.List;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

/**
 *
 * @author florent
 */
@Path("/mentions/legitimations")
@Tag(name = "mention")
public class LegitimationResource {
    
    @Inject
    MentionLegitimationService legitimationService;
    
    @Transactional
    @POST
    public void create(@NotNull LegitimationDto dto){
        legitimationService.createMention(dto);
    }
    
    @GET
    public List<LegitimationDto> findByActeNaissance(@NotBlank String acteNaissanceID){
       return legitimationService.findByActeNaissance(acteNaissanceID);
    }
    
    @Transactional
    @DELETE @Path("{id}")
    public void delete(@PathParam("id") String id){
        legitimationService.deleteMention(id);
    }
}
