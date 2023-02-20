/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.registre.dto;

import java.time.LocalDate;

/**
 *
 * @author florent
 */

public class RegistrePatchDto {
    
    private String statut;
    private LocalDate dataOuverture;
    private String motifAnnulation;

    public RegistrePatchDto() {
    }

    public RegistrePatchDto(String statut, LocalDate dataOuverture, String motifAnnulation) {
        this.statut = statut;
        this.dataOuverture = dataOuverture;
        this.motifAnnulation = motifAnnulation;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public LocalDate getDataOuverture() {
        return dataOuverture;
    }

    public void setDataOuverture(LocalDate dataOuverture) {
        this.dataOuverture = dataOuverture;
    }

    public String getMotifAnnulation() {
        return motifAnnulation;
    }

    public void setMotifAnnulation(String motifAnnulation) {
        this.motifAnnulation = motifAnnulation;
    }
    
    
}
