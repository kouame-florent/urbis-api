/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.mention.api;

import io.urbis.mention.dto.ReconnaissanceDto;
import io.urbis.mention.service.ReconnaissanceService;
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
@Path("/mentions/reconnaissances")
public class ReconnaissanceResource {
    
    @Inject
    ReconnaissanceService reconnaissanceService;
    
    @POST
    public void create(@NotNull ReconnaissanceDto dto){
        reconnaissanceService.creerMention(dto);
    }
    
    @GET
    public List<ReconnaissanceDto> findByActeNaissance(@NotBlank String acteNaissanceID){
       return reconnaissanceService.findByActeNaissance(acteNaissanceID);
    }
}
