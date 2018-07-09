package com.clashRoyale.game;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.clashRoyale.constants.constants;
import com.fasterxml.jackson.databind.JsonSerializer;

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
	private String player(@PathVariable("playerTag") String playerTag) {
		
		String token = con.getToken();
	    String playerUrl = con.getPlayerUrl() + playerTag;
		
	    try
		{
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", "bearer "+token);
			headers.add("Content-Type", "application/json");
			HttpEntity<String> request = new HttpEntity<String>(headers);
			ResponseEntity<String> response = restTemplate.exchange(playerUrl, HttpMethod.GET, request, String.class);
			/*
			JSONObject json = (JSONObject) ;        
		    double coolness = json.getDouble( "coolness" );
		    int altitude = json.getInt( "altitude" );
		    JSONObject pilot = json.getJSONObject("pilot");
		    String firstName = pilot.getString("firstName");
		    String lastName = pilot.getString("lastName");

		    System.out.println( "Coolness: " + coolness );
		    System.out.println( "Altitude: " + altitude );
		    System.out.println( "Pilot: " + lastName );
			*/
			return response.getBody();
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
			return ""+HttpStatus.BAD_REQUEST;
		}
	
	}

}
