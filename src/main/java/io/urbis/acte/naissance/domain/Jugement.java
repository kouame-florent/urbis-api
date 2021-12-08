/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.acte.naissance.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Embeddable;

/**
 *
 * @author florent
 */
@Embeddable
public class Jugement implements Serializable{
    
    public LocalDate date;
    public String numero;
    public String tribunal;
    
}
