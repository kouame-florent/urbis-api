/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.urbis.security.domain;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.runtime.StartupEvent;
import java.util.EnumSet;
import javax.enterprise.event.Observes;
import javax.inject.Singleton;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import io.quarkus.elytron.security.common.BcryptUtil;

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
       EnumSet<RoleName> roleNames = EnumSet.allOf(RoleName.class);
       roleNames.stream().filter(this::roleNotExist).forEach(this::createRole);
    }
    
   
    public void loadAdmin(){
        if(userNotExist("admin")){
            User user = new User();
            user.name = "admin";
            user.password = BcryptUtil.bcryptHash("admin");
            user.updatedBy = "system";
            
            PanacheQuery<Role> rq = Role.find("role", RoleName.ADMIN.name());
            Role role = rq.firstResult();
            if(role != null){
                 user.roles.add(role);
            }else{
                throw new EntityNotFoundException("Role ADMIN not found");
            }
            
            user.persist();
       }
    }
    
    
    private boolean userNotExist(String name){
        PanacheQuery<User> rq = User.find("name", name);
        User user = rq.firstResult();
        
        return user == null;
    }
    
    private boolean roleNotExist(RoleName roleName){
        PanacheQuery<Role> rq = Role.find("role", roleName.name());
        Role role = rq.firstResult();
        
        return role == null;
    }
    
    private void createRole(RoleName roleName){
        Role role = new Role();
        
        role.role = roleName.name();
        role.updatedBy = "system";
        
        role.persist();
    }
    
    
}
