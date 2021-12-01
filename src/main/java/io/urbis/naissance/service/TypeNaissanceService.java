/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.naissance.service;

import io.urbis.naissance.dto.TypeNaissanceDto;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import io.urbis.naissance.domain.TypeNaissance;

/**
 *
 * @author florent
 */
@ApplicationScoped
public class TypeNaissanceService {
    
    public List<TypeNaissanceDto> findAllTypeNaissance(){
        EnumSet<TypeNaissance> types = EnumSet.allOf(TypeNaissance.class);
        return types.stream().map(TypeNaissance::mapToDto).collect(Collectors.toList());
    }
    
   
}
