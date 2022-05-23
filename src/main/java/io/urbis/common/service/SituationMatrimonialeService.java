/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.common.service;


import io.urbis.common.domain.SituationMatrimoniale;
import io.urbis.common.dto.SituationMatrimonialeDto;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;

/**
 *
 * @author florent
 */
@ApplicationScoped
public class SituationMatrimonialeService {
    
    public List<SituationMatrimonialeDto> findAll(){
        EnumSet<SituationMatrimoniale> types = EnumSet.allOf(SituationMatrimoniale.class);
        return types.stream().map(SituationMatrimoniale::mapToDto).collect(Collectors.toList()); 
    }
    
}
