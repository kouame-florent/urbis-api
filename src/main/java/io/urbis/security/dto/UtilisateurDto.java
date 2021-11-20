/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.security.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 *
 * @author florent
 */
@AllArgsConstructor
@NoArgsConstructor
public class UtilisateurDto {

    public String username;

    public String password;

    public String role;
    
    public String statut;
}
