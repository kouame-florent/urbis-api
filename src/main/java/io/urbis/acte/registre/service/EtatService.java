/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.acte.registre.service;


import java.io.File;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

/**
 *
 * @author florent
 */

@RegisterRestClient
@RegisterClientHeaders(JasperAuthHeaderFactory.class)
public interface EtatService {
    
    @GET
    @Path("/urbis/registres.pdf")
    public File downloadRegistre();
    
    @GET
    @Path("/urbis/acte_naissance.pdf")
    public File downloadActeNaissance(@QueryParam("ACTE_NAISSANCE_ID") String acteNaissanceID);
    
}
