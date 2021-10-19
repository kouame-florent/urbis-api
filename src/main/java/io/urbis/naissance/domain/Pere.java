/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.naissance.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Embeddable;

/**
 *
 * @author florent
 */
@Embeddable
public class Pere implements Serializable{
    
    public String nom;
    public String prenoms;
    public String nomComplet;
    public String profession;
    public String lieuNaissance;
    public Nationalite nationalite; 
    public LocalDateTime dateNaissance;
    public LocalDateTime dateDeces;
    public String lieuDeces;
    public String localite;
    public TypePiece typePiece;
    public String numeroPiece;
    public LocalDateTime datePiece;
    public String lieuPiece;
    
}
