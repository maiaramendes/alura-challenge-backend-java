package br.alura.config.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Slf4j
public class JWTFilter extends OncePerRequestFilter {

    private static final String LOG_TAG = "[JWTFilter]";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        log.info("{} - Retrieving token...", LOG_TAG);
        final String token =  request.getHeader(JWTCreator.HEADER_AUTHORIZATION);

        try {
            if (token != null && !token.isEmpty()) {
                log.info("{} - Token is not empty, validating...", LOG_TAG);

                final JWTObject tokenObject = JWTCreator.create(token, "sYLzehcWaYf6JDWVM3UjlmgVsbeCxaj", "Bearer");
                final List<SimpleGrantedAuthority> authorities = authorities(tokenObject.getRoles());
                final UsernamePasswordAuthenticationToken userToken =
                        new UsernamePasswordAuthenticationToken(tokenObject.getSubject(), null, authorities);
                SecurityContextHolder.getContext().setAuthentication(userToken);
            } else {
                log.info("{} - Token is empty...", LOG_TAG);
                SecurityContextHolder.clearContext();
            }
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException e) {
            log.error(e.getMessage());
            response.setStatus(HttpStatus.FORBIDDEN.value());
        }
    }
    private List<SimpleGrantedAuthority> authorities(List<String> roles){
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .toList();
    }
}
