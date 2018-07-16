package com.clashRoyale.game;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.clashRoyale.constants.constants;
import com.clashRoyale.services.service;

@RestController
public class restController {

	@Autowired
	constants con;

	@Autowired
	service serv;
	
	@Autowired
	RestTemplate restTemplate; 
	
	@Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
	
	@Bean
    public com.clashRoyale.services.service service() {
        return new service();
    }
	@Bean
    public com.clashRoyale.constants.constants constants() {
        return new constants();
    }
	
	@GetMapping(value="player/{playerTag}")
	private JSONObject player(@PathVariable("playerTag") String playerTag) {
	    String playerUrl = con.getPlayerUrl() + playerTag;
	    return serv.httpCall(playerUrl);
	}
	
	@GetMapping(value="clan/{clanTag}/war")
	private JSONObject clanWar(@PathVariable("clanTag") String clanTag) {
	    String clanUrl = con.getClanUrl() + clanTag + "/war";
	    JSONObject warStats = serv.httpCall(clanUrl);
	    return warStats;
	}
	
	@GetMapping(value="tournament/{tournamentTag}")
	private JSONObject tournament(@PathVariable("tournamentTag") String tournamentTag) {
	    String tournamentUrl = con.getClanUrl() + tournamentTag;    
	    return serv.httpCall(tournamentUrl);
	}
	
	@GetMapping(value="warwins/{clanTag}")
	private String[] warwins(@PathVariable("clanTag") String clanTag) {
		String playerUrl;
		JSONObject playerDetails;
		String clanUrl=con.getClanUrl() + clanTag;
		String[] memberList = new String[51];
		String[] memberTag = new String[51];
		long[] warWins = new long[51];
		long[] clanCardsCollected = new long[51];
		JSONObject resultObj = serv.httpCall(clanUrl);
		System.out.println(((JSONArray)resultObj.get("members")).size());
		int memberListSize = ((JSONArray)resultObj.get("members")).size();
		for (int i = 0; i < memberListSize; i++) {
			memberList[i] = (String) ((JSONObject)((JSONArray)resultObj.get("members")).get(i)).get("name");
			memberTag[i] = (String)((JSONObject)((JSONArray)resultObj.get("members")).get(i)).get("tag");
			
			playerUrl = con.getPlayerUrl() + memberTag[i];
		    playerDetails = serv.httpCall(playerUrl);
		    
		    clanCardsCollected[i] = (long) ((JSONObject)playerDetails.get("stats")).get("clanCardsCollected");
		    warWins[i] = (long) ((JSONObject)playerDetails.get("games")).get("warDayWins");
		    
			System.out.println(memberList[i]+"      "+warWins[i]+"      "+clanCardsCollected[i]+"\n");			
		}
		return memberList;
	}
	
	/*
	@GetMapping(value="playe/{playerTag}")
	private JSONObject playe(@PathVariable("playerTag") String playerTag) {
		
		Api api = new Api("http://api.royaleapi.com/", con.getToken()); // standard auth mode
		String version = api.getVersion();
		Profile profile = api.getProfile(ProfileRequest.builder("2PGGCJJL")
			    .keys(Arrays.asList("name", "clan", "tag"))
			    .excludes(Arrays.asList("battles"))
			    .build()
			);
	}
	*/
	
}
