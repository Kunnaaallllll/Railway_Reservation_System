package com.example.user_service.service;

import java.util.HashMap;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.user_service.dto.JwtAuthenticationResponse;
import com.example.user_service.dto.RefreshTokenRequest;
import com.example.user_service.dto.SignInRequest;
import com.example.user_service.dto.SignUpRequest;
import com.example.user_service.exceptions.UserAlreadyExistsException;
import com.example.user_service.model.Role;
import com.example.user_service.model.User;
import com.example.user_service.repository.UserRepository;
import com.example.user_service.util.JWTUtil;

import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements AuthServiceInterface{
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passEncoder;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JWTUtil jwtService;
	
	public String signup(SignUpRequest request) {
	    Optional<User> existingUser = userRepository.findByUsername(request.getUsername());

	    if (existingUser.isPresent()) {
	    	System.out.println(1);
	        throw new UserAlreadyExistsException("User Already Exists");
	    }

	    User pi = new User();
	    pi.setName(request.getName());
	    pi.setAge(request.getAge());
	    pi.setGender(request.getGender());
	    pi.setGmail(request.getGmail());
	    pi.setUsername(request.getUsername());
	    pi.setPassword(passEncoder.encode(request.getPassword()));
	    pi.setRole(Role.USER);

	    userRepository.save(pi);
	    return "User Registered Successfully";
	}
	
	public JwtAuthenticationResponse signin(SignInRequest signInRequest) {
		System.out.println(signInRequest.getPassword());
		
		
		Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getUsername()
				,signInRequest.getPassword()));
		
			var user = userRepository.findByUsername(signInRequest.getUsername())
					.orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));
			System.out.print(user.getUsername());
			var jwt = jwtService.generateToken(user,user.getRole().name());
			var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);
			
			JwtAuthenticationResponse jwtResponse = new JwtAuthenticationResponse();
			jwtResponse.setToken(jwt);
			jwtResponse.setRefreshToken(refreshToken);
			return jwtResponse;
		
	}
	
	public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
		String username = jwtService.extractUserName(refreshTokenRequest.getToken());//extracted username from claims  
		User user = userRepository.findByUsername(username).orElseThrow();
		if(jwtService.isTokenValid(refreshTokenRequest.getToken(), user)) {
			//token valid hai expire to nhi hua username user se match ho rha h 
			var jwt = jwtService.generateToken(user,user.getRole().name());
			JwtAuthenticationResponse jwtResponse = new JwtAuthenticationResponse();
			//ye dto bss token and refreshtoken bhejne k liye bna h
			jwtResponse.setToken(jwt);
			jwtResponse.setRefreshToken(refreshTokenRequest.getToken());
			return jwtResponse;
		}
		return null;
	}
}
