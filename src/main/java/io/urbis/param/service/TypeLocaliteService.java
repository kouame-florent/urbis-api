/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.param.service;

import io.urbis.param.domain.TypeLocalite;
import io.urbis.param.dto.TypeLocaliteDto;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;

/**
 *
 * @author florent
 */
@ApplicationScoped
public class TypeLocaliteService {
    
    public List<TypeLocaliteDto> findAllTypeLocalite(){
        EnumSet<TypeLocalite> types = EnumSet.allOf(TypeLocalite.class);
        return types.stream().map(TypeLocaliteService::mapToDto).collect(Collectors.toList());
    }
    
    public static TypeLocaliteDto mapToDto(TypeLocalite type){
        return new TypeLocaliteDto(type.name(), type.getLibelle());
    }
}
