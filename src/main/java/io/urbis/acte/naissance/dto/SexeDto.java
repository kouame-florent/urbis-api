/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.acte.naissance.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author florent
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SexeDto {
    
    private String code;
    private String libelle;
}
