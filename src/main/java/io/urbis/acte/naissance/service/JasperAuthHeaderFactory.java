/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.acte.naissance.service;

import java.util.Base64;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import org.eclipse.microprofile.rest.client.ext.ClientHeadersFactory;

/**
 *
 * @author florent
 */
@ApplicationScoped
public class JasperAuthHeaderFactory implements ClientHeadersFactory{

    @Override
    public MultivaluedMap<String, String> update(MultivaluedMap<String, String> incomingHeaders, 
            MultivaluedMap<String, String> clientOutgoingHeaders) {
        
        MultivaluedMap<String, String> result = new MultivaluedHashMap<>();
        
        String basicAuth = "Basic "+ Base64.getEncoder().encodeToString("jasperadmin:bitnami".getBytes());
        result.add("Authorization", basicAuth);
        
        return result;
    }
    
}
