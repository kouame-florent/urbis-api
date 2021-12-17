/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.common.exception.mapper;

import javax.persistence.EntityExistsException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author florent
 */
@Provider
public class EntityExistExceptionMapper implements ExceptionMapper<EntityExistsException>{

    @Override
    public Response toResponse(EntityExistsException e) {
        return Response.status(Response.Status.CONFLICT)
                .entity(e.getMessage()).build();
    }
    
}
