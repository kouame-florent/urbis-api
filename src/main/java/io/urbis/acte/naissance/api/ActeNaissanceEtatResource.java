/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.acte.naissance.api;

import java.io.File;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;
import io.urbis.acte.naissance.service.ActeNaissanceRestClient;

/**
 *
 * @author florent
 */
@Path("/naissances/etats")
@Tag(name = "etat acte de naissance")
public class ActeNaissanceEtatResource {
    
    @Inject
    Logger log;
    
    @Inject
    @RestClient
    ActeNaissanceRestClient acteNaissanceRestClient;
    
    @ConfigProperty(name = "URBIS_TENANT")
    String tenant;
    
    @GET
    @Path("/acte_naissance/{tenant}")
    public File downloadActeNaissance(@PathParam("tenant") String tenant,@QueryParam("ACTE_NAISSANCE_ID") String acteNaissanceID){
        File file = acteNaissanceRestClient.downloadActeNaissance(tenant,acteNaissanceID);
        log.infof("-- ETAT REGISTRE TENANT: %s", tenant);
        log.infof("-- ETAT REGISTRE NOM: %s", file.getName());
        log.infof("-- ETAT REGISTRE ABSOLUTE PATH: %s", file.getAbsolutePath());
        log.infof("-- ETAT REGISTRE SIZE: %s", file.length());
        
        return file;
    }
    
}
