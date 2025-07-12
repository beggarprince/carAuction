package aaa.pfa.carAuctionBackend.security;

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

    static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    //generate signed token
    public String generateToken(String username){

        String token = Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + Expiration))
                .signWith(key)
                .compact();

        //System.out.println(token);
        if(token.startsWith(prefix)){
            System.out.println("This token has the prefix");
        }
        return token;

    }

    // Get a token from request Authorization header,
// verify the token, and get username
    public String getAuthUser(HttpServletRequest request) {
        String token = request.getHeader
                (HttpHeaders.AUTHORIZATION);
        if (token != null) {
            String user = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token.replace(prefix, ""))
                    .getBody()
                    .getSubject();
            if (user != null)
                return user;
        }
        return null;
    }


}