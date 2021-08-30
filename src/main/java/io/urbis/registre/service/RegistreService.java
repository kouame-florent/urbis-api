/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.registre.service;

import io.urbis.domain.Centre;
import io.urbis.domain.Localite;
import io.urbis.domain.Registre;
import io.urbis.domain.StatutRegistre;
import io.urbis.domain.Tribunal;
import io.urbis.domain.TypeRegistre;
import io.urbis.dto.RegistreDto;
import java.time.LocalDateTime;
import javax.enterprise.context.ApplicationScoped;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 *
 * @author florent
 */
@ApplicationScoped
public class RegistreService {
    
    public Registre creerRegistre(@NotNull RegistreDto registreDto){
        var typeRegistre = getTypeRgistre(registreDto.getTypeRegistre());
        Localite localite = Localite.findById(registreDto.getLocalite());
        Centre centre = Centre.findById(registreDto.getCentreID());
        Tribunal tribunal = Tribunal.findById(registreDto.getTribunalID());
        var statut = getStatutRegistre(registreDto.getStatut());
        
        Registre registre = new Registre(typeRegistre, registreDto.getLibelle(), localite, centre, registreDto.getAnnee(), 
                registreDto.getNumero(),tribunal, registreDto.getOfficierEtatCivilID(),registreDto.getNumeroPremierActe(),
                registreDto.getNumeroDernierActe(), registreDto.getNombreDeFeuillets(),statut);
        
        registre.persist();
        
        return registre;
    }
    
    public void modifierRegistre(@NotBlank String registreID,@NotNull RegistreDto registreDto){
        Registre registre = Registre.findById(registreID);
        
        var typeRegistre = getTypeRgistre(registreDto.getTypeRegistre());
        Localite localite = Localite.findById(registreDto.getLocalite());
        Centre centre = Centre.findById(registreDto.getCentreID());
        Tribunal tribunal = Tribunal.findById(registreDto.getTribunalID());
        var statut = getStatutRegistre(registreDto.getStatut());
        
        registre.typeRegistre = typeRegistre;
        registre.libelle = registreDto.getLibelle();
        registre.localite = localite;
        registre.centre = centre;
        registre.annee = registreDto.getAnnee();
        registre.numero = registreDto.getNumero();
        registre.tribunal = tribunal;
        registre.officierEtatCivilID = registreDto.getOfficierEtatCivilID();
        registre.numeroPremierActe = registreDto.getNumeroPremierActe();
        registre.numeroDernierActe = registreDto.getNumeroDernierActe();
        registre.nombreDeFeuillets = registreDto.getNombreDeFeuillets();
        registre.statut = statut;
        
        registre.updated = LocalDateTime.now();
        
        registre.persist();
        
    }
    
    public void validerRegistre(@NotBlank String registreID){
        Registre registre = Registre.findById(registreID);
        registre.statut = StatutRegistre.VALIDE;
        
    }
    
    public void annulerRegistre(@NotBlank String registreID){
        Registre registre = Registre.findById(registreID);
        registre.statut = StatutRegistre.ANNULE;
    }
    
    public void cloturerRegistre(@NotBlank String registreID){
        Registre registre = Registre.findById(registreID);
        registre.statut = StatutRegistre.CLOTURE;
    }
    
    public void consulterRegistre(){}
    
    public void deplacerRegistre(){}
      
    public void mapToDto(){
    
    }
    
    public TypeRegistre getTypeRgistre(String value){
        switch(value){
            case "NAISSANCE":
                return TypeRegistre.NAISSANCE;
            case "MARIAGE":
                return TypeRegistre.MARIAGE;
            case "DECES":
                return TypeRegistre.DECES;
            case "DIVERS":
                return TypeRegistre.DIVERS;
            case "SPECIAL_NAISSANCE":
                return TypeRegistre.SPECIAL_NAISSANCE;
            default:
                throw new IllegalArgumentException(value);
        }
        
        
    }
    
    public StatutRegistre getStatutRegistre(String value){
        switch(value){
            case "NON_VALIDE":
                return StatutRegistre.NON_VALIDE;
            case "VALIDE":
                return StatutRegistre.VALIDE;
            case "CLOTURE":
                return StatutRegistre.CLOTURE;
            case "ANNULE":
                return StatutRegistre.ANNULE;
           
            default:
                throw new IllegalArgumentException(value);
        }
        
        
    }
}


