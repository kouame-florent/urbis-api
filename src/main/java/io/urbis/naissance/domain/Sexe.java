/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.naissance.domain;

/**
 *
 * @author florent
 */
public enum Sexe {
    MASCULIN("M"),
    FEMININ("F");
    
    private String abreviation;
    
    private Sexe(String abreviation){
        this.abreviation = abreviation;
    }
}
