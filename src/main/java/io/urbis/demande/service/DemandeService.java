/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.demande.service;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Sort;
import io.urbis.acte.common.domain.Acte;
import io.urbis.acte.deces.service.ActeDecesService;
import io.urbis.acte.divers.service.ActeDiversService;
import io.urbis.acte.mariage.dto.ActeMariageDto;
import io.urbis.acte.mariage.service.ActeMariageService;
import io.urbis.acte.naissance.service.ActeNaissanceService;
import io.urbis.acte.common.service.ActeService;
import io.urbis.common.domain.TypePiece;

import io.urbis.demande.domain.Demande;
import io.urbis.demande.domain.Demandeur;
import io.urbis.demande.dto.DemandeDto;
import io.urbis.registre.domain.TypeRegistre;
import io.urbis.security.service.AuthenticationContext;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.jboss.logging.Logger;

/**
 *
 * @author florent
 */
@ApplicationScoped
@Transactional
public class DemandeService {
    
    @Inject
    ActeDecesService acteDecesService;
    
    @Inject
    ActeDiversService acteDiversService;
    
    @Inject
    ActeMariageService acteMariageService;
    
    @Inject
    ActeNaissanceService acteNaissanceService;
    
    @Inject
    ActeService acteService;
    
        
    @Inject
    EntityManager em;
    
    @Inject
    Logger log;
    
    @Inject
    AuthenticationContext authenticationContext;
    
        
    public void creer(@NotNull DemandeDto dto){
        
        TypeRegistre typeRegistre = TypeRegistre.fromString(dto.getTypeRegistre());
        
        Acte acte = null;
        
        try{
            
            acte = acteService.findByDemandeCreteria(dto.getNumeroActe(),typeRegistre,
                            dto.getDateOuvertureRegistre());
           
        }catch(EntityNotFoundException ex){
            throw  ex;
        }
       
        if(acte != null){
            Demandeur demandeur = new Demandeur();
        
            Demande demande = new Demande(demandeur);

            demande.acte = acte;
            demande.dateHeureDemande = dto.getDateHeureDemande();
            demande.dateHeureRdvRetrait = dto.getDateHeureRdvRetrait();
            demande.dateOuvertureRegistre = dto.getDateOuvertureRegistre();
            demande.demandeur.email = dto.getDemandeurEmail();
            demande.demandeur.nom = dto.getDemandeurNom();
            demande.demandeur.numeroTelephone = dto.getDemandeurNumeroTelephone();
            demande.demandeur.numeroPiece = dto.getDemandeurNumeroPiece();
            demande.demandeur.prenoms = dto.getDemandeurPrenoms();
            demande.demandeur.qualite = dto.getDemandeurQualite();
            demande.demandeur.typePiece = TypePiece.fromString(dto.getDemandeurTypePiece());

            demande.nombreCopies = dto.getNombreCopies();
            demande.nombreExtraits = dto.getNombreExtraits();
            demande.numero = dto.getNumero();
            demande.numeroActe = dto.getNumeroActe();
            demande.typeRegistre = TypeRegistre.fromString(dto.getTypeRegistre());
            demande.updatedBy = authenticationContext.userLogin();

            demande.persist();
        
        }
        
       
    
    }
    
    public DemandeDto findById(@NotBlank String id){
       Demande d = Demande.findById(id);
       return mapToDto(d);
    }
    
    public void modifier(@NotBlank String id,@NotNull DemandeDto demande){
    
    }
    
    
    public List<DemandeDto> findWithFilters(int offset,int pageSize){
        PanacheQuery<Demande>  query = Demande.findAll(Sort.by("numero").descending());
        
        PanacheQuery<Demande> rq =  query.range(offset, offset + (pageSize-1));
        
        return rq.stream().map(this::mapToDto).collect(Collectors.toList());
      
    }
    
    
    public int count(){
        return (int)Demande.count();
    }
    
     public int numeroDemande(){
   
        TypedQuery<Integer> query =  em.createNamedQuery("Demande.findMaxNumero", Integer.class);   
          
        try{
            Integer num = query.getSingleResult();
            log.infof("-- NUM DEMANDE: %d", num);
            if(num != null){
                return num + 1;
            }
            log.infof("aucune demande trouvée...");
            return 1;
        }catch(NoResultException ex){
            log.infof("aucune demande trouvée...");
            return 1;
        }
      
      
    }
    


    private DemandeDto mapToDto(@NotNull Demande demande){
        
        DemandeDto dto = new DemandeDto();
        
        dto.setCreated(demande.created);
        dto.setDateHeureDemande(demande.dateHeureDemande);
        dto.setDateHeureRdvRetrait(demande.dateHeureRdvRetrait);
        dto.setDateOuvertureRegistre(demande.dateOuvertureRegistre);
        dto.setDemandeurEmail(demande.demandeur.email);
        dto.setDemandeurNom(demande.demandeur.nom);
        dto.setDemandeurNumeroTelephone(demande.demandeur.numeroTelephone);
        dto.setDemandeurNumeroPiece(demande.demandeur.numeroPiece);
        dto.setDemandeurPrenoms(demande.demandeur.prenoms);
        dto.setDemandeurQualite(demande.demandeur.qualite);
        dto.setDemandeurTypePiece(demande.demandeur.typePiece.name());
        dto.setId(demande.id);
        dto.setNombreCopies(demande.nombreCopies);
        dto.setNombreExtraits(demande.nombreExtraits);
        dto.setNumero(demande.numero);
        dto.setNumeroActe(demande.numeroActe);
        dto.setTypeRegistre(demande.typeRegistre.name());
        dto.setUpdated(demande.updated);
        
        return dto;
    }
}
