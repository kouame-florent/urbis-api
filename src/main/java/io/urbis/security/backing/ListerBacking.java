/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.security.backing;

import io.urbis.acte.mariage.dto.ActeMariageDto;
import io.urbis.common.util.BaseBacking;
import io.urbis.security.dto.UserDto;
import java.io.Serializable;
import java.util.logging.Logger;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import org.primefaces.event.SelectEvent;


import javax.validation.constraints.NotBlank;

/**
 *
 * @author florent
 */
@Named(value = "userListerBacking")
@ViewScoped
public class ListerBacking extends BaseBacking implements Serializable{
    
    private static final Logger LOG = Logger.getLogger(ListerBacking.class.getName());
    
    @Inject
    LazyUserDataModel lazyUserDataModel;
     
       
    private String selectedUserID;
    
    public void onload(){
       
    }
    
    private UserDto selectedDto;
    
    @PostConstruct
    public void init(){
        
        
    }
 
    
    public void openModifierView(ActeMariageDto dto){
        
    }
    
   
    
    
    public void onNew(SelectEvent event){
        LOG.log(Level.INFO, "RETURN FROM NEW ACTE...");
    }
   
  
   
    public void openConsulterView(ActeMariageDto dto){
        
    }

   
    public void supprimer(@NotBlank String id){
      
    }
    
    public String returnToRegistresList(){
        return "/registre/lister.xhtml?faces-redirect=true";
    }
    

    public LazyUserDataModel getLazyUserDataModel() {
        return lazyUserDataModel;
    }

    public void setLazyUserDataModel(LazyUserDataModel lazyUserDataModel) {
        this.lazyUserDataModel = lazyUserDataModel;
    }

    public UserDto getSelectedDto() {
        return selectedDto;
    }

    public void setSelectedDto(UserDto selectedDto) {
        this.selectedDto = selectedDto;
    }

    
    
    
}
