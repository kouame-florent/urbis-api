/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.urbis.common.backing;

import io.urbis.acte.naissance.backing.ListerBacking;
import io.urbis.common.util.BaseBacking;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.Dependent;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author florent
 */
@Dependent
public class ImageBacking extends BaseBacking{
    
    private static final Logger LOG = Logger.getLogger(ListerBacking.class.getName());
    
    public String logoURI(){
        HttpServletRequest request = ((HttpServletRequest)facesContext.getCurrentInstance().getExternalContext().getRequest());
        
       LOG.log(Level.INFO, "--- REQUEST SCHEME: {0}", request.getScheme());
       LOG.log(Level.INFO, "--- REQUEST LOCAL PORT: {0}", request.getLocalPort());
       LOG.log(Level.INFO, "--- REQUEST LOCAL ADDR: {0}", request.getLocalAddr());
       
       var logo = request.getScheme() + "://" + request.getLocalAddr() + ":" + request.getLocalPort() + "/image/logo.png";
       
       LOG.log(Level.INFO, "--- LOGO URI: {0}", logo);
       
       return logo;
    }
    
}
