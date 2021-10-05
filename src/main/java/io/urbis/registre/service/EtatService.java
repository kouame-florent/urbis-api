/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.registre.service;


import java.io.File;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
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
    
}
