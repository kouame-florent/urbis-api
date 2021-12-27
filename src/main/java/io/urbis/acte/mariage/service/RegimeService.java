/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.acte.mariage.service;

import io.urbis.acte.mariage.domain.Regime;
import io.urbis.acte.mariage.dto.RegimeDto;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;

/**
 *
 * @author florent
 */
@ApplicationScoped
public class RegimeService {
    public List<RegimeDto> findAllRegimes(){
        EnumSet<Regime> types = EnumSet.allOf(Regime.class);
        return types.stream().map(Regime::mapToDto).collect(Collectors.toList()); 
    }
}
