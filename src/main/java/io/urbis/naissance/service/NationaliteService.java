/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.naissance.service;

import io.urbis.naissance.domain.Nationalite;
import io.urbis.naissance.dto.NationaliteDto;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;

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
