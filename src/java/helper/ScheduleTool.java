/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 *
 * @author Marie
 */
public class ScheduleTool {
    
    public static Date plusOneDay(Date date){
         LocalDateTime localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        return Date.from(LocalDateTime.from(localDateTime.plusDays(1)).atZone(ZoneId.systemDefault()).toInstant());
    }
    
    public static Date minusOneDay(Date date) {
        LocalDateTime localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        return Date.from(LocalDateTime.from(localDateTime.minusDays(1)).atZone(ZoneId.systemDefault()).toInstant());
    }
}
