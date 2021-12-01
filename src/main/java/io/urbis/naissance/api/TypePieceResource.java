/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.naissance.api;


import io.urbis.naissance.dto.TypePieceDto;
import io.urbis.naissance.service.TypePieceService;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 *
 * @author florent
 */
@Path("/types-piece")
public class TypePieceResource {
    
    @Inject
    TypePieceService typePieceService;
    
    @GET
    public List<TypePieceDto> findAll(){
      return  typePieceService.findAllTypePiece();
    }
}
