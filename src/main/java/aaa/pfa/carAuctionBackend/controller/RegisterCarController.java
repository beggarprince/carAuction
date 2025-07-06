package aaa.pfa.carAuctionBackend.controller;

import aaa.pfa.carAuctionBackend.model.User;
import aaa.pfa.carAuctionBackend.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class RegisterCarController {
    @Autowired
    public RegisterCarController(JwtService jwtService, AuthenticationManager authMan) {
    }

    @GetMapping("/register")
    public RedirectView register(){
        return new RedirectView("/register.html");
    }

    @PostMapping("/register")
    public String carRegistration(@ModelAttribute User user){
        System.out.println(user.toString());
        return "register";
    }


}
