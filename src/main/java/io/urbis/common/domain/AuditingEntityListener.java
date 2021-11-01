/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.common.domain;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

/**
 *
 * @author florent
 */
public class AuditingEntityListener {
    
    @PrePersist
    public void preCreate(BaseEntity auditable){
        auditable.id = UUID.randomUUID().toString();
        auditable.created = LocalDateTime.now();
    }
    
    @PreUpdate
    public void preUpdate(BaseEntity auditable){
        auditable.updated = LocalDateTime.now();
    }
}
