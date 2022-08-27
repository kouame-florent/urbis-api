/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.urbis.common.backing;

import io.urbis.common.util.BaseBacking;
import java.io.Serializable;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import org.primefaces.PrimeFaces;

/**
 *
 * @author florent
 */
@Named(value = "globalMenuBacking")
@RequestScoped
public class GlobalMenuBacking extends BaseBacking implements Serializable{

    private static final Logger LOG = Logger.getLogger(GlobalMenuBacking.class.getName());
    
    
    
    public void openParamsView(){
        Map<String,Object> options = getDialogOptions(96, 96, true);
        options.put("resizable", false);
        PrimeFaces.current().dialog().openDynamic("/parametre/parametres", options, null);
    }
    
    public String openUserView(){
    
        String url = "/security/lister.xhtml?faces-redirect=true";
        LOG.log(Level.INFO, "--- DEMANDE URL: {0}", url);   
        
        return  url;
    }
    
    public String openDemandeView(){
        String url = "/demande/lister.xhtml?faces-redirect=true";
        LOG.log(Level.INFO, "--- DEMANDE URL: {0}", url);   
        
        return  url;
    }
    
    public String openRegistreView(){
        return "/registre/lister.xhtml?faces-redirect=true";
    }
    
}
