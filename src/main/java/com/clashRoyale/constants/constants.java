package com.clashRoyale.constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class constants {

    @Value("${token}")
    public String token;

    @Value("${playerUrl}")
    public String playerUrl;

    @Value("${clanUrl}")
    public String clanUrl;

    @Value("${tournamentUrl}")
    public String tournamentUrl;
    
	public String getTournamentUrl() {
		return tournamentUrl;
	}

	public String getClanUrl() {
		return clanUrl;
	}

	public String getToken() {
		return token;
	}

	public String getPlayerUrl() {
		return playerUrl;
	}

}
