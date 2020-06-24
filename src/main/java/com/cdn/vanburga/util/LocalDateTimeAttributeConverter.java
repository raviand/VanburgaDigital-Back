package com.cdn.vanburga.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.cdn.vanburga.exception.LocalDateTimeFormatException;

 
@Converter(autoApply = true)
public class LocalDateTimeAttributeConverter implements AttributeConverter<LocalDateTime, Date> {
     
    @Override
    public Date convertToDatabaseColumn(LocalDateTime locDateTime) {
        return locDateTime == null ? null : DateUtils.asDate(locDateTime) ;
    }
 
    @Override
    public LocalDateTime convertToEntityAttribute(Date sqlTimestamp) {
        return sqlTimestamp == null ? null : DateUtils.asLocalDate( sqlTimestamp ) ;
    }
    
	private static final DateTimeFormatter TIMESTAMP_PARSER = new DateTimeFormatterBuilder()
			   .parseCaseInsensitive()
			   .append(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))
			   .toFormatter();

	private static final DateTimeFormatter TIMESTAMP_PARSER_EXPIRATION = new DateTimeFormatterBuilder()
			   .parseCaseInsensitive()
			   .append(DateTimeFormatter.ofPattern("yyyyMMddHHmm"))
			   .toFormatter();

	private static final DateTimeFormatter TIMESTAMP_PRITY_PARSER = new DateTimeFormatterBuilder()
			.parseCaseInsensitive()
			.append(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
			.toFormatter();
	
	private static final DateTimeFormatter DATE_FORMAT = new DateTimeFormatterBuilder()
			.parseCaseInsensitive()
			.append(DateTimeFormatter.ofPattern("yyyyMMdd"))
			.toFormatter();


	/**
	 * Deserializa un string con formato "yyyyMMddHHmmss" a LocalDateTime
	 * @param stringDate
	 * @return
	 * @throws LocalDateTimeFormatException
	 */
	public static LocalDateTime deserialize(String stringDate)
			throws LocalDateTimeFormatException	 {
		try {
			
			return LocalDateTime.from( TIMESTAMP_PARSER.parse(stringDate) );
			
		}catch(Exception e) {
			throw new LocalDateTimeFormatException("Invalid date format must be 'yyyyMMddHHmmss'");
		}
	}
	
	/**
	 * Convierte un LocalDateTime a un String con formato "yyyyMMddHHmmss"
	 * 
	 * @param stringDate
	 * @return
	 * @throws LocalDateTimeFormatException
	 */
	public static String serialize(LocalDateTime stringDate)
			throws LocalDateTimeFormatException	 {
		try {
			
			return stringDate.format(TIMESTAMP_PARSER);
			
		}catch(Exception e) {
			throw new LocalDateTimeFormatException("Invalid date format must be 'yyyyMMddHHmmss'");
		}
	}
	
	/**
	 * Convierte un String con formato "yyyyMMddHHmm" a LocalDateTime
	 * @param stringDate
	 * @return
	 * @throws LocalDateTimeFormatException
	 */
	public static LocalDateTime deserializeExpiration(String stringDate)
			throws LocalDateTimeFormatException	 {
		try {
			
			return LocalDateTime.from( TIMESTAMP_PARSER_EXPIRATION.parse(stringDate) );
			
		}catch(Exception e) {
			throw new LocalDateTimeFormatException("Invalid date format must be 'yyyyMMddHHmm'");
		}
	}
	
	/**
	 * Convierte un LocalDateTime a formato String "dd/MM/yyyy HH:mm"
	 * @param stringDate
	 * @return
	 * @throws LocalDateTimeFormatException
	 */
	public static String serializeNiceFormat(LocalDateTime stringDate)
			throws LocalDateTimeFormatException	 {
		try {
			
			return stringDate.format(TIMESTAMP_PRITY_PARSER);
			
		}catch(Exception e) {
			throw new LocalDateTimeFormatException("Invalid date format must be 'yyyyMMddHHmmss'");
		}
	}
	
	/**
	 * Convierte un String con formato "yyyyMMdd" a LocalDateTime
	 * @param stringDate
	 * @return
	 * @throws LocalDateTimeFormatException
	 */
	public static LocalDateTime deserializeBirthDate(String stringDate)
			throws LocalDateTimeFormatException	 {
		try {
			
			return LocalDateTime.from( DATE_FORMAT.parse(stringDate) );
			
		}catch(Exception e) {
			throw new LocalDateTimeFormatException("Invalid date format must be 'yyyyMMdd'");
		}
	} 
	
	/**
	 * Valida que el String recivido sea del formato "yyyyMMdd"
	 * @param birthDate
	 * @return
	 */
	public static boolean isValidDate(String date) {
		try {
			LocalDateTime.from( DATE_FORMAT.parse(date) );
			return true;
		}catch(Exception e) {
			return false;
		}
	}
}
