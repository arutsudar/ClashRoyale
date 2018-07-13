package com.clashRoyale.game;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.clashRoyale.constants.constants;

@RestController
public class restController {

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
	
	@GetMapping(value="player/{playerTag}")
	private JSONObject player(@PathVariable("playerTag") String playerTag) {
		
		String token = con.getToken();
	    String playerUrl = con.getPlayerUrl() + playerTag;
	    
	    try
		{
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", "bearer "+token);
			headers.add("Content-Type", "application/json");
			HttpEntity<String> request = new HttpEntity<String>(headers);
			ResponseEntity<String> response = restTemplate.exchange(playerUrl, HttpMethod.GET, request, String.class);
			
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

	/*
	@GetMapping(value="player/{playerTag}/{anyValue}")
	private Object player(@PathVariable("playerTag") String playerTag, @PathVariable("anyValue") String anyValue) {
	    System.out.println(anyValue);
	    try {
		    JSONObject resultObj = player(playerTag);
		    if(resultObj.containsKey(anyValue))
		    	return resultObj.get(anyValue);
		    else
		    	return null;	
	    }
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
			return null;
		}
	}
	*/
	
	@GetMapping(value="clan/{clanTag}/war")
	private JSONObject clanWar(@PathVariable("clanTag") String clanTag) {
		
		String token = con.getToken();
	    String clanUrl = con.getClanUrl() + clanTag + "/war";
	    
	    try
		{
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", "bearer "+token);
			headers.add("Content-Type", "application/json");
			HttpEntity<String> request = new HttpEntity<String>(headers);
			ResponseEntity<String> response = restTemplate.exchange(clanUrl, HttpMethod.GET, request, String.class);
			
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
	
}
