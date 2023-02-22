/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.urbis.security.service;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.runtime.StartupEvent;
import java.util.EnumSet;
import javax.enterprise.event.Observes;
import javax.inject.Singleton;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.urbis.security.domain.Role;
import io.urbis.security.domain.RoleValue;
import io.urbis.security.domain.User;

/**
 *
 * @author florent
 */
@Singleton
public class Startup {
    
    @Transactional
    public void init(@Observes StartupEvent evt){
        loadRoles();
        loadAdmin();
    }
    
    
    public void loadRoles(){
       EnumSet<RoleValue> roleNames = EnumSet.allOf(RoleValue.class);
       roleNames.stream().filter(this::roleNotExist).forEach(this::createRole);
    }
    
   
    public void loadAdmin(){
        if(userNotExist("admin")){
            User user = new User();
            user.name = "admin";
            user.password = BcryptUtil.bcryptHash("admin");
            user.updatedBy = "system";
            
            PanacheQuery<Role> rq = Role.find("role", RoleValue.ADMIN.name());
            Role role = rq.firstResult();
            if(role != null){
                 user.roles.add(role);
            }else{
                throw new EntityNotFoundException("role ADMIN not found");
            }
            
            user.persist();
       }
    }
    
    
    private boolean userNotExist(String name){
        PanacheQuery<User> rq = User.find("name", name);
        User user = rq.firstResult();
        
        return user == null;
    }
    
    private boolean roleNotExist(RoleValue roleName){
        PanacheQuery<Role> rq = Role.find("role", roleName.name());
        Role role = rq.firstResult();
        
        return role == null;
    }
    
    private void createRole(RoleValue roleName){
        Role role = new Role();
        
        role.role = roleName.name();
        role.updatedBy = "system";
        
        role.persist();
    }
    
    
}
