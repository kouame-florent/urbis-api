/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.acte.deces.domain;

import io.urbis.common.domain.Sexe;
import io.urbis.common.domain.SituationMatrimoniale;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 *
 * @author florent
 */
@Embeddable
public class Defunt implements Serializable{
    
    public String nom;
    public String prenoms;
    public LocalDateTime dateDeces;
    public String profession;
    public String lieuDeces;
    public String domicile;
    
    
    public LocalDate dateNaissance;
    public String lieuNaissance;
    public String localiteNaissance;
         
    @Enumerated(EnumType.STRING)
    public Sexe sexe;
    
    public SituationMatrimoniale situationMatrimoniale; 
     
}
