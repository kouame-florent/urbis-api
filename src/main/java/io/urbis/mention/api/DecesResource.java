/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.mention.api;

import io.urbis.mention.dto.DecesDto;
import io.urbis.mention.service.MentionDecesService;
import java.util.List;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
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
    public void create(@NotNull DecesDto dto){
        decesService.creerMention(dto);
    }
    
    @GET
    public List<DecesDto> findByActeNaissance(@NotBlank String acteNaissanceID){
       return decesService.findByActeNaissance(acteNaissanceID);
    }
}
