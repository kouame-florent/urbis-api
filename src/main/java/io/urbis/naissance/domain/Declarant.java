/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.naissance.domain;

import java.time.LocalDateTime;
import javax.persistence.Embeddable;

/**
 *
 * @author florent
 */
@Embeddable
public class Declarant {
    
    public String lien;
    public String nom;
    public String prenoms;
    public String profession;
    public String lieuNaissance;
    public Nationalite nationalite;
    public LocalDateTime dateNaissance;
    public String localite;
    public TypePiece typePiece;
    public String numeroPiece;
    public LocalDateTime datePiece;
    public String lieuPiece;
}