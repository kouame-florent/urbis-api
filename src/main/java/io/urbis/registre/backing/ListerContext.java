/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.urbis.registre.backing;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;

/**
 *
 * @author florent
 */
@SessionScoped
public class ListerContext implements Serializable{
    
    private String selectedType;
    private String annee;
    private String numero;
    
    //empty string is the sentinel value
    private String lastNavigationURL = "";
    

    public String getSelectedType() {
        return selectedType;
    }

    public void setSelectedType(String selectedType) { 
        this.selectedType = selectedType;
    }

    public String getAnnee() {
        return annee;
    }

    public void setAnnee(String annee) {
        this.annee = annee;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

   

    public String getLastNavigationURL() {
        return lastNavigationURL;
    }

    public void setLastNavigationURL(String lastNavigationURL) {
        this.lastNavigationURL = lastNavigationURL;
    }

   
    
    
    
}
