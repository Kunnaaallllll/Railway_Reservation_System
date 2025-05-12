package com.example.user_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.user_service.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService{
	//when any user logs in.. system verify the roles and permission and finds the username 
	//jab mujhe kisi user ka username milega tab main ise use krke uski details fetch kr skta hu 
	@Autowired
	private UserRepository userRepository;
	
	public UserDetailsService userDetailService() {
		// used for user authentication

		return new UserDetailsService() {
			@Override
			public UserDetails loadUserByUsername(String username) {
				return userRepository.findByUsername(username)
						.orElseThrow(() -> new UsernameNotFoundException("User not found"));
			}
		};
	}
}
