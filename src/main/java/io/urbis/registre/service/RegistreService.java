/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.registre.service;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Sort;
import io.urbis.acte.naissance.domain.ActeNaissance;
import io.urbis.acte.naissance.domain.StatutActeNaissance;
import io.urbis.param.domain.Centre;
import io.urbis.param.domain.Localite;
import io.urbis.param.domain.OfficierEtatCivil;
import io.urbis.registre.domain.Reference;
import io.urbis.registre.domain.Registre;
import io.urbis.registre.domain.TypeRegistre;
import io.urbis.registre.domain.StatutRegistre;
import io.urbis.param.domain.Tribunal;
import io.urbis.registre.dto.RegistreDto;
import io.urbis.registre.dto.RegistrePatchDto;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.validation.ValidationException;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
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
    
    @Transactional
    public void creer(@NotNull RegistreDto registreDto){
        
        TypeRegistre typeRegistre = TypeRegistre.fromString(registreDto.getTypeRegistre());
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
        
        Registre registre = new Registre(typeRegistre,registreDto.getLibelle(), reference,
                tribunal, officier, registreDto.getNumeroPremierActe(),
                registreDto.getNumeroDernierActe(),
                registreDto.getNombreDeFeuillets(),
                StatutRegistre.PROJET);
        
        registre.numeroProchainActe = registreDto.getNumeroPremierActe();
        registre.dateOuverture = registreDto.getDateOuverture();
        
        verifierOrdreNumero(typeRegistre, reference);
        
        if(!exist(typeRegistre, reference)){
           registre.persist(); 
        }else{
            String msg = String.format("le registre %d de %d existe déjà", 
                    registreDto.getNumero(),registreDto.getAnnee());
            throw new EntityExistsException(msg);
        }
        
       // return mapToDto(registre);
    }
    
    
    @Transactional
    public void modifierRegistre(@NotBlank String registreID,@NotNull RegistreDto registreDto){
        Registre registre = Registre.findById(registreID);
        if(registre == null){
           throw  new EntityNotFoundException("registre not found");
       }
        
        var typeRegistre = TypeRegistre.fromString(registreDto.getTypeRegistre());
        Localite localite = Localite.findById(registreDto.getLocalite());
        Centre centre = Centre.findById(registreDto.getCentreID());
        Tribunal tribunal = Tribunal.findById(registreDto.getTribunalID());
        OfficierEtatCivil officier = OfficierEtatCivil.findById(registreDto.getOfficierEtatCivilID());
        var statut = StatutRegistre.fromString(registreDto.getStatut());
        
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
        registre.dateOuverture = registreDto.getDateOuverture();
        registre.statut = statut;
        
        registre.updated = LocalDateTime.now();
        
        registre.persist();
        
    }
    
   
    
    /*
    * verifier que le registre de numero n est précédé du registre de n-1
    */
    public void verifierOrdreNumero(TypeRegistre type,Reference ref){
        if(ref.numero > 1){
            Reference previousRegRef = new Reference(ref.localite, ref.centre, ref.annee, ref.numero - 1);
            if(!exist(type, previousRegRef)){
                String msg = String.format("le registre numéro %d doit précéder le registre numéro %d ",ref.numero - 1,ref.numero );
                throw new ValidationException(msg);
            }
        }
    }
    
    @Transactional
    public void validerRegistre(@NotBlank String registreID,@NotNull RegistrePatchDto patchDTO){
        Registre registre = Registre.findById(registreID);
        if(registre == null){
           throw  new EntityNotFoundException("registre not found");
        }
        
                
        if(registre.statut == StatutRegistre.PROJET){
            registre.statut = StatutRegistre.VALIDE;
        }else{
            throw new IllegalStateException("cannot validate, 'registre' is not in 'PROJECT' status");
        }
        
        if(patchDTO.getDataOuverture() != null){
            registre.dateOuverture = patchDTO.getDataOuverture();
        }
    }
    
    @Transactional
    public void annulerRegistre(@NotBlank String registreID, String motifAnnulation){
        Registre registre = Registre.findById(registreID);
        if(registre == null){
           throw new EntityNotFoundException("registre not found");
        }
        registre.statut = StatutRegistre.ANNULE;
        registre.motifAnnulation = motifAnnulation;
        registre.dateAnnulation = LocalDateTime.now();
    }
    
    @Transactional
    public void cloturerRegistre(@NotBlank String registreID){
        Registre registre = Registre.findById(registreID);
        if(registre == null){
           throw new EntityNotFoundException("registre not found");
        }
        
        if(countActeNaissanceActeWithStatus(registre, StatutActeNaissance.PROJET) > 0){
            throw new ValidationException("le registre comprend des actes en projet");
        }
        
        registre.statut = StatutRegistre.CLOTURE;
    }
    
    private long countActeNaissanceActeWithStatus(Registre reg, StatutActeNaissance statutActeNaissance){
       long count = ActeNaissance.count("registre = ?1 AND statut = ?2", reg, statutActeNaissance);
       return count;
    }
    
    public void consulterRegistre(){}
    
    public void deplacerRegistre(){}
    
    @Transactional
    public void supprimer(@NotBlank String id){
        Registre.deleteById(id);
    }
   
    /*
    public List<RegistreDto> findAll(){
        log.debug("Request to get all registres");
        
        Stream<Registre> registres = Registre.findAll().stream();
        return registres.map(this::mapToDto).collect(Collectors.toList());       
    
    }
    */
    
    public RegistreDto findById(String id){
       //log.infof("FIND REG WITH ID: %s", id);
       Registre reg = Registre.findById(id);
       if(reg == null){
           throw new EntityNotFoundException("registre not found");
       }
       return mapToDto(reg);
    }
    
    @Transactional
    public List<RegistreDto> findWithFilters(int offset,int pageSize, String type,int annee,int numero){
       
       PanacheQuery<Registre> query = Registre.find("typeRegistre", 
               Sort.by("annee", Sort.Direction.Descending).and("numero", Sort.Direction.Descending),
               TypeRegistre.fromString(type));
       if(annee != 0){
           query.filter("anneeFilter", Map.of("anneeLimit",annee));
       }
       
       if(numero != 0){
           query.filter("numeroFilter", Map.of("numeroLimit",numero));
       }
       
       PanacheQuery<Registre> rq =  query.range(offset, offset + (pageSize-1));
       //log.infof("OFFSET: %d PAGESIZE: %d", offset,offset + pageSize);
       return rq.stream().map(this::mapToDto).collect(Collectors.toList());
      
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
    public Registre findByTypeAndReference(TypeRegistre type, Reference ref){
        TypedQuery<Registre> query =  em.createNamedQuery("Registre.findByTypeAndReference", Registre.class);
        query.setParameter("typeRegistre", type);
        query.setParameter("localite",ref.localite);
        query.setParameter("centre", ref.centre);
        query.setParameter("annee", ref.annee);
        query.setParameter("numero", ref.numero);
        
        query.getResultList();
    }
    */
    
    public boolean exist(TypeRegistre typeRegistre,Reference ref){
        TypedQuery<Registre> query =  em.createNamedQuery("Registre.findByTypeAndReference", Registre.class);
        query.setParameter("typeRegistre", typeRegistre);
        query.setParameter("localite", ref.localite);
        query.setParameter("centre", ref.centre);
        query.setParameter("annee", ref.annee);
        query.setParameter("numero", ref.numero);
        
        List<Registre> rs = query.getResultList();
        return !rs.isEmpty();
        
    }
    
    /*
    * propose une valeur pour le champ anneeCourante
    */
    public int anneeCourante(){
        return LocalDateTime.now().getYear();
    }
    
   /*
    * propose une valeur pour le champ numeroRegistre
    */
    public int numeroRegistre(String typeRegistre, int annee){
   
        TypedQuery<Integer> query =  em.createNamedQuery("Registre.findMaxNumeroByType", Integer.class);
        query.setParameter("typeRegistre",TypeRegistre.fromString(typeRegistre));
        query.setParameter("annee", annee);
          
        try{
            Integer num = query.getSingleResult();
            log.infof("-- NUM REGISTRE: %d", num);
            if(num != null){
                return num + 1;
            }
            log.infof("aucun registre trouvé...");
            return 1;
        }catch(NoResultException ex){
            log.infof("aucun registre trouvé...");
            return 1;
        }
      
      
    }
    
    /*
    public int numeroRegistre(String typeRegistre, int annee,int numero){
        TypedQuery<Integer> query =  em.createNamedQuery("Registre.findNumeroDernierActe", Integer.class);
        query.setParameter("annee", annee);
        query.setParameter("numero", numero);
        
        var num = query.getSingleResult();
          if(num != null){
              return num + 1;
          }else{
              return 1;
          }

      
    }
    */
    
      
    /*
    * propose une valeur pour le champ numeroPremierActe
    */
    public int numeroPremierActeCourant(String typeRegistre,int annee){
        TypedQuery<Integer> query =  em.createNamedQuery("Registre.findMaxNumeroDernierActe", Integer.class);
        log.infof("NUM PREMIER QUERY: %s", query);
        query.setParameter("typeRegistre",TypeRegistre.fromString(typeRegistre));
        query.setParameter("annee", annee);
        
        try{
            Integer num = query.getSingleResult();
            if(num != null){
                return num + 1;
            }
            log.infof("aucun acte précédent...");
            return 1;
        }catch(NoResultException ex){
            log.infof("aucun acte précédent...");
            return 1;
        }
        /*
        try{
            var num = query.getSingleResult();
            return num + 1;
        }catch(NoResultException ex){
            log.infof("aucun acte précédent...");
            return 1;
        }
        */
        
    }
    
    public int numeroPremierActe(String typeRegistre,int annee,int numero){
        TypedQuery<Integer> query =  em.createNamedQuery("Registre.findNumeroDernierActe", Integer.class);
        log.infof("NUM PREMIER QUERY: %s", query);
        query.setParameter("typeRegistre",TypeRegistre.fromString(typeRegistre));
        query.setParameter("annee", annee);
        query.setParameter("numero", numero);
        
        try{
            Integer num = query.getSingleResult();
            if(num != null){
                return num + 1;
            }
            log.infof("aucun acte précédent...");
            return 1;
        }catch(NoResultException ex){
            log.infof("aucun acte précédent...");
            return 1;
        }
        
        /*
        try{
            var num = query.getSingleResult();
            return num + 1;
        }catch(NoResultException ex){
            log.infof("aucun acte précédent...");
            return 1;
        }
        */
                
    }
    
    public int numeroDernierActe(String typeRegistre,int annee,int numero,int numeroPremierActe, int nombreFeuillet){
        TypedQuery<Integer> query =  em.createNamedQuery("Registre.findNumeroPremierActe", Integer.class);
        log.infof("NUM PREMIER QUERY: %s", query);
        query.setParameter("typeRegistre",TypeRegistre.fromString(typeRegistre));
        query.setParameter("annee", annee);
        query.setParameter("numero", numero);
        
        try{
            Integer num = query.getSingleResult();
            if(num != null){
                return num - 1;
            }
            log.infof("aucun acte suivant...");
            return numeroPremierActe + nombreFeuillet - 1;
        }catch(NoResultException ex){
            log.infof("aucun acte suivant...");
            return numeroPremierActe + nombreFeuillet - 1;
        }
        
        /*
        try{
            var num = query.getSingleResult();
            return num - 1;
        }catch(NoResultException ex){
            log.infof("aucun acte suivant...");
            return numeroPremierActe + nombreFeuillet - 1;
        }
        */
    }
    
    
    
    public int numeroProchainActe(){
        return 0;
    }
    
    /**
     * 
     * Retourne le registre registreCourant utilisé
     * @param typeString
     * @return 
     */
    public RegistreDto registreCourant(String typeString){
        TypeRegistre typeRegistre = TypeRegistre.fromString(typeString);
        int annee = LocalDateTime.now().getYear();
        TypedQuery<Registre> query =  em.createNamedQuery("Registre.findWithMaxNumeroByType", Registre.class);
        query.setParameter("typeRegistre", typeRegistre);
        query.setParameter("annee", annee);
        query.setParameter("statut", StatutRegistre.VALIDE);
        
        List<Registre> rs = query.getResultList();
       if(!rs.isEmpty()){
           Registre r = rs.get(0);
           return mapToDto(r);
       }else{
           Response res = Response.status(Response.Status.NOT_FOUND)
                   .entity("aucun registre validé n'a été trouvé").build();
           throw new WebApplicationException(res);
       }
    
    }
    
    public List<RegistreDto> findByTypeAndDateOuverture(String typeString, LocalDate dateOuverture){
        TypeRegistre typeRegistre = TypeRegistre.fromString(typeString);
        int annee = LocalDateTime.now().getYear();
        TypedQuery<Registre> query =  em.createNamedQuery("Registre.findByTypeAndDateOuverture", Registre.class);
        query.setParameter("typeRegistre", typeRegistre);
        query.setParameter("dateOuverture", dateOuverture);
        
        
        List<Registre> rs = query.getResultList();
        return rs.stream().map(r -> mapToDto(r)).collect(Collectors.toList());
       
    
    }
    
    /**
     * 
     * @param registre
     * @return 
     */
    public int nombreActe(Registre registre){
        int nbrActe = (int)ActeNaissance.count("registre", registre);
        //log.infof("----- NOMBRE ACTE : %s", nbrActe);
        //+ActeMariage.count()
        //+ActeDivers.count()...
        return nbrActe;
    }
    
    public  RegistreDto mapToDto(@NotNull Registre registre){
       // log.infof("-- TYPE REGISTRE: %s", registre.typeRegistre);
       
       RegistreDto reg = new RegistreDto();
       
       reg.setId(registre.id);
       reg.setCreated(registre.created);
       reg.setUpdated(registre.updated);
       reg.setTypeRegistre(registre.typeRegistre.name());
       reg.setLibelle(registre.libelle);
       reg.setLocalite(registre.reference.localite.libelle);
       reg.setLocaliteID(registre.reference.localite.id);
       reg.setCentre(registre.reference.centre.libelle);
       reg.setCentreID(registre.reference.centre.id);
       reg.setAnnee(registre.reference.annee);
       reg.setNumero(registre.reference.numero);
       reg.setTribunal(registre.tribunal.libelle);
       reg.setTribunalID(registre.tribunal.id);
       reg.setOfficierEtatCivilNomComplet(registre.officierEtatCivil.prenoms + " " + registre.officierEtatCivil.nom);
       reg.setOfficierEtatCivilID(registre.officierEtatCivil.id);
       reg.setNumeroPremierActe(registre.numeroPremierActe);
       reg.setNumeroDernierActe(registre.numeroDernierActe);
       reg.setNombreDeFeuillets(registre.nombreDeFeuillets);
       reg.setDateOuverture(registre.dateOuverture);
       reg.setNombreActe(nombreActe(registre));
       reg.setStatut(registre.statut.name());
       reg.setDateAnnulation(registre.dateAnnulation);
       reg.setMotifAnnulation(registre.motifAnnulation);
        
       return reg;

    }
    
}


