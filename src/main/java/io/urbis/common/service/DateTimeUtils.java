/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.common.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAccessor;
import java.util.Locale;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 *
 * @author florent
 */
public class DateTimeUtils {
    /*
    public static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy[ HH:mm:ss]",new Locale("fr","FR"));
    
    public static LocalDateTime fromStringToDateTime(String dateTimeStr){
        
        try{
            LocalDateTime dateTime;
            TemporalAccessor temporalAccessor = dateTimeFormatter.parseBest(dateTimeStr, LocalDateTime::from, LocalDate::from);
            if (temporalAccessor instanceof LocalDateTime) {
              dateTime = (LocalDateTime)temporalAccessor;
            } else {
              dateTime = ((LocalDate)temporalAccessor).atStartOfDay();
            }
            return dateTime;
        }catch(DateTimeParseException ex){
            System.out.printf("PARSED STRING: %s", ex.getParsedString());
            ex.printStackTrace();
            throw new WebApplicationException(ex, Response.Status.BAD_REQUEST);
        }
        
    }
    
    public static String fromDateTimeToString(LocalDateTime localDateTime){
       return localDateTime.format(dateTimeFormatter);
    }
*/
    
}
