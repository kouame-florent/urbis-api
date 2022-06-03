/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.acte.naissance.service;

import io.agroal.api.AgroalDataSource;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.urbis.acte.naissance.domain.ActeNaissance;
import io.urbis.acte.naissance.domain.ActeNaissanceEtat;
import io.urbis.acte.naissance.dto.ActeNaissanceEtatDto;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.jboss.logging.Logger;

/**
 *
 * @author florent
 */
@ApplicationScoped
public class ActeNaissanceEtatService {
    
    
    
    @Inject
    Logger log;
    
    
       
    public ActeNaissanceEtat findByActeNaissance(ActeNaissance acteNaissance){
        PanacheQuery<ActeNaissanceEtat> pq = ActeNaissanceEtat.find("acteNaissance", acteNaissance);
        ActeNaissanceEtat etat = pq.firstResult();
        if(etat == null){
            throw new EntityNotFoundException("ActeNaissanceEtat not found");
        }
        
        return etat;
    }
    
   public void modifier(@NotBlank String id,@NotNull ActeNaissanceEtatDto dto){
       
   }
   
   
   
    
}
