package com.example.api_gateway.filter;
 
import java.nio.charset.StandardCharsets;
import java.security.Key;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.ws.rs.core.HttpHeaders;
import reactor.core.publisher.Mono;
@Component
//- yeh configurable filter hai jo request process karega
public class JwtAuthenticationFilter extends AbstractGatewayFilterFactory<JwtAuthenticationFilter.Config>{
	@Value("${jwt.secret}")
	private String secretKey;
	public JwtAuthenticationFilter() {
		super(Config.class);
	}
	@Override
	public GatewayFilter apply(Config config) {
		return (exchange, chain) -> {
			String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

			if(authHeader == null || !authHeader.startsWith("Bearer ")) {
				return onError(exchange, "Authorization header is missing or invalid", HttpStatus.UNAUTHORIZED);
			}
			String token = authHeader.substring(7);
			System.out.println(token);
			Claims claims = validateToken(token);
			System.out.println(claims);
			if(claims==null) {
				return onError(exchange, "Invalid token", HttpStatus.UNAUTHORIZED);
			}
			String role = claims.get("Role", String.class);
			System.out.println(role);
			System.out.println(config.getRole());
			if(role==null || !role.equals(config.getRole())) {
				return onError(exchange, "Unauthorized access", HttpStatus.FORBIDDEN);
			}
			return chain.filter(exchange);
		};
	}
	private Claims validateToken(String token) {
		try {
			byte[] keyBytes = Decoders.BASE64.decode(secretKey);
			Key signInkey = Keys.hmacShaKeyFor(keyBytes);
			return Jwts.parserBuilder().setSigningKey(signInkey).build()
					.parseClaimsJws(token)
					.getBody();
		}catch(Exception e) {
			return null;
		}
	}
	private Mono<Void> onError(ServerWebExchange exchange, String message, HttpStatus status){
		exchange.getResponse().setStatusCode(status);
		DataBufferFactory bufferFactory = exchange.getResponse().bufferFactory();
		//DataBufferFactory response body generate karne ka mechanism hai
		byte[] bytes = message.getBytes(StandardCharsets.UTF_8);
		return exchange.getResponse().writeWith(Mono.just(bufferFactory.wrap(bytes)));	
	}
	public static class Config{
		private String role;
		public Config(String role) {
			this.role=role;
		}
		public String getRole() {
			return role;
		}
		public void setRole(String role) {
			this.role=role;
		}
	}
}