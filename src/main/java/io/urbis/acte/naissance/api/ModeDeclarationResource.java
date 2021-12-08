/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.acte.naissance.api;




import io.urbis.acte.naissance.dto.ModeDeclarationDto;
import io.urbis.acte.naissance.service.ModeDeclarationService;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 *
 * @author florent
 */
@Path("/modes-declaration")
public class ModeDeclarationResource {
    
    @Inject
    ModeDeclarationService modeDeclarationService;
    
    @GET
    public List<ModeDeclarationDto> findAll(){
        return modeDeclarationService.findAllModeDeclaration();
    }
}
