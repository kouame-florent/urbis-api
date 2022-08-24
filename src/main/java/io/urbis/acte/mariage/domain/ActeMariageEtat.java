/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.acte.mariage.domain;

import io.urbis.acte.naissance.domain.ActeNaissance;
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
@Table(name = "acte_mariage_etat")
@Entity
public class ActeMariageEtat extends BaseEntity{
    
    @JoinColumn(name = "acte_mariage_id")
    @OneToOne
    public ActeMariage acteMariage;
    
    @Column(name = "noms_maries_texte")
    public String nomsMariesTexte;
        
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
    @Column(name = "mention_divorce_texte")
    public String mentionDivorceTexte;
    
    @Lob
    @Column(name = "mention_modification_regime_biens_texte")
    public String mentionModifRegimeBiensTexte;
    
    @Lob
    @Column(name = "mention_ordonnance_retranscription_texte")
    public String mentionOrdonRetranscriptionTexte;
    
    @Lob
    @Column(name = "mention_rectification_texte")
    public String mentionRectificationTexte;
    
    @Lob
    @Column(name = "mention_annulation_texte")
    public String mentionAnnulationTexte;
    
    @Lob
    @Column(name = "copie_mentions_textes")
    public String copieMentionsTextes;

    public ActeMariageEtat() {
    }

    public ActeMariageEtat(ActeMariage acteMariage) {
        this.acteMariage = acteMariage;
    }

    
    
}
