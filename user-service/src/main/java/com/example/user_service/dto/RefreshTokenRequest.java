package com.example.user_service.dto;

import lombok.Getter;
import lombok.Setter;


public class RefreshTokenRequest {
	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
}
