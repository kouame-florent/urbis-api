/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.registre.service;

import io.urbis.registre.domain.TypeRegistre;
import io.urbis.registre.dto.TypeRegistreDto;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;

/**
 *
 * @author florent
 */
@ApplicationScoped
public class TypeRegistreService {
    
    public List<TypeRegistreDto> findAllTypeRegistre(){
        EnumSet<TypeRegistre> types = EnumSet.allOf(TypeRegistre.class);
        return types.stream().map(TypeRegistreService::mapToDto).collect(Collectors.toList());
    }
    
    public static TypeRegistreDto mapToDto(TypeRegistre type){
        return new TypeRegistreDto(type.name(), type.getLibelle());
    }
    
}
