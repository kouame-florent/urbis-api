/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.acte.naissance.api;

import io.urbis.acte.naissance.dto.MentionDecesDto;
import io.urbis.acte.naissance.service.MentionDecesService;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

/**
 *
 * @author florent
 */
@Path("/mentions/deces")
@Tag(name = "mention")
public class DecesResource {
    
    @Inject
    MentionDecesService decesService;
    
    @Transactional
    @POST
    public void create(@NotNull MentionDecesDto dto){
        decesService.createMention(dto);
    }
    
    @GET
    public Set<MentionDecesDto> findByActeNaissance(@QueryParam("acte-naissance-id") @NotBlank String acteNaissanceID){
       return decesService.findByActeNaissance(acteNaissanceID);
    }
    
    @Transactional
    @DELETE @Path("{id}")
    public void delete(@PathParam("id") String id){
        decesService.deleteMention(id);
    }
}
