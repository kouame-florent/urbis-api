/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.urbis.security.service;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Sort;
import io.urbis.security.domain.Role;
import io.urbis.security.domain.User;
import io.urbis.security.dto.UserDto;
import java.util.List;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 *
 * @author florent
 */
@ApplicationScoped
@Transactional
public class UserService {
    
    public UserDto findById(@NotBlank String id){
        return User.findByIdOptional(id)
                .map(p -> (User)p)
                .map(this::mapToDto)
                .orElseThrow(() -> new WebApplicationException("user not found",Response.status(Response.Status.NOT_FOUND).build()));
       
    }
    
    public String creer(@NotNull UserDto dto) {
      User user = new User();
      user.name = dto.getName();
      user.password = dto.getPassword();
      
      for(String roleStr : dto.getRoles()){
         PanacheQuery<Role> rq = Role.find("name", roleStr);
         Role r = rq.firstResult();
         if(r == null){
             throw new EntityNotFoundException("Role not found");
         }
         user.roles.add(r);
      }
      
      user.persist();
      
      return user.id;
        
    }
    
    
    
    public void modifier(@NotBlank String id,@NotNull UserDto dto){
          
    }
   
    public boolean supprimer(@NotBlank String id){
        return User.deleteById(id);
    }
    
    public List<UserDto> findWithFilters(int offset,int pageSize){
         PanacheQuery<User> query = User.findAll();
            
        PanacheQuery<User> rq =  query.range(offset, offset + (pageSize-1));
        
        return rq.stream().map(this::mapToDto).collect(Collectors.toList());
      
    }
    
   
    public int count(){
        return (int)User.count();
    }
    
   
    
    
    public UserDto mapToDto(@NotNull User entity){
        UserDto dto = new UserDto();
        
        dto.setId(entity.id);
        dto.setName(entity.name);
        List<String> roles = entity.roles.stream().map(r -> r.value).collect(Collectors.toList());
        dto.setRoles(roles);
        String roleView = entity.roles.stream().map(r -> r.value).collect(Collectors.joining(" "));
        dto.setRolesView(roleView);
      
        
       
        return dto;
    }
}
