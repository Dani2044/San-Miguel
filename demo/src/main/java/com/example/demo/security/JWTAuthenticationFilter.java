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
import java.util.Set;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenService jwtTokenService; // tu servicio para parsear/validar el JWT

    @Autowired
    private UserDetailsService userDetailsService;

    // Rutas que no deben pasar por este filtro
    private static final Set<String> PUBLIC_PATHS = Set.of(
        "/h2-console/",
        "/api/administrators/login",
        "/uploads/",
        "/image/",
        "/api/events/",
        "/api/galleries/",
        "/api/gallery"
    );

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = normalize(request.getRequestURI());
        // Ignorar GETs a recursos públicos
        if (HttpMethod.GET.matches(request.getMethod())) {
            for (String p : PUBLIC_PATHS) {
                if (path.startsWith(p)) return true;
            }
            // También ignorar GET genérico /api/* ya permitido
            if (path.matches("^/api/[^/]+$")) return true;
        }
        // H2 console cualquier método
        if (path.startsWith("/h2-console/")) return true;
        return false;
    }

    private String normalize(String uri) {
        if (uri == null || uri.isEmpty()) return "/";
        // Asegurar trailing slash donde corresponda para los startsWith
        if (uri.endsWith("/")) return uri;
        return uri;
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
            String username = jwtTokenService.extractUsername(token);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                if (jwtTokenService.isTokenValid(token, username)) {
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
