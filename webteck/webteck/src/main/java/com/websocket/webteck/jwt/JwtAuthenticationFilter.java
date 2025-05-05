package com.websocket.webteck.jwt;

import java.io.IOException;
import java.util.List;

import org.hibernate.query.sqm.sql.internal.EmbeddableValuedPathInterpretation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.websocket.webteck.model.Role;
import com.websocket.webteck.repository.TokenBlacklist;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private TokenBlacklist tokenBlacklist;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException{
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            try {
                if (tokenBlacklist.existsByToken(token)) {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.setContentType("text/plain");
                    response.getWriter().write("Blaclisted");
                    return;
                }
                String email = jwtUtil.extractEmail(token);
                Role role = jwtUtil.extractRole(token);
                if (email != null && jwtUtil.validateToken(email, token)) {
                    List<SimpleGrantedAuthority> authority = List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, null, authority);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (ExpiredJwtException e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("text/plain");
                response.getWriter().write("Blaclisted");
                return;
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("text/plain");
                response.getWriter().write("Blaclisted");
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}
