package com.example.user_service.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
//ye decide krta hai ki user authenticated hai ya nhi 
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//ye current request se authentication k details nikalta hai jaise ip session id aur token k saath jodta hai 
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.user_service.service.UserService;
import com.example.user_service.util.JWTUtil;

import io.jsonwebtoken.Jwts;
//jwt token ko parse aur verify krne k liye 
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

 
@Component
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter{
	
	@Autowired
	private JWTUtil jwtService;
	@Autowired
	private UserService userService;
	
	//this method runs for every request here we extract jwt, verify and authenticate the user
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		final String authHeader = request.getHeader("Authorization");
		String jwt = null;
		String username = null;
		if(authHeader != null && authHeader.startsWith("Bearer ")) {
			jwt = authHeader.substring(7);
			username = jwtService.extractUserName(jwt);
		}
		if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			//found username but not authenticated till now* 
			UserDetails userDetails = userService.userDetailService().loadUserByUsername(username);
			//loads user details*
			System.out.println("  ->  "+userDetails.getUsername());
			if(jwtService.isTokenValid(jwt, userDetails)) {
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
				//this line defines user's identity like uska  username credentials aur role 
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				//adds extra info with request like ip session
				
				SecurityContextHolder.getContext().setAuthentication(authToken);
				//simply means that the user is authenticated  
				//on validating the jwt token it generate authentication token and set it in securityContextHolder*
			}
		}
		

		filterChain.doFilter(request, response);
		//request forwarded to next filter 
	}
	
}
