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

public class MentionMariageDto extends BaseMentionDto{
    
    
    private String lieu;  
    private LocalDate date;
    private String conjointNom;
    private String conjointPrenoms;
    private String conjointDomicile;
    private String conjointProfession;

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getConjointNom() {
        return conjointNom;
    }

    public void setConjointNom(String conjointNom) {
        this.conjointNom = conjointNom;
    }

    public String getConjointPrenoms() {
        return conjointPrenoms;
    }

    public void setConjointPrenoms(String conjointPrenoms) {
        this.conjointPrenoms = conjointPrenoms;
    }

    public String getConjointDomicile() {
        return conjointDomicile;
    }

    public void setConjointDomicile(String conjointDomicile) {
        this.conjointDomicile = conjointDomicile;
    }

    public String getConjointProfession() {
        return conjointProfession;
    }

    public void setConjointProfession(String conjointProfession) {
        this.conjointProfession = conjointProfession;
    }
    
    
}
