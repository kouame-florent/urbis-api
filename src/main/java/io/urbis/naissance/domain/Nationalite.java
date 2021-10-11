/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.naissance.domain;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 *
 * @author florent
 */
public enum Nationalite {
    
    IVOIRIENNE,
    FRANCAISE;
    
    public static Nationalite fromString(String nationalite){
        for(var t : Nationalite.values()){
            if(t.name().equalsIgnoreCase(nationalite)){
                return Nationalite.valueOf(t.name());
            }
        }
        
        Response res = Response.status(Response.Status.BAD_REQUEST)
                   .entity(new IllegalArgumentException(nationalite)).build();
        throw new WebApplicationException(res);
       
    }
}
