package br.alura.config.security;

import br.alura.enums.Role;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class JWTCreator {

    public static final String HEADER_AUTHORIZATION = "Authorization";

    public static final String ROLES_AUTHORITIES = "authorities";

    public static String create(final JWTObject jwtObject, final String key, final String prefix) {
        final String token = Jwts.builder()
                .setSubject(jwtObject.getSubject())
                .setIssuedAt(jwtObject.getIssuedAt())
                .setExpiration(jwtObject.getExpiration())
                .claim(ROLES_AUTHORITIES, Role.checkRoles(jwtObject.getRoles()))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
        return prefix + " " + token;
    }

    public static JWTObject create(String token, final String key, final String prefix)
            throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException {
        final JWTObject object = new JWTObject();
        token = token.replace(prefix, "");
        Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
        object.setSubject(claims.getSubject());
        object.setExpiration(claims.getExpiration());
        object.setIssuedAt(claims.getIssuedAt());
        object.setRoles((List) claims.get(ROLES_AUTHORITIES));
        return object;

    }
}
