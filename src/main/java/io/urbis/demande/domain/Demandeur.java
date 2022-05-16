/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.demande.domain;

import io.urbis.common.domain.TypePiece;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 *
 * @author florent
 */
@Embeddable
public class Demandeur {
    
    public String nom;
    
    public String prenoms;
    
    public String numero;
    
    public String email;
    
    @Column(name = "type_naissance")
    @Enumerated(EnumType.STRING)
    public TypePiece typePiece;
    
    public String numeroPiece;
    
    public String qualite;
}
