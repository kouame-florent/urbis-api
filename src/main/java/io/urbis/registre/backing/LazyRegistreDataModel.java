/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.registre.backing;


import io.urbis.registre.dto.RegistreDto;
import io.urbis.registre.service.RegistreService;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.filter.FilterConstraint;




/**
 *
 * @author florent
 */
@Dependent
public class LazyRegistreDataModel extends LazyDataModel<RegistreDto> {
    
   // private List<RegistreDto> datasource;
    
    private static final Logger LOG = Logger.getLogger(LazyRegistreDataModel.class.getName());

    
    @Inject 
    RegistreService registreService;
    
    
    private String typeRegistre;
    private int annee;
    private int numero;
    private LocalDate dateOuverture;
    

    @Override
    public List<RegistreDto> load(int offset, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
        LOG.log(Level.INFO,"Loading the lazy registre data between {0} and {1}", new Object[]{offset, offset+pageSize} );
        
        setFilterValues(filterBy);
        
        if(typeRegistre == null){
            throw new IllegalStateException("'typeRegistre' cannot be null");
        }
        List<RegistreDto> regs = registreService.findWithFilters(offset, pageSize, typeRegistre, annee, numero,dateOuverture);
        LOG.log(Level.INFO,"LOADED DATA SIZE: {0}", regs.size());
        int count = registreService.count(typeRegistre,annee,numero);
        setRowCount(count);
       
        return regs;
    }
    
    private void setFilterValues(Map<String, FilterMeta> filterBy){
         annee = 0;
         numero = 0;
         dateOuverture = null;
        
        for(FilterMeta filter : filterBy.values()) {
            FilterConstraint constraint = filter.getConstraint();
            Object filterValue = filter.getFilterValue();
            String field = filter.getField();
            
            LOG.log(Level.INFO,"FILTER CONSTRAINT: {0}", constraint);
            LOG.log(Level.INFO,"FILTER VALUE: {0}", filterValue);
            LOG.log(Level.INFO,"FILTER FIELD: {0}", field);
            
            if(field.equals("annee")){
                try{
                    annee = Integer.parseInt(filterValue.toString());
                }catch(NumberFormatException ex){
                    LOG.log(Level.INFO,"value not a number: {0}", filterValue);
                }
            }
            
            if(field.equals("numero")){
                try{
                    numero = Integer.parseInt(filterValue.toString());
                }catch(NumberFormatException ex){
                    LOG.log(Level.INFO,"value not a number: {0}", filterValue);
                }
            }
            
            if(field.equals("dateOuverture")){
                var val = filterValue.toString();
                try{
                  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                  dateOuverture =  LocalDate.parse(val,formatter);
                }catch(DateTimeParseException ex){
                    LOG.log(Level.WARNING, "cannot parse '{0}' to localdate", val);  
                }
                
                
            }
            
            
        }
    
    }

    

    
    @Override
    public int count(Map<String, FilterMeta> arg0) {
        return registreService.count(typeRegistre,annee,numero);
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void setTypeRegistre(String typeRegistre) {
        this.typeRegistre = typeRegistre;
    }

    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public LocalDate getDateOuverture() {
        return dateOuverture;
    }

    public void setDateOuverture(LocalDate dateOuverture) {
        this.dateOuverture = dateOuverture;
    }
    
    
    
    
    
}
