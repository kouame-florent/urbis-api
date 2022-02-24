/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.acte.naissance.domain;

import io.urbis.common.domain.BaseEntity;
import java.sql.Clob;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author florent
 */
@Table(name = "acte_naissance_etat")
@Entity
public class ActeNaissanceEtat extends BaseEntity{
    
    @JoinColumn(name = "acte_naissance_id")
    @OneToOne
    public ActeNaissance acteNaissance;
        
    @Lob
    @Column(name = "extrait_texte")
    public Clob extraitTexte;
    
    @Lob
    @Column(name = "copie_texte")
    public Clob copieTexte;
    
    @Column(name = "mention_mariage_date")
    public LocalDate dateMariage;
    
    @Column(name = "mention_mariage_lieu")
    public String lieuMariage;
    
    @Column(name = "mention_mariage_conjoint_nom_complet")
    public String conjointNomComplet;
    
    @Column(name = "mention_dissolution_mariage_decision_date")
    public LocalDate mentionDissolutionMariageDecisionDate;
    
    @Column(name = "mention_deces_date")
    public LocalDate dateDeces;
    
    @Column(name = "mention_deces_lieu")
    public String lieuDeces;
    
    @Column(name = "numero_acte_texte")
    public String numeroActeTexte;
    
    
    public ActeNaissanceEtat() {
    }

    public ActeNaissanceEtat(ActeNaissance acteNaissance) {
        this.acteNaissance = acteNaissance;
    }
    
    
}
