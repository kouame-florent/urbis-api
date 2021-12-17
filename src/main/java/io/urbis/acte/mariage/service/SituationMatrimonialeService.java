/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.acte.mariage.service;

import io.urbis.acte.mariage.domain.SituationMatrimoniale;
import io.urbis.acte.mariage.dto.SituationMatrimonialeDto;
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
    
    public List<SituationMatrimonialeDto> findAllSituation(){
        EnumSet<SituationMatrimoniale> types = EnumSet.allOf(SituationMatrimoniale.class);
        return types.stream().map(SituationMatrimoniale::mapToDto).collect(Collectors.toList()); 
    }
    
}
