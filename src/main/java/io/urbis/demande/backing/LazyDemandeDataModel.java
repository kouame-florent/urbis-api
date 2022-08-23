/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.demande.backing;


import io.urbis.demande.dto.DemandeDto;
import io.urbis.demande.service.DemandeService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

/**
 *
 * @author florent
 */
@Dependent
public class LazyDemandeDataModel extends LazyDataModel<DemandeDto>{

    private static final Logger LOG = Logger.getLogger(LazyDemandeDataModel.class.getName());
    
    @Inject
    DemandeService demandeService;
    
   // private String registreID;
    
    private String typeRegistre;
    
    List<DemandeDto> demandes = new ArrayList<>();

    @Override
    public List<DemandeDto> load(int offset, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
        
        LOG.log(Level.INFO,"Loading the lazy data between {0} and {1}", new Object[]{offset, offset+pageSize} );
        if(typeRegistre == null){
            throw new IllegalStateException("'typeRegistre' cannot be null");
        }
        demandes = demandeService.findWithFilters(offset, pageSize,typeRegistre);
        LOG.log(Level.INFO,"LOADED DATA SIZE: {0}", demandes.size());
        int count = demandeService.count();
        setRowCount(count);
        
        return demandes;
    }
    
    
    @Override
    public DemandeDto getRowData(String rowKey) { 
        //LOG.log(Level.INFO,"ROW KEY: {0}", rowKey);
        //LOG.log(Level.INFO,"DATA SIZE: {0}", actes.size());
        
        for(DemandeDto demande : demandes){
            //LOG.log(Level.INFO,"--- CURRENT ID: {0}", acte.getId());
            if(demande.getId().equals(rowKey)){
                return demande;
            }
            
            
        }
        throw  new IllegalStateException("cannot get acteNaissanceDto from rowkey");
        /*
        return actes.stream().filter(a -> a.getId().equals(rowKey))
                .peek(a -> LOG.log(Level.INFO,"--- CURRENT ID: {0}", a.getId()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("cannot get acteNaissanceDto from rowkey"));
        */
    }


    @Override
    public String getRowKey(DemandeDto demande) {
        return demande.getId();
    }

    
    
    @Override
    public int count(Map<String, FilterMeta> arg0) {
        return demandeService.count();
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getTypeRegistre() {
        return typeRegistre;
    }

    public void setTypeRegistre(String typeRegistre) {
        this.typeRegistre = typeRegistre;
    }
    
    
    
}
