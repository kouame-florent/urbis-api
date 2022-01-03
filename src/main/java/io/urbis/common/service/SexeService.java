/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.common.service;

import io.urbis.common.dto.SexeDto;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import io.urbis.common.domain.Sexe;

/**
 *
 * @author florent
 */
@ApplicationScoped
public class SexeService {
    public List<SexeDto> findAllSexe(){
        EnumSet<Sexe> types = EnumSet.allOf(Sexe.class);
        return types.stream().map(Sexe::mapToDto).collect(Collectors.toList()); 
    }
    
}
