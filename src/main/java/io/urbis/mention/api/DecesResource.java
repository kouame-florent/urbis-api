/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.mention.api;

import io.urbis.mention.domain.Adoption;
import io.urbis.mention.dto.AdoptionDto;
import io.urbis.mention.dto.DecesDto;
import io.urbis.mention.service.AdoptionService;
import io.urbis.mention.service.DecesService;
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
@Path("/mentions/deces")
public class DecesResource {
    
    @Inject
    DecesService decesService;
    
    @POST
    public void create(@NotNull DecesDto dto){
        decesService.creerMention(dto);
    }
    
    @GET
    public List<DecesDto> findByActeNaissance(@NotBlank String acteNaissanceID){
       return decesService.findByActeNaissance(acteNaissanceID);
    }
}
