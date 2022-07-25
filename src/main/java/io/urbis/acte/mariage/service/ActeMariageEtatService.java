/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.acte.mariage.service;

import io.urbis.acte.mariage.domain.ActeMariage;
import io.urbis.acte.mariage.domain.ActeMariageEtat;
import io.urbis.common.util.DateTimeUtils;
import io.urbis.param.service.LocaliteService;
import io.urbis.security.service.AuthenticationContext;
import java.time.format.DateTimeFormatter;
import java.util.Formatter;
import java.util.Locale;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

/**
 *
 * @author florent
 */
@ApplicationScoped
@Transactional
public class ActeMariageEtatService {
    
    @Inject
    AuthenticationContext authenticationContext;
    
    @Inject
    LocaliteService localiteService;
    
    
    public void creer(String acteID){
        
        ActeMariageEtat etat = new ActeMariageEtat();
        ActeMariage acte = ActeMariage.findById(acteID);
        etat.acteMariage = acte;
        etat.updatedBy = authenticationContext.userLogin();  
        
        etat.nomsMariesTexte = nomsMaries(acte);
        etat.numeroActeTexte = numeroActeTexte(acte);
        etat.titreTexte = titreTexte(acte);
        etat.extraitTexte = extraitTexte(acte);
        etat.copieNumeroActeTexte = copieNumeroActeTexte(acte);
        etat.copieTitreTexte = copieTitretexte(acte);
        etat.copieTexte = copieIntegraleTexte(acte);
   
    }
    
    private String nomsMaries(ActeMariage acte){
        StringBuilder sb = new StringBuilder();
        sb.append(acte.epoux.conjoint.nom.toUpperCase(Locale.FRENCH));
        sb.append(" ");
        sb.append(acte.epoux.conjoint.prenoms);
        sb.append("\n");
        sb.append("Et de ");
        sb.append("\n");
        sb.append(acte.epouse.conjoint.nom.toUpperCase(Locale.FRENCH));
        sb.append(" ");
        sb.append(acte.epouse.conjoint.prenoms);
        
        return sb.toString();
        
    }
    
    public String numeroActeTexte(ActeMariage acte){
        StringBuilder sb = new StringBuilder();
        sb.append("N° ");
        sb.append(acte.numero);
        sb.append(" ");
        sb.append("DU ");
        sb.append(acte.dateMariage.format(DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.FRENCH)));
        sb.append(" ");
        sb.append("DU REGISTRE");
        
        return sb.toString();
    
    }
    
    public String titreTexte(ActeMariage acte){
        StringBuilder sb = new StringBuilder();
        sb.append("Pour l'année ");
        sb.append(acte.registre.reference.annee);
        
        return sb.toString();
    }
    
    public String extraitTexte(ActeMariage acte){
        StringBuilder sb = new StringBuilder();
        sb.append("Le ");
        sb.append(DateTimeUtils.dateSpelling(acte.dateMariage.toLocalDate()));
        sb.append("\n");
        sb.append("Entre ");
        sb.append(acte.epoux.conjoint.nom.toUpperCase(Locale.FRENCH));
        sb.append(" ");
        sb.append(acte.epoux.conjoint.prenoms);
        sb.append("\n");
        sb.append(getEmploi(acte.epoux.conjoint.profession));
        sb.append("\n");
        sb.append("Né le ");
        sb.append(DateTimeUtils.dateSpelling(acte.epoux.conjoint.dateNaissance));
        sb.append("à ");
        sb.append(acte.epoux.conjoint.lieuNaissance);
        sb.append("\n");
        sb.append("Fils de ");
        sb.append(acte.epoux.pere.nom.toUpperCase(Locale.FRENCH));
        sb.append(" ");
        sb.append(acte.epoux.pere.prenoms);
        sb.append("\n");
        sb.append("et de ");
        sb.append(acte.epoux.mere.nom.toUpperCase(Locale.FRENCH));
        sb.append(" ");
        sb.append(acte.epoux.mere.prenoms);
        sb.append("\n");
        sb.append("domicilié à ");
        sb.append(acte.epoux.conjoint.domicile);
        sb.append("\n");
        sb.append("Et ");
        sb.append(acte.epouse.conjoint.nom.toUpperCase(Locale.FRENCH));
        sb.append(" ");
        sb.append(acte.epouse.conjoint.prenoms);
        sb.append("\n");
        sb.append(getEmploi(acte.epouse.conjoint.profession));
        sb.append("\n");
        sb.append("Né le ");
        sb.append(DateTimeUtils.dateSpelling(acte.epouse.conjoint.dateNaissance));
        sb.append("à ");
        sb.append(acte.epouse.conjoint.lieuNaissance);
        sb.append("Fille de ");
        sb.append(acte.epouse.pere.nom.toUpperCase(Locale.FRENCH));
        sb.append(" ");
        sb.append(acte.epouse.pere.prenoms);
        sb.append("\n");
        sb.append("et de ");
        sb.append(acte.epouse.mere.nom.toUpperCase(Locale.FRENCH));
        sb.append(" ");
        sb.append(acte.epouse.mere.prenoms);
        sb.append("\n");
        sb.append("domicilié à ");
        sb.append(acte.epouse.conjoint.domicile);
        
        return sb.toString();
    }
    
    private String getEmploi(String emploi){
        if(emploi == null || emploi.isBlank()){
            return "Sans-emploi";
        }
        return emploi;
    }
    
    
    public String copieNumeroActeTexte(ActeMariage acte){
        StringBuilder sb = new StringBuilder();
        sb.append("Acte N° ");
        sb.append(acte.numero);
        sb.append(" ");
        sb.append("DU ");
        sb.append(acte.dateMariage.format(DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.FRANCE)));
       
        return sb.toString();
    
    }
    
    
    public String copieTitretexte(ActeMariage acte){
        StringBuilder sb = new StringBuilder();
        sb.append("Pour l'année ");
        sb.append(acte.registre.reference.annee);
        
        return sb.toString();
    }
    
    public String copieIntegraleTexte(ActeMariage acte){
        /*
        String text = """
        Le %s, à %s, devant
        Nous: %s, Officier d'état civil, %s de la 
        commune, ont comparu publiquement, à la mairie de %s,
        %s, %s, né le %s, 
        à %s, fils de %s, et de %s,
        domicilié à %s, 
        Célibataire.
        
        %s, %s, née le %s, 
        à %s, fille de %s, et de %s,
        domicilié à %s, 
        Célibataire.
                      
        Lesquels ont déclaré sur notre interpellation opter pour le,
        regime %s,  et l'un après l'autre vouloir se prendre 
        pour époux et nous avons prononcé, au nom de la loi, qu'ils 
        sont unis pour le mariage, en présence de: %s,
        %s, domicilié(e) à %s et %s, %s,
        domicilié(e) à %s.
                      
        Lecture fait, et invité à lire l'acte.
        Nous avons signé avec les époux et les témoins.
        
        """.formatted(DateTimeUtils.dateSpelling(acte.dateMariage.toLocalDate()),
                      DateTimeUtils.hourSpelling(acte.dateMariage),
                      acte.officierEtatCivil.nom.toUpperCase(Locale.FRENCH) + " " + acte.officierEtatCivil.prenoms,
                      acte.officierEtatCivil.titre,
                      localiteService.findActive().getLibelle(),
                      acte.epoux.conjoint.nom.toUpperCase(Locale.FRENCH) + " " + acte.epoux.conjoint.prenoms,
                      getEmploi(acte.epoux.conjoint.profession),
                      DateTimeUtils.dateSpelling(acte.epoux.conjoint.dateNaissance),
                      acte.epoux.conjoint.lieuNaissance,
                      acte.epoux.pere.nom.toUpperCase(Locale.FRENCH) + " " + acte.epoux.pere.prenoms,
                      acte.epoux.mere.nom.toUpperCase(Locale.FRENCH) + " " + acte.epoux.mere.prenoms,
                      acte.epoux.conjoint.domicile,
                      acte.epouse.conjoint.nom.toUpperCase(Locale.FRENCH) + " " + acte.epouse.conjoint.prenoms,
                      getEmploi(acte.epouse.conjoint.profession),
                      DateTimeUtils.dateSpelling(acte.epouse.conjoint.dateNaissance),
                      acte.epouse.conjoint.lieuNaissance,
                      acte.epouse.pere.nom.toUpperCase(Locale.FRENCH) + " " + acte.epouse.pere.prenoms,
                      acte.epouse.mere.nom.toUpperCase(Locale.FRENCH) + " " + acte.epouse.mere.prenoms,
                      acte.epouse.conjoint.domicile,
                      acte.regime.getLibelle(),
                      acte.epoux.temoin.nom.toUpperCase(Locale.FRENCH) + " " + acte.epoux.temoin.prenoms,
                      getEmploi(acte.epoux.temoin.profession),
                      acte.epoux.temoin.domicile,
                      acte.epouse.temoin.nom.toUpperCase(Locale.FRENCH) + " " + acte.epouse.temoin.prenoms,
                      getEmploi(acte.epouse.temoin.profession),
                      acte.epouse.temoin.domicile);
        
        
        */
        
        String text = "Le %s, à %s, devant\n"
        + "Nous: %s, Officier d'état civil, %s de la\n" 
        + "commune, ont comparu publiquement, à la mairie de %s,\n"
        + "%s, %s, né le %s,"
        + "à %s, fils de %s, et de %s,\n"
        + "domicilié à %s,\n" 
        + "Célibataire.\n"
        + "%s, %s, née le %s\n"
        + "à %s, fille de %s, et de %s,\n"
        + "domicilié à %s,\n"
        + "Célibataire.\n"
        + "Lesquels ont déclaré sur notre interpellation opter pour le,\n"
        + "regime %s,  et l'un après l'autre vouloir se prendre\n"
        + "pour époux et nous avons prononcé, au nom de la loi, qu'ils\n"
        + "sont unis pour le mariage, en présence de: %s,\n"
        + "%s, domicilié(e) à %s et %s, %s,\n"
        + "domicilié(e) à %s.\n"
        + "Lecture fait, et invité à lire l'acte.\n"
        + "Nous avons signé avec les époux et les témoins.\n";
        
        
        Formatter formatter = new Formatter(Locale.FRENCH);
        return formatter.format(text, 
                DateTimeUtils.dateSpelling(acte.dateMariage.toLocalDate()),
                DateTimeUtils.hourSpelling(acte.dateMariage),
                acte.officierEtatCivil.nom.toUpperCase(Locale.FRENCH) + " " + acte.officierEtatCivil.prenoms,
                acte.officierEtatCivil.titre,
                localiteService.findActive().getLibelle(),
                acte.epoux.conjoint.nom.toUpperCase(Locale.FRENCH) + " " + acte.epoux.conjoint.prenoms,
                getEmploi(acte.epoux.conjoint.profession),
                DateTimeUtils.dateSpelling(acte.epoux.conjoint.dateNaissance),
                acte.epoux.conjoint.lieuNaissance,
                acte.epoux.pere.nom.toUpperCase(Locale.FRENCH) + " " + acte.epoux.pere.prenoms,
                acte.epoux.mere.nom.toUpperCase(Locale.FRENCH) + " " + acte.epoux.mere.prenoms,
                acte.epoux.conjoint.domicile,
                acte.epouse.conjoint.nom.toUpperCase(Locale.FRENCH) + " " + acte.epouse.conjoint.prenoms,
                getEmploi(acte.epouse.conjoint.profession),
                DateTimeUtils.dateSpelling(acte.epouse.conjoint.dateNaissance),
                acte.epouse.conjoint.lieuNaissance,
                acte.epouse.pere.nom.toUpperCase(Locale.FRENCH) + " " + acte.epouse.pere.prenoms,
                acte.epouse.mere.nom.toUpperCase(Locale.FRENCH) + " " + acte.epouse.mere.prenoms,
                acte.epouse.conjoint.domicile,
                acte.regime.getLibelle(),
                acte.epoux.temoin.nom.toUpperCase(Locale.FRENCH) + " " + acte.epoux.temoin.prenoms,
                getEmploi(acte.epoux.temoin.profession),
                acte.epoux.temoin.domicile,
                acte.epouse.temoin.nom.toUpperCase(Locale.FRENCH) + " " + acte.epouse.temoin.prenoms,
                getEmploi(acte.epouse.temoin.profession),
                acte.epouse.temoin.domicile).toString();
        
        
    }
    
  
   
}
