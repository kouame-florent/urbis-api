/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.urbis.acte.deces.domain;

import io.urbis.common.domain.BaseEntity;
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
@Table(name = "acte_deces_etat")
@Entity
public class ActeDecesEtat extends BaseEntity {
    
    @JoinColumn(name = "acte_deces_id")
    @OneToOne
    public ActeDeces acteDeces;
    
    @Column(name = "nom_complet_texte")
    public String nomCompletTexte;
        
    @Lob
    @Column(name = "extrait_texte")
    public String extraitTexte;
    
    @Lob
    @Column(name = "copie_texte")
    public String copieTexte;
          
    @Column(name = "numero_acte_texte")
    public String numeroActeTexte;
    
    @Column(name = "copie_numero_acte_texte")
    public String copieNumeroActeTexte;
    
    @Column(name = "titre_texte")
    public String titreTexte;
    
    @Column(name = "copie_titre_texte")
    public String copieTitreTexte;
    
    @Lob
    @Column(name = "mention_rectification_texte")
    public String mentionRectificationTexte;
    
    @Lob
    @Column(name = "mention_annulation_texte")
    public String mentionAnnulationTexte;    
     
    @Lob
    @Column(name = "copie_mentions_textes")
    public String copieMentionsTextes;

    public ActeDecesEtat() {
    }

    public ActeDecesEtat(ActeDeces acteDeces) {
        this.acteDeces = acteDeces;
    }
    
    

    
}
