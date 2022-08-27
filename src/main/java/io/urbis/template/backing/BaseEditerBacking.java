/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.urbis.template.backing;

/**
 *
 * @author florent
 */
public interface BaseEditerBacking {
    
     public void onload();
    
    public void init();
        
    public void create();
       
    public void update();
    
    public boolean renderedCreateButton();
    
    public boolean renderedUpdateButton();
     
    public void initDto();
}
