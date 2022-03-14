/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.acte.naissance.service;


import io.urbis.registre.service.*;
import java.io.File;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

/**
 *
 * @author florent
 */

@RegisterRestClient
@RegisterClientHeaders(JasperAuthHeaderFactory.class)
public interface ActeNaissanceRestClient {
   
    @GET
    @Path("/urbis/{tenant}/acte_naissance.pdf")
    public File downloadActeNaissance(@PathParam("tenant") String tenant,@QueryParam("ACTE_NAISSANCE_ID") String acteNaissanceID);
    
}
