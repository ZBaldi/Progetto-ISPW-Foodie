package com.foodie.model;

public class Chef implements Utente{
	private String username;
	private String nickname;
	public Chef(String username) {
		this.username=username;
	}
	@Override
	public String getUsername() {
		return username;
	}
	@Override
	public String getViewIniziale() {
		return null;
	}
	public void setNickname(String nickname) {
		this.nickname=nickname;
	}
	public String getNickname() {
		return this.nickname;
	}
}
