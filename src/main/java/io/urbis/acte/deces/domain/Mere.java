/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.acte.deces.domain;

import io.urbis.acte.naissance.domain.*;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Embeddable;

/**
 *
 * @author florent
 */
@Embeddable
public class Mere implements Serializable{
    
    public String nom;
    public String prenoms;
    public String nomComplet;
    public boolean decedee;
    public String profession;
    public String lieuNaissance;
    public Nationalite nationalite;
    public LocalDate dateNaissance;
    public LocalDate dateDeces;
    public String lieuDeces;
    public String localite;
    public TypePiece typePiece;
    public String numeroPiece;
    public LocalDate datePiece;
    public String lieuPiece;
    
}
