/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.urbis.security.domain;

import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.Roles;
import javax.persistence.Entity;
import javax.persistence.Table;
import io.quarkus.security.jpa.UserDefinition;
import io.quarkus.security.jpa.Username;
import io.urbis.common.domain.BaseEntity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.ManyToMany;

/**
 *
 * @author florent
 */
@Entity
@Table(name = "user")
@UserDefinition
public class User extends BaseEntity {
    
    @Username
    public String name;

    @Password
    public String password;

    @ManyToMany
    @Roles
    public List<Role> roles = new ArrayList<>();
}
