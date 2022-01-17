/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.common.service;


import io.urbis.common.dto.NationaliteDto;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import io.urbis.common.domain.Nationalite; 

/**
 *
 * @author florent
 */ 
@ApplicationScoped
public class NationaliteService {
     public List<NationaliteDto> findAllNationalite(){
        EnumSet<Nationalite> types = EnumSet.allOf(Nationalite.class);
        return types.stream().map(Nationalite::mapToDto).collect(Collectors.toList());
    }
    
}
