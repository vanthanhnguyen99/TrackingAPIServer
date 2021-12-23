package com.vanth.jwt.configs;

import java.util.Date;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Service
public class JwtTokenProvider {
	// this is contain jwt
	private final String JWT_SECRET = "vanth";
	//time expire
	private final long JWT_EXPIRATION = 24 * 60 * 60 * 1000;
	
	//Create JWT from user
	
	public String generateToken(CustomUserDetail userDetails) {
		Date now = new Date(System.currentTimeMillis());
		Date expireDate = new Date(now.getTime() +JWT_EXPIRATION);
		
		return Jwts.builder()
					.setSubject(userDetails.getUsername())
					.setIssuedAt(now)
					.setExpiration(expireDate)
					.signWith(SignatureAlgorithm.HS256, JWT_SECRET)
					.compact();
	}
	
	// get username from jwt
	public String getUsernameFromJWT(String token) {
		Claims claims = Jwts.parser()
						.setSigningKey(JWT_SECRET)
						.parseClaimsJws(token)
						.getBody();
		return claims.getSubject();
	}
	// validate token
	public boolean validateToken(String authToken) {
		try {
//			System.out.println("token: " + authToken);
			if (authToken.isEmpty()) throw new IllegalArgumentException();
			Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(authToken);
			return true;
		} catch (MalformedJwtException e) {
			System.out.print("Invaled JWT token");
		} catch(ExpiredJwtException e) {
			System.out.println("Expired JWT token");
		} catch(UnsupportedJwtException e) {
			System.out.println("Unsupported JWT token");
		} catch(IllegalArgumentException e) {
			System.out.println("JWT claims string is empty");
		}
		catch (SignatureException e)
		{
			System.out.println("JWT not trusted");
		}
		return false;
	}
}
