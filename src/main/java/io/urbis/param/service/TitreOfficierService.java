/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.param.service;

import io.urbis.param.domain.TitreOfficier;
import io.urbis.param.dto.TitreOfficierDto;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;

/**
 *
 * @author florent
 */
@ApplicationScoped
public class TitreOfficierService {
    
    public List<TitreOfficierDto> findAll(){
        EnumSet<TitreOfficier> types = EnumSet.allOf(TitreOfficier.class);
        return types.stream().map(TitreOfficierService::mapToDto).collect(Collectors.toList());
    }
    
    public static TitreOfficierDto mapToDto(TitreOfficier titre){
        return new TitreOfficierDto(titre.name(), titre.getLibelle());
    }
}
