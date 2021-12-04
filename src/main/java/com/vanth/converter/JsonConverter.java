package com.vanth.converter;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.ObjectWriter;

public class JsonConverter {
	 public static String convertObjectToJson(Object object) throws JsonProcessingException
	    
	 {
	       ObjectWriter objectWriter =  new ObjectMapper().writer().withDefaultPrettyPrinter();
	       String json =  objectWriter.writeValueAsString(object);
	       return json;
	    
	 }
	 
	 public static Object convertJsonToObject(String json, Class objectClass) throws IOException
	  {
	       Object object = new ObjectMapper().readValue(json,objectClass);
	       return object;
	   }
}
