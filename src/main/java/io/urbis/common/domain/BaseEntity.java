/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.common.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

/**
 *
 * @author florent
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity extends PanacheEntityBase implements Serializable{
    
   
    @Id
    public String id = UUID.randomUUID().toString();
    
    @Version
    public long version;
    
    public LocalDateTime created ;
    public LocalDateTime updated ;
    
    @Column(nullable = false, name = "updated_by")
    public String updatedBy;
    
    /*
    @PrePersist
    public void preCreate(){
        //auditable.id = UUID.randomUUID().toString();
       SecurityIdentity authCtx = CDI.current().select(SecurityIdentity.class).get();
        //AuthenticationContext authCtx = CDI.current().select(AuthenticationContext.class).get();
        //LOG.log(Level.INFO, "--> SECID: {0}", authCtx);
        created = LocalDateTime.now();
        updatedBy = authCtx.getPrincipal().getName();
        //updatedBy = authCtx.userLogin();
        //updatedBy = "lisa";
       
    }
    
    @PreUpdate
    public void preUpdate(){
        updated = LocalDateTime.now();
       // auditable.updatedBy = authCtx.userLogin();
       //updatedBy = "lisa";
    }
    */
    
    
}
