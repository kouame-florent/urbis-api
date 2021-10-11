/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.registre.service;

import io.urbis.registre.domain.OfficierEtatCivil;
import io.urbis.registre.dto.OfficierEtatCivilDto;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.enterprise.context.ApplicationScoped;

/**
 *
 * @author florent
 */
@ApplicationScoped
public class OfficierService {
    
    public List<OfficierEtatCivilDto> findAll(){
        Stream<OfficierEtatCivil> officiers = OfficierEtatCivil.findAll().stream();
        return officiers.map(OfficierService::mapToDto).collect(Collectors.toList());      
        
    }
    
    public static OfficierEtatCivilDto mapToDto(OfficierEtatCivil officier){
        return new OfficierEtatCivilDto(
                officier.id, 
                officier.created,
                officier.updated,
                officier.nom, 
                officier.prenoms,
                officier.qualite, 
                officier.titre, 
                officier.actif,
                officier.maire);
    }
}
