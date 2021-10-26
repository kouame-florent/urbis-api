/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.mention.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @author florent
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DecesDto extends MentionDto {
    
    private String date;
    private String lieu;

    private String localite;
    private String dateDressage;
}
