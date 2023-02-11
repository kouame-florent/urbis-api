/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.urbis.security.domain;

import io.quarkus.security.jpa.RolesValue;
import io.urbis.common.domain.BaseEntity;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 *
 * @author florent
 */
@Entity
@Table(name = "role")
public class Role extends BaseEntity{
    
    @ManyToMany(mappedBy = "roles")
    public List<User> users ;
    
    @RolesValue
    public String value;

    public Role() {
    }

    public Role(String value) {
        this.value = value;
    }
    
    
}
