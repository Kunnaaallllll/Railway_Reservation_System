package com.example.user_service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//ye class csrf ko disable krne k liye use hoti hai 
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.user_service.model.Role;
import com.example.user_service.service.UserService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity//here we are using it because we are making our own custom security 
@RequiredArgsConstructor
public class SecurityConfiguration {
	@Autowired
    private JWTFilter jwtAuthenticationFilter;
	@Autowired
    private UserService userService;
    
    @Bean //http security :- to set rules for authentication aur authorization
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    	//securityFilterChain btata hai konsi request kaise handle hogi returns filterchain that is followed for all http request
        http.csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(request -> request
                .requestMatchers("/users/**").permitAll()
                .anyRequest().authenticated()
            )
            .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) 
            .authenticationProvider(authenticationProvider())
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); 
        return http.build();
    }
    
    
  //this entire thing is providing the user with authentication by username and encrypting its password
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
      //it verifies user through userDetailService 
        authenticationProvider.setUserDetailsService(userService.userDetailService());
        authenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder(10));
        return authenticationProvider;
    }
     
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    
    //it decides whether user to give access by verifying the user credentials 
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
        //it handles the login request

    }
}
