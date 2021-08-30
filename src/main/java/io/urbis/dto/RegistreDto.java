/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author florent
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistreDto {
    
    private String id ;
    
    private LocalDateTime created; 
    private LocalDateTime updated;
       
    private long version;
    
    private String typeRegistre;
    
    private String libelle;
   
    private String localite; 
    private String localiteID; 
    
    private String centre;
    private String centreID;
    
    private long annee;
    
    private long numero;
    
    private String tribunal;
    private String tribunalID;
    
    private String officierEtatCivilID;
    
    private long numeroPremierActe; 
    
    private long numeroDernierActe;
    
    private long nombreDeFeuillets;
    
    private String statut;
}
