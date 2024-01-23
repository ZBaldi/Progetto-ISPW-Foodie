package com.foodie.model;

public class Standard implements Utente{
	private String username;
	private String nickname;
	public Standard(String username) {
		this.username=username;
	}
	@Override
	public String getUsername() {
		return username;
	}
	@Override
	public String getViewIniziale() {
		return "DispensaUtenteView.fxml";
	}
	public void setNickname(String nickname) {
		this.nickname=nickname;
	}
	public String getNickname() {
		return this.nickname;
	}
}
