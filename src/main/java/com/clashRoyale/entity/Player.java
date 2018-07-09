package com.clashRoyale.entity;

public class Player {
	private String tag;
	private String name;
	private String trophies;
	private String rank;
	
	public Player(){}
	
	public Player(String name, String tag, String rank,String trophies){
		this.name = name;
		this.tag = tag;
		this.rank = rank;
		this.trophies = trophies;
	}
	
	
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTrophies() {
		return trophies;
	}
	public void setTrophies(String trophies) {
		this.trophies = trophies;
	}
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
}
