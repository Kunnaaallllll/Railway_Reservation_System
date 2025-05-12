package com.example.api_gateway.config;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
 
import com.example.api_gateway.filter.JwtAuthenticationFilter;
 
@Configuration
public class GatewayConfig {
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	@Bean
	public RouteLocator customRouteLoader(RouteLocatorBuilder builder) {
		return builder.routes()
				.route("user-service", r -> r
						.path("/users/**")
						.uri("http://localhost:8083/"))
				.route("client-service", r -> r
						.path("/client/getByTrainName/**")
						.uri("http://localhost:8081/"))
				.route("admin-service", r -> r
						.path("/admin/**")
						.filters(f ->f.filter(jwtAuthenticationFilter.apply(new JwtAuthenticationFilter.Config("ADMIN"))))
						.uri("http://localhost:8082/"))
				.route("client-service", r -> r
						.path("/client/**")
						.filters(f ->f.filter(jwtAuthenticationFilter.apply(new JwtAuthenticationFilter.Config("USER"))))
						.uri("http://localhost:8081/"))
				
				.build();	 
	}
} 