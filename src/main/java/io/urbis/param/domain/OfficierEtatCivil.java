/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.param.domain;

import io.urbis.common.domain.BaseEntity;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author florent
 */
@Table(name = "officier_etat_civil")
@Entity
public class OfficierEtatCivil extends  BaseEntity{
    
    public String nom;
    public String prenoms;
   // public String qualite;
    public TitreOfficier titre;
    public boolean actif;
    public String profession;
    
}
