/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.common.domain;

import io.quarkus.security.identity.SecurityIdentity;
import io.urbis.security.service.AuthenticationContext;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.ws.rs.core.SecurityContext;

/**
 *
 * @author florent
 */
@Dependent
public class AuditingEntityListener {
    
    //@Inject
    //SecurityContext  securityContext;
    
    //@Inject
    //SecurityIdentity secId;
    
    @Inject
    AuthenticationContext authCtx;
    
    @PrePersist
    public void preCreate(BaseEntity auditable){
        auditable.id = UUID.randomUUID().toString();
        auditable.created = LocalDateTime.now();
        auditable.updatedBy = authCtx.getUserLogin();
    }
    
    @PreUpdate
    public void preUpdate(BaseEntity auditable){
        auditable.updated = LocalDateTime.now();
        auditable.updatedBy = authCtx.getUserLogin();
    }
}
