/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.mention.dto;

import java.time.LocalDate;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *
 * @author florent
 */

@EqualsAndHashCode(callSuper = true,onlyExplicitlyIncluded = true)
public class MentionDecesDto extends MentionDto {
    
    private LocalDate date;
    private String lieu;

    private String localite;
    private LocalDate dateDressage;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getLocalite() {
        return localite;
    }

    public void setLocalite(String localite) {
        this.localite = localite;
    }

    public LocalDate getDateDressage() {
        return dateDressage;
    }

    public void setDateDressage(LocalDate dateDressage) {
        this.dateDressage = dateDressage;
    }
    
    

}
