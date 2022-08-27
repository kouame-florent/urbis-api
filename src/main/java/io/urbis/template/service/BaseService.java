/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.urbis.template.service;

import io.urbis.common.domain.BaseEntity;
import io.urbis.template.dto.BaseDto;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 *
 * @author florent
 * @param <D>
 * @param <E>
 */
public interface BaseService<D extends BaseDto, E extends BaseEntity> {
    
    public D findById(@NotBlank String id);
    
    public String creer(@NotNull D dto);
    
    public void modifier(@NotBlank String id,@NotNull D dto);
    
    public boolean supprimer(@NotBlank String id);
           
    public List<D> findWithFilters(int offset,int pageSize);
      
    public int count();
       
    public D mapToDto(@NotNull E acte);
    
    
}
