/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.acte.naissance.service;



import io.urbis.acte.naissance.domain.ModeDeclaration;
import io.urbis.acte.naissance.dto.ModeDeclarationDto;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;


/**
 *
 * @author florent
 */
@ApplicationScoped
public class ModeDeclarationService {
    
    public List<ModeDeclarationDto> findAll(){
        EnumSet<ModeDeclaration> types = EnumSet.allOf(ModeDeclaration.class);
        return types.stream().map(ModeDeclaration::mapToDto).collect(Collectors.toList()); 
    }
    
}
