package com.easyexam.security.jwt;

import com.easyexam.security.service.UserPrinciple;
import io.jsonwebtoken.*;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class JwtUtils {

	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

	@Value("${easyexam.jwtSecret}")
	private String jwtSecret;

	@Value("${easyexam.jwtExpiration}")
	private int jwtExpiration;

	public String generateJwtToken(Authentication authentication) {

		UserPrinciple userPrincipal = (UserPrinciple) authentication.getPrincipal();

		return Jwts.builder().setSubject((userPrincipal.getUsername())).setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtExpiration))
				.signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
	}

	public String getUserNameFromJwtToken(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}

	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		}
		catch (SignatureException e) {
			logger.error("Invalid JWT signature -> Message: {} ", e);
		}
		catch (MalformedJwtException e) {
			logger.error("Invalid JWT token -> Message: {}", e);
		}
		catch (ExpiredJwtException e) {
			logger.error("Expired JWT token -> Message: {}", e);
		}
		catch (UnsupportedJwtException e) {
			logger.error("Unsupported JWT token -> Message: {}", e);
		}
		catch (IllegalArgumentException e) {
			logger.error("JWT claims string is empty -> Message: {}", e);
		}

		return false;
	}

}
