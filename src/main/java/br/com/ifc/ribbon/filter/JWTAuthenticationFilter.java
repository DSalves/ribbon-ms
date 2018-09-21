package br.com.ifc.ribbon.filter;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;

/**
 * Filter JWT JWTAuthenticationFilter
 * 
 * @author thiago.colombo
 *
 */
@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {
	
	@Value("${auth.secret}")
	private String secret;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		String token = request.getHeader("Authorization");
		String username = null;
		Claims claims = null;

		if (token != null) {
			try {
				token = token.replace("Bearer", "").trim();
				Jws<Claims> claimsJws =  Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
		        claims = claimsJws.getBody();
		        username = claims.getSubject();    				

			} catch (IllegalArgumentException e) {
				throw new ServletException("An error occured during getting username from token", e);
			} catch (ExpiredJwtException e) {
				throw new ServletException("The token is expired and not valid anymore", e);
			} catch (SignatureException e) {
				throw new ServletException("Authentication Failed. Username or Password not valid.");
			}
		}

		if (username != null && claims != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			
			@SuppressWarnings("unchecked")
			ArrayList<String> roles = (ArrayList<String>)claims.get("Roles");
	        
	        ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();
	        if(roles != null) {
	            for (String role : roles) {
	            	SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(role);
	            	authorities.add(simpleGrantedAuthority);
	            }
	        }

	        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, "", authorities);
			authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(authentication);			
		}

		filterChain.doFilter(request, response);
	}
}
