package com.cdn.vanburga.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ServiceUtils {

	public static String objectToJson(Object object) {
		try {
			ObjectMapper Obj = new ObjectMapper();
			return Obj.writerWithDefaultPrettyPrinter().writeValueAsString(object);
		} catch (JsonProcessingException e) {
			return null;
		}
	}
	
}
