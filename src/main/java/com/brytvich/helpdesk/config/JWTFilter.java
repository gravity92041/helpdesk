package com.brytvich.helpdesk.config;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.brytvich.helpdesk.security.JWTUtil;

import com.brytvich.helpdesk.services.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;

@Component
public class JWTFilter extends OncePerRequestFilter {
    private final JWTUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;
    @Autowired
    public JWTFilter(JWTUtil jwtUtil, CustomUserDetailsService customUserDetailsService) {
        this.jwtUtil = jwtUtil;
        this.customUserDetailsService = customUserDetailsService;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && !authHeader.isBlank() && authHeader.startsWith("Bearer")){
            String jwt = authHeader.substring(7);
            if (jwt.isBlank()){
                response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                        "Invalid JWT TOKEN in Bearer Header");
            }else{
                try {
                    Map<String, String> claims = jwtUtil.validateTokenAndRetrieveClaim(jwt);
                    String username = claims.get("username");
                    String role = claims.get("role");


                    UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken authenticationToken=
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    userDetails.getPassword(),
                                    userDetails.getAuthorities()
                            );
                    if (SecurityContextHolder.getContext().getAuthentication()==null){
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }
                }catch (JWTVerificationException e){
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST,"INVALID JWT TOKEN");
                }
            }
        }
        filterChain.doFilter(request,response);
    }
}
