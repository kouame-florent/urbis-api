/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.urbis.template.backing;


import io.urbis.template.dto.BaseDto;
import javax.annotation.PostConstruct;
import javax.validation.constraints.NotBlank;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author florent
 * 
 */
public interface BaseListerBacking{
    
    
    public void onload();
    
    public void init();
        
    public void openCreateView();
    
    public void onNew(SelectEvent event);
    
    public void openreadView(BaseDto dto);
    
    public void openOpdateView(BaseDto dto);
    
    public void supprimer(@NotBlank String id);
    
}
