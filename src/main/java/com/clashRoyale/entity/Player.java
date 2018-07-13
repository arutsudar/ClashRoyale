package com.clashRoyale.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"tag",
"name",
"trophies",
"rank"
})
public class Player {
	@JsonProperty("tag")
	private String tag;
	@JsonProperty("name")
	private String name;
	@JsonProperty("trophies")
	private Integer trophies;
	@JsonProperty("rank")
	private String rank;
	
}
