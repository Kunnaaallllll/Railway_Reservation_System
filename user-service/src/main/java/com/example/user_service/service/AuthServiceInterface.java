package com.example.user_service.service;

import com.example.user_service.dto.JwtAuthenticationResponse;
import com.example.user_service.dto.RefreshTokenRequest;
import com.example.user_service.dto.SignInRequest;
import com.example.user_service.dto.SignUpRequest;

public interface AuthServiceInterface {

	String signup(SignUpRequest request);
	JwtAuthenticationResponse signin(SignInRequest signInRequest);
	JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
