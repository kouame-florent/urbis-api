/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.acte.mariage.domain;

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
    public String profession;
    public String domicile;
    public boolean decede;
    public LocalDate dateDeces;
    public String lieuDeces;
}
