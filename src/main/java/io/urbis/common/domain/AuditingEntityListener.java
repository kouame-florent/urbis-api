/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.common.domain;

import java.time.LocalDateTime;
import java.util.logging.Logger;
import javax.enterprise.context.control.ActivateRequestContext;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

/**
 *
 * @author florent
 */

public class AuditingEntityListener{

    private static final Logger LOG = Logger.getLogger(AuditingEntityListener.class.getName());
   
   //@Inject
   //AuthenticationContext authCtx;
    
    //@Inject @io.quarkus.arc.Unremovable
    //SecurityIdentity authCtx;
    
    @ActivateRequestContext
    @PrePersist
    public void preCreate(BaseEntity auditable){
        //auditable.id = UUID.randomUUID().toString();
       //SecurityIdentity authCtx = CDI.current().select(SecurityIdentity.class).get();
       // AuthenticationContext authCtx = CDI.current().select(AuthenticationContext.class).get();
        //LOG.log(Level.INFO, "--> SECID: {0}", authCtx);
        auditable.created = LocalDateTime.now();
        //auditable.updatedBy = authCtx.getPrincipal().getName();
        //auditable.updatedBy = authCtx.userLogin();
        //updatedBy = "lisa";
       
    }
    
    @PreUpdate
    public void preUpdate(BaseEntity auditable){
        auditable.updated = LocalDateTime.now();
       // auditable.updatedBy = authCtx.userLogin();
       //updatedBy = "lisa";
    }
    
}
