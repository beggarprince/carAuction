package aaa.pfa.carAuctionBackend.controller;

import aaa.pfa.carAuctionBackend.model.User;
import aaa.pfa.carAuctionBackend.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterCarController {
    @Autowired
    public RegisterCarController(JwtService jwtService, AuthenticationManager authMan) {
    }

    @GetMapping("/register")
    public String register(){
        return "register";
    }

    @PostMapping("/register")
    public String carRegistration(@ModelAttribute User user){
        System.out.println(user.toString());
        return "register";
    }


}
