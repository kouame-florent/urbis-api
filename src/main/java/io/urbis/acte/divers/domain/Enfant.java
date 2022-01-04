/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.acte.divers.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;


/**
 *
 * @author florent
 */
@Embeddable
public class Enfant implements Serializable{
    
    public int numeroActe;
    public LocalDate dateActe;
    
    public String nom;
    public String prenoms;
    public LocalDate dateNaissance;
    public String lieuNaissance;
    @Enumerated(EnumType.STRING)
    public Sexe sexe;
    
}
