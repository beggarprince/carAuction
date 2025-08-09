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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Collection;
import java.util.List;

@RestController
//@RequestMapping("/api/auth")
public class LoginController {
    private final JwtService jwtService;

    private final AuthenticationManager authMan;

    @GetMapping("/login")
    public RedirectView login() {
        return new RedirectView("/login.html");
    }

    public LoginController(JwtService jwtService,
                           AuthenticationManager authenticationManager) {
        this.jwtService = jwtService;
        this.authMan = authenticationManager;
    }


    @PostMapping("/login")
    public ResponseEntity<?> getToken(
            @RequestBody
            AccountCredentials credentials) {

        String jwts = jwtService.generateToken(credentials.username());


        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION,
                        "Bearer" + jwts)
                .header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS,
                        "Authorization")
                .build();
    }

}