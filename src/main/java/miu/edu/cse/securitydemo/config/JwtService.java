package miu.edu.cse.securitydemo.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Service
public class JwtService {

    @Value("${jwt.secretKey}")
    private String SECRET;
    public String generateToken(UserDetails userDetails) {
        return Jwts
                .builder()
                .claim("authorities", populateAuthorities(userDetails.getAuthorities()))
                .issuedAt(new Date())
                .issuer("cse.edu.miu")
                .subject(userDetails.getUsername())
                .expiration(new Date((new Date().getTime() + 1000 * 60 * 60 * 24)))
                .signWith(signInKey())
                .compact();
    }

    private String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
    }

    private Key signInKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET));
    }

    public Claims getClaimsFromToken(String token) {
        return Jwts
                .parser()
                .verifyWith((SecretKey) signInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
