/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.acte.mariage.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 *
 * @author florent
 */
@Embeddable
public class Temoin implements Serializable{
    
    @NotBlank
    public String nom;
    
    @NotBlank
    public String prenoms;
    
    @NotBlank
    public String profession;
    
    @NotBlank
    public String domicile;
    
    @NotNull
    public LocalDate dateNaissance;
}
