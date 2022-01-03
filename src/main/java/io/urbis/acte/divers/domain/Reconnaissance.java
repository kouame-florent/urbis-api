/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.acte.divers.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Embeddable;

/**
 *
 * @author florent
 */
@Embeddable
public class Reconnaissance implements Serializable{
    
    public LocalDateTime date;
    public String lieu;
    public String natureCirconscription;
    public String nomCirconscription;
}
