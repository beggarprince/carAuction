package aaa.pfa.carAuctionBackend.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.List;

@RestController
public class HomeController {

    @GetMapping("/home")
    public RedirectView home(){
        return new RedirectView("/home.html");
    }


    @GetMapping("/home-data")
    public HomeResponse homePage() {
        List<String> strings = new ArrayList<>();
        strings.add("You");
        strings.add("are");
        strings.add("a bitch");

        String url = "/images/temp.png";
        return new HomeResponse(url, strings);
    }

    //dto
    public static class HomeResponse{
        public String imgUrl;
        public List<String> strings;

        public HomeResponse(String url, List<String> string){
            this.imgUrl = url;
            this.strings = string;
        }

    }
}