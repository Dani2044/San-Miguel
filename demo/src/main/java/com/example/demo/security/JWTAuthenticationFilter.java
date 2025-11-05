package com.example.demo.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JWTGenerator jwtGenerator; // use existing JWTGenerator for parse/validate

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        if (path == null || path.isEmpty()) return false;
        
        String method = request.getMethod();
        
        // H2 console cualquier método
        if (path.startsWith("/h2-console")) return true;
        
        // Login endpoint cualquier método
        if (path.equals("/api/administrators/login") || path.startsWith("/api/administrators/login/")) {
            return true;
        }
        
        // Para GET requests, permitir acceso público a:
        if (HttpMethod.GET.matches(method)) {
            // Uploads estáticos (archivos de imagen)
            if (path.startsWith("/uploads/")) return true;
            
            // Endpoints de imagen
            if (path.startsWith("/image/")) return true;
            
            // Todos los GET a /api/* son públicos según SecurityConfig
            if (path.startsWith("/api/")) return true;
        }
        
        return false;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        // Si no hay Bearer, no intentamos autenticar: continuar
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);

        try {
            String username = jwtGenerator.extractUsername(token);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                if (jwtGenerator.validateToken(token)) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken auth =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(auth);
                } else {
                    // Token presente pero inválido: limpiamos y seguimos (rutas públicas continuarán; rutas protegidas caerán en 401 más adelante)
                    SecurityContextHolder.clearContext();
                }
            }
        } catch (Exception ex) {
            // Cualquier problema parseando el token: no autenticamos y dejamos continuar
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }
}
