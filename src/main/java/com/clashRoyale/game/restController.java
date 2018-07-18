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

	@GetMapping(value="clan/{clanTag}")
	private JSONObject clan(@PathVariable("clanTag") String clanTag) {
	    String clanUrl = con.getClanUrl() + clanTag;
	    JSONObject clanStats = serv.httpCall(clanUrl);
	    return clanStats;
	}
	
	@GetMapping(value="clan/{clanTag}/war")
	private JSONObject clanWar(@PathVariable("clanTag") String clanTag) {
	    String warUrl = con.getClanUrl() + clanTag + "/war";
	    JSONObject warStats = serv.httpCall(warUrl);
	    return warStats;
	}

	@GetMapping(value="clan/{clanTag}/warlog")
	private JSONArray clanWarLog(@PathVariable("clanTag") String clanTag) {
	    String warLogUrl = con.getClanUrl() + clanTag + "/warlog";
	    JSONArray warLogStats = serv.httpCallReturningAnArray(warLogUrl);
	    return warLogStats;
	}

	@GetMapping(value="clan/{clanTag}/warStreak")
	private JSONObject clanWarStreak(@PathVariable("clanTag") String clanTag) {
		JSONObject warStreak = new JSONObject();
		JSONArray warLogStats = clanWarLog(clanTag);
	    for(Object i:warLogStats) {
	    	JSONObject x = (JSONObject) i;
	    	JSONArray participants = (JSONArray) x.get("participants");
	    	for(Object j:participants) {
		    	JSONObject y = (JSONObject) j;
		    	String personTag = (String) y.get("tag");
		    	if(warStreak.containsKey(personTag)) {
		    		JSONObject personWarStreak = (JSONObject) warStreak.get(personTag);	    		
		    		if((int)personWarStreak.get("streakFlag") == 0) {
			    		long winStreakTemp = (long)personWarStreak.get("winStreak");
			    		winStreakTemp += (long) y.get("wins");
			    		personWarStreak.replace("winStreak", winStreakTemp);
			    		if((long) y.get("wins") == 0)
			    			personWarStreak.replace("streakFlag",1);
			    		warStreak.replace(personTag, personWarStreak);
		    		}
		    		else
		    			continue;
		    	}
		    	else {
					JSONObject personWarStreak = new JSONObject();
					personWarStreak.put("wins", y.get("wins"));
					personWarStreak.put("cardsEarned", y.get("cardsEarned"));
					personWarStreak.put("name", y.get("name"));
					personWarStreak.put("battlesPlayed", y.get("battlesPlayed"));
		    		if((long) y.get("wins") == 0) {
		    			personWarStreak.put("streakFlag", 1);
		    			personWarStreak.put("winStreak", (long)y.get("wins"));
		    		}
		    		else {
		    			personWarStreak.put("streakFlag", 0);
		    			personWarStreak.put("winStreak", (long)y.get("wins"));
		    		}
		    		warStreak.put(personTag, personWarStreak);
		    	}
	    	}	    	
	    }
	    return warStreak;
	}
	
	@GetMapping(value="tournament/{tournamentTag}")
	private JSONObject tournament(@PathVariable("tournamentTag") String tournamentTag) {
	    String tournamentUrl = con.getTournamentUrl() + tournamentTag;    
	    return serv.httpCall(tournamentUrl);
	}

	@GetMapping(value="tournamentPlayersDecks/{tournamentTag}")
	private JSONObject tournamentPlayersDecks(@PathVariable("tournamentTag") String tournamentTag) {
		JSONObject tournamentPlayersDecks = new JSONObject();
		JSONArray tournamentPlayersDecksArr = new JSONArray();
		String playerTag = null;
		String playerName = null;
		String playerDeckLink = null;
	    JSONObject tournamentObj = tournament(tournamentTag);
		long currentNoOfPlayers = (long)tournamentObj.get("currentPlayers");
		for (int i = 0; i < currentNoOfPlayers; i++) {
			playerTag = (String) ((JSONObject)((JSONArray)tournamentObj.get("members")).get(i)).get("tag");
			playerName = (String) ((JSONObject)((JSONArray)tournamentObj.get("members")).get(i)).get("name");
			JSONObject playerObj = player(playerTag);
			playerDeckLink = (String) playerObj.get("deckLink");
			JSONObject playerDeckLinkTemp = new JSONObject();
			playerDeckLinkTemp.put("Name", playerName);
			playerDeckLinkTemp.put("Tag", playerTag);
			playerDeckLinkTemp.put("DeckLink", playerDeckLink);
			tournamentPlayersDecksArr.add(playerDeckLinkTemp);
			
		}
		tournamentPlayersDecks.put("tournamentPlayersDecks", tournamentPlayersDecksArr);
		return tournamentPlayersDecks;
	}	
	
	@GetMapping(value="warwins/{clanTag}")
	private JSONObject warwins(@PathVariable("clanTag") String clanTag) {
		JSONObject playerDetails;
		JSONObject warWinsJson = new JSONObject();
		JSONArray warWinsArr = new JSONArray();
		String[] memberName = new String[51];
		String[] memberTag = new String[51];
		long[] warWins = new long[51];
		long[] clanCardsCollected = new long[51];
		JSONObject clanObj = clan(clanTag);
		long memberCount = (long) clanObj.get("memberCount");
		for (int i = 0; i < memberCount; i++) {
			memberName[i] = (String) ((JSONObject)((JSONArray)clanObj.get("members")).get(i)).get("name");
			memberTag[i] = (String)((JSONObject)((JSONArray)clanObj.get("members")).get(i)).get("tag");
			
		    playerDetails = player(memberTag[i]);
		    
		    clanCardsCollected[i] = (long) ((JSONObject)playerDetails.get("stats")).get("clanCardsCollected");
		    warWins[i] = (long) ((JSONObject)playerDetails.get("games")).get("warDayWins");
		    	
			JSONObject warWinsJsonTemp = new JSONObject();
			warWinsJsonTemp.put("Name",memberName[i]); 
			warWinsJsonTemp.put("Tag",memberTag[i]); 
			warWinsJsonTemp.put("WarWins",warWins[i]); 
			warWinsJsonTemp.put("ClanCardsCollected",clanCardsCollected[i]); 
			warWinsArr.add(warWinsJsonTemp);
		}
		warWinsJson.put("WarWins",warWinsArr);
		return warWinsJson;
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
