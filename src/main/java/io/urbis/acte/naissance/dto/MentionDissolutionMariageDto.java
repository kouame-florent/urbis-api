/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.acte.naissance.dto;

import java.time.LocalDate;

/**
 *
 * @author florent
 */

public class MentionDissolutionMariageDto extends BaseMentionDto{
    private LocalDate dateJugement;

    public LocalDate getDateJugement() {
        return dateJugement;
    }

    public void setDateJugement(LocalDate dateJugement) {
        this.dateJugement = dateJugement;
    }
    
    
}
