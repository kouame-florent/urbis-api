/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.acte.mariage.domain;

import java.io.Serializable;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

/**
 *
 * @author florent
 */
@Embeddable
public class Epouse implements Serializable{
    
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name="nom",
                           column=@Column(name="epouse_conjoint_nom")),
        @AttributeOverride(name="prenoms",
                           column=@Column(name="epouse_conjoint_prenoms")),
        @AttributeOverride(name="profession",
                           column=@Column(name="epouse_conjoint_profession")),
        @AttributeOverride(name="domicile",
                           column=@Column(name="epouse_conjoint_domicile")),
        @AttributeOverride(name="dateNaissance",
                           column=@Column(name="epouse_conjoint_date_naissance")),
        @AttributeOverride(name="lieuNaissance",
                           column=@Column(name="epouse_conjoint_lieu_naissance")),
        @AttributeOverride(name="situationMatrimoniale",
                           column=@Column(name="epouse_conjoint_situation_matrimoniale")),
    })
    public Conjoint conjoint;
    
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name="nom",
                           column=@Column(name="epouse_pere_nom")),
        @AttributeOverride(name="prenoms",
                           column=@Column(name="epouse_pere_prenoms")),
        @AttributeOverride(name="profession",
                           column=@Column(name="epouse_pere_profession")),
        @AttributeOverride(name="domicile",
                           column=@Column(name="epouse_pere_domicile")),
        @AttributeOverride(name="decede",
                           column=@Column(name="epouse_pere_decede")),
    })
    public Pere pere;
    
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name="nom",
                           column=@Column(name="epouse_mere_nom")),
        @AttributeOverride(name="prenoms",
                           column=@Column(name="epouse_mere_prenoms")),
        @AttributeOverride(name="profession",
                           column=@Column(name="epouse_mere_profession")),
        @AttributeOverride(name="domicile",
                           column=@Column(name="epouse_mere_domicile")),
        @AttributeOverride(name="decede",
                           column=@Column(name="epouse_mere_decede")),
    })
    public Mere mere;
    
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name="nom",
                           column=@Column(name="epouse_temoin_nom")),
        @AttributeOverride(name="prenoms",
                           column=@Column(name="epouse_temoin_prenoms")),
        @AttributeOverride(name="profession",
                           column=@Column(name="epouse_temoin_profession")),
        @AttributeOverride(name="domicile",
                           column=@Column(name="epouse_temoin_domicile")),
        @AttributeOverride(name="age",
                           column=@Column(name="epouse_temoin_age")),
    })
    public Temoin temoin;
}
