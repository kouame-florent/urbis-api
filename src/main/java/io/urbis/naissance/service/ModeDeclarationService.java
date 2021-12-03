/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.naissance.service;



import io.urbis.naissance.domain.ModeDeclaration;
import io.urbis.naissance.dto.ModeDeclarationDto;
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
    
    public List<ModeDeclarationDto> findAllModeDeclaration(){
        EnumSet<ModeDeclaration> types = EnumSet.allOf(ModeDeclaration.class);
        return types.stream().map(ModeDeclaration::mapToDto).collect(Collectors.toList()); 
    }
    
}
