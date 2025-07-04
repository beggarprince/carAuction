package aaa.pfa.carAuctionBackend.controller;

import aaa.pfa.carAuctionBackend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/items")
public class SaleItemController {
    @Autowired
    private UserService userService;

    public SaleItemController(){};

    public SaleItemController(UserService userService){
        this.userService = userService;
    }

}
