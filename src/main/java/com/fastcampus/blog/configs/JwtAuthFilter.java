package com.fastcampus.blog.configs;

import com.fastcampus.blog.services.CustomUserDetailService;
import com.fastcampus.blog.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Configuration
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    JwtService jwtService;

    @Autowired
    CustomUserDetailService customUserDetailService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        // Old code
        // String jwt = authHeader.substring("Bearer ".length());
        // new code
        String jwt = authHeader.substring(7);

        if (jwtService.isExpired(jwt)) {
            filterChain.doFilter(request, response);
            return;
        }

        String username = jwtService.extractUsername(jwt);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = customUserDetailService.loadUserByUsername(username);
            if (userDetails != null) {
                UsernamePasswordAuthenticationToken userPassAuth = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                userPassAuth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(userPassAuth);
            }
            filterChain.doFilter(request, response);
        }
    }
}
