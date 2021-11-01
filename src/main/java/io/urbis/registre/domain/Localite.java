/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.registre.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.urbis.common.domain.BaseEntity;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

/**
 *
 * @author florent
 */
@Table(name = "localite")
@Entity
public class Localite extends  BaseEntity{
    
    public String code;
    public String libelle;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type_localite",nullable = false)
    public TypeLocalite typeLocalite;
    
}
