package br.com.videxium.videxiumapi.filter;

import br.com.videxium.videxiumapi.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        String jwt = extractJwtToken(httpServletRequest);

        if (jwt != null ) {
            String username = jwtUtil.getUsenameFromToken(jwt);
            if (shouldProcessAuthentication(username)) {
                processAuthentication(httpServletRequest, jwt, username);
            }
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private void processAuthentication(HttpServletRequest httpServletRequest, String jwt, String username) {
        if (jwtUtil.validateToken(jwt)) {
            UserDetails userDetails = createUserDetailFromToken(jwt, username);
            setAutheticationInContext(httpServletRequest, userDetails);
        }
    }

    private void setAutheticationInContext(HttpServletRequest httpServletRequest, UserDetails userDetails) {

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities());

            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }

    private UserDetails createUserDetailFromToken(String jwt, String username) {
        String role = jwtUtil.getRoleFromToken(jwt);
        return User.builder()
                .username(username)
                .password("")
                .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_".concat(role))))
                .build();
    }

    private boolean shouldProcessAuthentication(String username) {
        return username != null && SecurityContextHolder.getContext().getAuthentication() == null;
    }

    private String extractJwtToken(HttpServletRequest httpServletRequest) {

        final String authorizationHeader = httpServletRequest.getHeader("Authorization");
        final String requestURI = httpServletRequest.getRequestURI();

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        } else if ((requestURI.contains("/api/files/video") || requestURI.contains("/api/files/image"))
                && httpServletRequest.getParameter("token") != null) {
            return httpServletRequest.getParameter("token");
        }

        return null;
    }

}
