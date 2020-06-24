package com.cdn.vanburga.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DateUtils {
    public static Date asDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }
 
    public static Date asDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
 
    public static LocalDateTime asLocalDate(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
 
    public static LocalDateTime asLocalDateTime(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
    
    public static boolean validateDatesFromTo(Date dateFrom, Date dateTo) {
		if(dateFrom.after(dateTo)) {
			return false;
		}
		return true;		
	}
    
    public static boolean validateDatesFromTo(LocalDateTime dateFrom, LocalDateTime dateTo) {
		if(dateFrom.isAfter(dateTo)) {
			return false;
		}
		return true;		
	}
}
