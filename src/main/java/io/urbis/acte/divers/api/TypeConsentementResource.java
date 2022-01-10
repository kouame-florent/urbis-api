/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.acte.divers.api;

import io.urbis.acte.divers.dto.TypeConsentementDto;
import io.urbis.acte.divers.service.TypeConsentementService;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

/**
 *
 * @author florent
 */
@Path("/divers/types-consentement")
@Tag(name = "acte divers reconnaissance enfant ulterin")
public class TypeConsentementResource {
    
    @Inject
    TypeConsentementService typeConsentementService;
    
    @GET
    public List<TypeConsentementDto> findAll(){
      return  typeConsentementService.findAllSituation();
    }
}
