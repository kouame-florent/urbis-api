/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.registre.api;

import io.urbis.registre.service.EtatService;
import java.io.File;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

/**
 *
 * @author florent
 */
@Path("/etats")
public class EtatResource {
    
    @Inject
    Logger log;
    
    @Inject
    @RestClient
    EtatService etatService;
    
    @GET
    @Path("/registres")
   // @Produces("application/pdf")
    public File registres(){
        File file = etatService.downloadRegistre();
        log.infof("-- ETAT REGISTRE NOM: %s", file.getName());
        log.infof("-- ETAT REGISTRE ABSOLUTE PATH: %s", file.getAbsolutePath());
        log.infof("-- ETAT REGISTRE SIZE: %s", file.length());
        
        return file;
    }
    
    @GET
    @Path("/acte_naissance")
    public File acteNaissance(@QueryParam("ACTE_NAISSANCE_ID") String acteNaissanceID){
        File file = etatService.downloadActeNaissance(acteNaissanceID);
        log.infof("-- ETAT REGISTRE NOM: %s", file.getName());
        log.infof("-- ETAT REGISTRE ABSOLUTE PATH: %s", file.getAbsolutePath());
        log.infof("-- ETAT REGISTRE SIZE: %s", file.length());
        
        return file;
    }
    
}
