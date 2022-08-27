/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.urbis.demande.service;

import io.urbis.demande.domain.StatutDemande;
import io.urbis.demande.dto.StatutDemandeDto;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;

/**
 *
 * @author florent
 */
@ApplicationScoped
public class StatutDemandeService {
    
    public List<StatutDemandeDto> findAll(){
        EnumSet<StatutDemande> types = EnumSet.allOf(StatutDemande.class);
        return types.stream().map(StatutDemande::mapToDto).collect(Collectors.toList()); 
    }
}
