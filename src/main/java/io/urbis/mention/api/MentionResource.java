/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.mention.api;

import io.urbis.naissance.dto.MentionDto;
import io.urbis.mention.service.MentionService;
import java.util.List;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

/**
 *
 * @author florent
 */
@Path("/mentions")
public class MentionResource {
    
    @Inject
    MentionService mentionService;
    
    @POST
    public void create(@NotNull MentionDto mentionDto){
        mentionService.creerMention(mentionDto);
    }
    
    @GET 
    public List<MentionDto> findByActeNaissance(@QueryParam("acte-naissance-id") String id){
        return mentionService.findByActeNaissance(id);
    }
}
