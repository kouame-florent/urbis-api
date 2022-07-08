/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.acte.naissance.backing;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author florent
 */
@RequestScoped
public class FilterData implements Serializable{
    
    private String nom;
    private String prenoms;
    private LocalDateTime dateNaissance;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenoms() {
        return prenoms;
    }

    public void setPrenoms(String prenoms) {
        this.prenoms = prenoms;
    }

    

    public LocalDateTime getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDateTime dateNaissance) {
        this.dateNaissance = dateNaissance;
    }
    
    
    
}
