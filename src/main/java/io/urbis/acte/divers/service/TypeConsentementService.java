/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.acte.divers.service;

import io.urbis.acte.divers.domain.TypeConsentement;
import io.urbis.acte.divers.dto.TypeConsentementDto;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;

/**
 *
 * @author florent
 */
@ApplicationScoped
public class TypeConsentementService {
    
    public List<TypeConsentementDto> findAll(){
        EnumSet<TypeConsentement> types = EnumSet.allOf(TypeConsentement.class);
        return types.stream().map(TypeConsentement::mapToDto).collect(Collectors.toList()); 
    }
}
