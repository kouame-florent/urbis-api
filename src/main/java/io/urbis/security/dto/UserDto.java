/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.security.dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author florent
 */

public class UserDto {
    
    private String id;

    private String name;

    private String password;
    
    private String rolesView;

    private List<String> roles = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getRolesView() {
        return rolesView;
    }

    public void setRolesView(String rolesView) {
        this.rolesView = rolesView;
    }

    
    
    
}
