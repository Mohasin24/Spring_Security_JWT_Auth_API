package com.authentication.configuration;

import com.authentication.util.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;

    public JWTAuthenticationFilter(UserDetailsService userDetailsService){
        this.userDetailsService=userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwtToken = getJwtToken(request);

        if(jwtToken != null && JwtUtils.validateToken(jwtToken)){

            // Get username from jwtToken
            String username = JwtUtils.getUsernameFromToken(jwtToken);

            // Get userdetails using userdetails service by using loadUserByUsername
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // Create authentication token

            var authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());

            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // Set authentication token to security context
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        }

        // pass request and response to next filter
        filterChain.doFilter(request,response);
    }

    private String getJwtToken(HttpServletRequest request){

        // Extract Authentication Headers
        String authHeader = request.getHeaders(HttpHeaders.AUTHORIZATION).toString();

        // Bearer <Jwt Token>
        if(!authHeader.isEmpty() && authHeader.startsWith("Bearer")){
            return authHeader.substring(7);
        }

        return null;
    }
}
