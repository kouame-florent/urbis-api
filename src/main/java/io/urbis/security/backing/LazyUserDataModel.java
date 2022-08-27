/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.security.backing;


import io.urbis.acte.mariage.backing.*;
import io.urbis.acte.mariage.dto.ActeMariageDto;
import io.urbis.acte.mariage.service.ActeMariageService;
import io.urbis.security.dto.UserDto;
import io.urbis.security.service.UserService;
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
public class LazyUserDataModel extends LazyDataModel<UserDto>{
    
    private static final Logger LOG = Logger.getLogger(LazyUserDataModel.class.getName());
    
    @Inject
    UserService userService;
    
        
    List<UserDto> users = new ArrayList<>();
    
    @Override
    public List<UserDto> load(int offset, int pageSize, Map<String, SortMeta> map, Map<String, FilterMeta> map1) {
        users = userService.findWithFilters(offset, pageSize);
        LOG.log(Level.INFO,"LOADED DATA SIZE: {0}", users.size());
        int count = userService.count();
        setRowCount(count);
        return users;
    }

    @Override
    public int count(Map<String, FilterMeta> map) {
        return userService.count();
    }

   @Override
    public UserDto getRowData(String rowKey) { 
        //LOG.log(Level.INFO,"ROW KEY: {0}", rowKey);
        //LOG.log(Level.INFO,"DATA SIZE: {0}", actes.size());
        
        for(UserDto dto : users){
            //LOG.log(Level.INFO,"--- CURRENT ID: {0}", acte.getId());
            if(dto.getId().equals(rowKey)){
                return dto;
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
    public String getRowKey(UserDto dto) {
        return dto.getId();
    }


    
    
}
