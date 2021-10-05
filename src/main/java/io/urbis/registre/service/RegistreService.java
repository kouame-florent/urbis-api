/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.registre.service;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import io.urbis.domain.Centre;
import io.urbis.domain.Localite;
import io.urbis.domain.OfficierEtatCivil;
import io.urbis.domain.Reference;
import io.urbis.domain.Registre;
import io.urbis.domain.StatutRegistre;
import io.urbis.domain.Tribunal;
import io.urbis.domain.TypeRegistre;
import io.urbis.dto.RegistreDto;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.jboss.logging.Logger;

/**
 *
 * @author florent
 */
@ApplicationScoped
public class RegistreService {
    
    @Inject
    Logger log;
    
    @Inject
    EntityManager em;
    
    private final int PAGE_SIZE = 25;
    
    public RegistreDto creerRegistre(@NotNull RegistreDto registreDto){
        
        var typeRegistre = TypeRegistre.fromString(registreDto.getTypeRegistre());
        Localite localite = Localite.findById(registreDto.getLocaliteID());
        Centre centre = Centre.findById(registreDto.getCentreID());
        Tribunal tribunal = Tribunal.findById(registreDto.getTribunalID());
        OfficierEtatCivil officier = OfficierEtatCivil.findById(registreDto.getOfficierEtatCivilID());
        //var statut = getStatutRegistre(registreDto.getStatut());
        
        Reference reference = new Reference(localite, centre, registreDto.getAnnee(),
                registreDto.getNumero());
        
        log.infof("-- TYPE REGISTRE: %s", typeRegistre);
        log.infof("-- LOCALITE: %s", localite);
        log.infof("-- CENTRE: %s", centre);
        log.infof("-- TRIBUNAL: %s", tribunal);
        log.infof("-- LIBELLE REGISTRE: %s", registreDto.getLibelle());
        log.infof("-- NUM DERNIER ACTE: %s", registreDto.getNumeroDernierActe());
        log.infof("-- NOMBRE FEUILLETS: %s", registreDto.getNombreDeFeuillets());
        
        Registre registre = new Registre(typeRegistre, registreDto.getLibelle(), reference,
                tribunal, officier, registreDto.getNumeroPremierActe(),
                registreDto.getNumeroDernierActe(), 
                registreDto.getNombreDeFeuillets(),
                 StatutRegistre.PROJET);
        
        registre.persist();
       
        return mapToDto(registre);
    }
    
    public void modifierRegistre(@NotBlank String registreID,@NotNull RegistreDto registreDto){
        Registre registre = Registre.findById(registreID);
        
        var typeRegistre = TypeRegistre.fromString(registreDto.getTypeRegistre());
        Localite localite = Localite.findById(registreDto.getLocalite());
        Centre centre = Centre.findById(registreDto.getCentreID());
        Tribunal tribunal = Tribunal.findById(registreDto.getTribunalID());
        OfficierEtatCivil officier = OfficierEtatCivil.findById(registreDto.getOfficierEtatCivilID());
        var statut = getStatutRegistre(registreDto.getStatut());
        
        Reference reference = new Reference(localite, centre, registreDto.getAnnee(),
                registreDto.getNumero());
        
        registre.typeRegistre = typeRegistre;
        registre.libelle = registreDto.getLibelle();
        registre.reference = reference;
        registre.tribunal = tribunal;
        registre.officierEtatCivil = officier;
        registre.numeroPremierActe = registreDto.getNumeroPremierActe();
        registre.numeroDernierActe = registreDto.getNumeroDernierActe();
        registre.nombreDeFeuillets = registreDto.getNombreDeFeuillets();
        registre.statut = statut;
        
        registre.updated = LocalDateTime.now();
        
        registre.persist();
        
    }
    
    public void validerRegistre(@NotBlank String registreID){
        Registre registre = Registre.findById(registreID);
        if(registre.statut == StatutRegistre.PROJET){
            registre.statut = StatutRegistre.VALIDE;
        }else{
            throw new IllegalStateException("cannot validate, 'registre' is not in 'PROJECT' status");
        }
        
        
    }
    
    public void annulerRegistre(@NotBlank String registreID, String motifAnnulation){
        Registre registre = Registre.findById(registreID);
        registre.statut = StatutRegistre.ANNULE;
        registre.motifAnnulation = motifAnnulation;
        registre.dateAnnulation = LocalDateTime.now();
    }
    
    public void cloturerRegistre(@NotBlank String registreID){
        Registre registre = Registre.findById(registreID);
        registre.statut = StatutRegistre.CLOTURE;
    }
    
    public void consulterRegistre(){}
    
    public void deplacerRegistre(){}
   
    public List<RegistreDto> findAll(){
        log.debug("Request to get all registres");
        
        Stream<Registre> registres = Registre.findAll().stream();
        return registres.map(this::mapToDto).collect(Collectors.toList());       
    
    }
    
    public List<RegistreDto> findWithFilters(int offset,int pageSize, String type,int annee,int numero){
       
       PanacheQuery<Registre> query = Registre.find("typeRegistre", 
               Sort.by("numero", Sort.Direction.Descending),TypeRegistre.fromString(type));
       if(annee != 0){
           query.filter("anneeFilter", Map.of("anneeLimit",annee));
       }
       
       if(numero != 0){
           query.filter("numeroFilter", Map.of("numeroLimit",numero));
       }
       
       query.range(offset, offset + (pageSize-1));
       log.infof("OFFSET: %d PAGESIZE: %d", offset,offset + pageSize);
       return query.stream().map(this::mapToDto).collect(Collectors.toList());
      
    }
 
    
    public int count(String type, int annee,int numero){
        PanacheQuery<Registre> query = Registre.find("typeRegistre", TypeRegistre.fromString(type));
        if(annee != 0){
           query.filter("anneeFilter", Map.of("anneeLimit",annee));
        }
        if(numero != 0){
           query.filter("numeroFilter", Map.of("numeroLimit",numero));
       }
        return (int)query.count();
    }
    
    
    /*
    * propose une valeur pour le champ annee
    */
    public int annee(){
        return LocalDateTime.now().getYear();
    }
    
   /*
    * propose une valeur pour le champ numeroRegistre
    */
    public int numeroRegistre(String typeRegistre, int annee){
   
      TypedQuery<Integer> query =  em.createNamedQuery("Registre.findMaxNumero", Integer.class);
      query.setParameter("typeRegistre",TypeRegistre.fromString(typeRegistre));
      query.setParameter("annee", annee);
      
       return Optional.ofNullable(query.getSingleResult())
                  .map(r -> r + 1).orElse(1); 
    }
      
    /*
    * propose une valeur pour le champ numeroPremierActe
    */
    public int numeroPremierActe(String typeRegistre){
        TypedQuery<Integer> query =  em.createNamedQuery("Registre.findNumeroDernierActe", Integer.class);
        query.setParameter("typeRegistre",TypeRegistre.fromString(typeRegistre));
        query.setParameter("annee", annee());
      
       return Optional.ofNullable(query.getSingleResult())
                  .map(r -> r + 1).orElse(1); 
    
    }
    
    
   /*
    public TypeRegistre getTypeRgistre(String value){
        switch(value.toUpperCase()){
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
*/
    
    public StatutRegistre getStatutRegistre(String value){
        switch(value){
            case "PROJET":
                return StatutRegistre.PROJET;
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
    
    public  RegistreDto mapToDto(Registre registre){
        log.infof("-- TYPE REGISTRE: %s", registre.typeRegistre);
        
        return new RegistreDto(
                registre.id,
                registre.created,
                registre.updated, 
                registre.typeRegistre.name(), 
                registre.libelle, 
                registre.reference.localite.libelle, 
                registre.reference.localite.id, 
                registre.reference.centre.libelle, 
                registre.reference.centre.id, 
                registre.reference.annee, 
                registre.reference.numero, 
                registre.tribunal.libelle, 
                registre.tribunal.id,
                registre.officierEtatCivil.id,
                registre.numeroPremierActe, 
                registre.numeroDernierActe, 
                registre.nombreDeFeuillets, 
                registre.statut.name(),
                null,
                null);

    }
    
}


