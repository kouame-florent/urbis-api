/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.registre.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *
 * @author florent
 */
@Data
@AllArgsConstructor
public class RegistrePatchDto {
    
    private String statut;
    private LocalDate dataOuverture;
    private String motifAnnulation;
}
