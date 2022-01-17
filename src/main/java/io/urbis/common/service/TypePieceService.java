/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.common.service;


import io.urbis.common.dto.TypePieceDto;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import io.urbis.common.domain.TypePiece;

/**
 *
 * @author florent
 */
@ApplicationScoped
public class TypePieceService {
    public List<TypePieceDto> findAllTypePiece(){
        EnumSet<TypePiece> types = EnumSet.allOf(TypePiece.class);
        return types.stream().map(TypePiece::mapToDto).collect(Collectors.toList()); 
    }
    
}
