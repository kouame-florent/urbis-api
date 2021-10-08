/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.naissance.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 *
 * @author florent
 */
@Embeddable
public class Enfant implements Serializable{
    
    public LocalDateTime dateNaissance;
    public String lieuNaissance;
    public Sexe sexe;
    public String localite;
    public String nom;
    public String prenoms;
    
    @Enumerated(EnumType.STRING)
    public Nationalite nationalite;
}
