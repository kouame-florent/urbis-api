/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.mention.api;

import io.urbis.mention.dto.DissolutionMariageDto;
import io.urbis.mention.service.DissolutionMariageService;
import io.urbis.naissance.domain.ActeNaissance;
import java.util.List;
import javax.inject.Inject;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 *
 * @author florent
 */
@Path("/mentions/dissolutions")
public class DissolutionMariageResource {
    
    @Inject
    DissolutionMariageService dissolutionMariageService;
    
    @POST
    public void create(@NotNull DissolutionMariageDto dto){
        dissolutionMariageService.creerMention(dto);
    }
    
    @GET
    public List<DissolutionMariageDto> findByActeNaissance(@NotBlank String acteNaissanceID){
       return dissolutionMariageService.findByActeNaissance(acteNaissanceID);
    }
}
