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
public enum TypeMentionEnum {
    
    RECONNAISSANCE("Reconnaissance"),
    ADOPTION("Adoption"),
    LEGITIMATION("Légitimation"),
    MARIAGE("Mariage"),
    DECES("Decès"),
    DISSOLUTION_MARIAGE("Dissolution de mariage"),
    RECTIFICATION("Rectification");
    
    private final String libelle;
    
    private TypeMentionEnum(String libelle){
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
}