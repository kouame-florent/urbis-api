/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.mention.api;

import io.urbis.mention.dto.AdoptionDto;
import io.urbis.mention.service.MentionAdoptionService;
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
@Path("/mentions/adoptions")
@Tag(name = "mention")
public class AdoptionResource {
    
    @Inject
    MentionAdoptionService adoptionService;
    
    @Transactional
    @POST
    public void create(@NotNull AdoptionDto dto){
        adoptionService.creerMention(dto);
    }
    
    @GET
    public List<AdoptionDto> findByActeNaissance(@NotBlank String acteNaissanceID){
       return adoptionService.findByActeNaissance(acteNaissanceID);
    }
}
