/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.acte.naissance.service;

import io.urbis.acte.naissance.domain.LienDeclarant;
import io.urbis.acte.naissance.dto.LienDeclarantDto;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;

/**
 *
 * @author florent
 */
@ApplicationScoped
public class LienDeclarantService {
    
    public List<LienDeclarantDto> findAll(){ 
        EnumSet<LienDeclarant> liens = EnumSet.allOf(LienDeclarant.class);
        return liens.stream().map(LienDeclarant::mapToDto).collect(Collectors.toList());
    }
}
