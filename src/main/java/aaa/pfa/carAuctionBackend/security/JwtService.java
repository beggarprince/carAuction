package aaa.pfa.carAuctionBackend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JwtService {
    static final long Expiration = 86400000;

    static final String prefix = "Bearer";

    final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    //generate signed token
    public String generateToken(String username){

        String token = Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + Expiration))
                .signWith(key)
                .compact();

        return token;

    }


    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    public String extractUsername(Claims claim) {

        return claim.getSubject();
    }

    public boolean isValid(Claims claim, UserDetails userDetails) {
        final String username = extractUsername(claim);

        return username.equals(userDetails.getUsername()) && !isExpired(claim);
    }


    public boolean isExpired(Claims claim) {
        Date exp = claim.getExpiration();
        return exp.before(new Date());
    }

}