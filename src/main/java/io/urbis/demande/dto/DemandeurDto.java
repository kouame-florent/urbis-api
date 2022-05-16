/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.demande.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author florent
 */
@Data
@NoArgsConstructor
public class DemandeurDto {
    
    private String demandeurNom;
    
    private String demandeurPrenoms;
    
    private String demandeurNumero;
    
    private String demandeurEmail;
    
    private String demandeurTypePiece;
    
    private String demandeurNumeroPiece;
    
    private String demandeurQualite;
    
    
    private String numero;

    private String typeRegistre;

    private int numeroActe;

    private LocalDate dateOuvertureRegistre;
  
    private int nombreExtraits;
 
    private int nombreCopies;
    
    private LocalDateTime dateHeureDemande;
    
    private LocalDateTime dateHeureRdvRetrait;
    
    //@NotBlank
    private String ActeID;
    
    
}
