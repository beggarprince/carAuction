package aaa.pfa.carAuctionBackend.controller;

import aaa.pfa.carAuctionBackend.model.AccountCredentials;
import aaa.pfa.carAuctionBackend.security.JwtService;
import aaa.pfa.carAuctionBackend.services.UserDetailsServiceService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
public class LoginController {
    private final JwtService jwtService;

    private final AuthenticationManager authMan;

    public LoginController(JwtService jwtService,
                           AuthenticationManager authenticationManager)
    {
        this.jwtService = jwtService;
        this.authMan = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<?> getToken(@RequestBody
                                      AccountCredentials credentials){
        UsernamePasswordAuthenticationToken creds = new
                UsernamePasswordAuthenticationToken(credentials.username(),
                credentials.password());

        Authentication auth = authMan.authenticate(creds);


        String jwts = jwtService.generateToken(credentials.username());


        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION,
                        "Bearer"+jwts)
                .header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS,
                        "Authorization")
                .build();
    }
}