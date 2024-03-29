/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.acte.naissance.domain;

import io.urbis.common.domain.BaseEntity;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
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
    
    @Column(name = "nom_complet_texte")
    public String nomCompletTexte;
        
    @Lob
    @Column(name = "extrait_texte")
    public String extraitTexte;
    
    @Lob
    @Column(name = "copie_texte")
    public String copieTexte;
    
    @Lob
    @Column(name = "mention_mariage_texte")
    public String mentionMarigeTexte;
    
    @Lob
    @Column(name = "mention_dissolution_mariage_texte")
    public String mentionDissolutionMarigeTexte;
    
    @Lob
    @Column(name = "mention_adoption_texte")
    public String mentionAdoptionTexte;
    
    @Lob
    @Column(name = "mention_deces_texte")
    public String mentionDecesTexte;
    
    @Lob
    @Column(name = "mention_legitimation_texte")
    public String mentionLegitimationTexte;
    
    @Lob
    @Column(name = "mention_reconnaissance_texte")
    public String mentionReconnaissanceTexte;
    
    @Lob
    @Column(name = "mention_rectification_texte")
    public String mentionRectificationTexte;
    
    @Lob
    @Column(name = "mention_annulation_texte")
    public String mentionAnnulationTexte;
    
    @Lob
    @Column(name = "copie_mentions_textes")
    public String copieMentionsTextes;
    
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
    
    @Column(name = "copie_numero_acte_texte")
    public String copieNumeroActeTexte;
    
    @Column(name = "titre_texte")
    public String titreTexte;
    
    @Column(name = "copie_titre_texte")
    public String copieTitreTexte;
    
    
    public ActeNaissanceEtat() {
    }

    public ActeNaissanceEtat(ActeNaissance acteNaissance) {
        this.acteNaissance = acteNaissance;
    }
    
    
}
