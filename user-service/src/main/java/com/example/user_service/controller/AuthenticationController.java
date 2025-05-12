package com.example.user_service.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.user_service.dto.JwtAuthenticationResponse;
import com.example.user_service.dto.RefreshTokenRequest;
import com.example.user_service.dto.SignInRequest;
import com.example.user_service.dto.SignUpRequest;
import com.example.user_service.model.User;
import com.example.user_service.service.AuthServiceInterface;
import com.example.user_service.service.AuthenticationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class AuthenticationController {
	
	@Autowired
	private AuthServiceInterface authenticationService;
	
	@PostMapping("/signup")
	public ResponseEntity<Map<String, String>> signup(@RequestBody SignUpRequest passengerInfoDTO) {
	    String message = authenticationService.signup(passengerInfoDTO);

	    Map<String, String> response = new HashMap<>();
	    response.put("message", message); // ✅ Convert response to JSON format

	    return ResponseEntity.ok(response); // ✅ Fix: Send JSON response
	}
	
	@PostMapping("/signin")
	public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SignInRequest signInRequest){
		return ResponseEntity.ok(authenticationService.signin(signInRequest));
	}
	
	@PostMapping("/refresh")
	public ResponseEntity<JwtAuthenticationResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest){
		return ResponseEntity.ok(authenticationService.refreshToken(refreshTokenRequest));
	}
}
