/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.registre.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author florent
 */
@Data
@AllArgsConstructor
public class LocaliteDto {
    
    private String id;
    
    private LocalDateTime created ;
    private LocalDateTime updated ;
    
    private String code;
    private String libelle;
    
}
