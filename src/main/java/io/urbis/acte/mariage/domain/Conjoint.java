/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.acte.mariage.domain;

import java.time.LocalDate;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 *
 * @author florent
 */
@Embeddable
public class Conjoint {
    
    @NotBlank
    public String nom;
    @NotBlank
    public String prenoms;
    @NotBlank
    public String profession;
    @NotBlank
    public String lieuNaissance;
    @NotBlank
    public LocalDate dateNaissance;
    @NotBlank
    public String domicile;
    
    @Enumerated(EnumType.STRING)
    @NotNull
    public SituationMatrimoniale situationMatrimoniale;
}
