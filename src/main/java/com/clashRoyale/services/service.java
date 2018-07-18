package com.clashRoyale.services;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.clashRoyale.constants.constants;


public class service {


	@Autowired
	RestTemplate restTemplate; 

	@Autowired
	constants con;

	@Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
	
	@Bean
    public com.clashRoyale.constants.constants constants() {
        return new constants();
    }

	public JSONObject httpCall(String url) {
	
		String token = con.getToken();
	    try
		{
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", "bearer "+token);
			headers.add("Content-Type", "application/json");
			HttpEntity<String> request = new HttpEntity<String>(headers);
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
			
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(response.getBody());
			JSONObject resultObj = (JSONObject) obj;
			
			return resultObj;
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
			return null;
		}
	}

	public JSONArray httpCallReturningAnArray(String url) {
	
		String token = con.getToken();
	    try
		{
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", "bearer "+token);
			headers.add("Content-Type", "application/json");
			HttpEntity<String> request = new HttpEntity<String>(headers);
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
			
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(response.getBody());
			JSONArray resultObj = (JSONArray) obj;
			
			return resultObj;
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
			return null;
		}
	}
    
}
