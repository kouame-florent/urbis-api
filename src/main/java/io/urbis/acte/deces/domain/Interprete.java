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
public class Interprete implements Serializable{
    
    public String nom;
    public String prenoms;
    public String profession;
    public LocalDate dateNaissance;
    public String domicile;
    public String langue;
} 
