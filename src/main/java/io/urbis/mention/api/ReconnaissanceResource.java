/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.mention.api;

import io.urbis.mention.dto.MentionReconnaissanceDto;
import io.urbis.mention.service.MentionReconnaissanceService;
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
@Path("/mentions/reconnaissances")
@Tag(name = "mention")
public class ReconnaissanceResource {
    
    @Inject
    MentionReconnaissanceService reconnaissanceService;
    
    @Transactional
    @POST
    public void create(@NotNull MentionReconnaissanceDto dto){
        reconnaissanceService.createMention(dto);
    }
    
    @GET
    public Set<MentionReconnaissanceDto> findByActeNaissance(@QueryParam("acte-naissance-id") @NotBlank String acteNaissanceID){
       return reconnaissanceService.findByActeNaissance(acteNaissanceID);
    }
    
    @Transactional
    @DELETE @Path("{id}")
    public void delete(@PathParam("id") String id){
        reconnaissanceService.deleteMention(id);
    }
}
