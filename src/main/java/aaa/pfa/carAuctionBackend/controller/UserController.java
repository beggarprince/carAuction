package aaa.pfa.carAuctionBackend.controller;


import aaa.pfa.carAuctionBackend.model.User;
import aaa.pfa.carAuctionBackend.services.UserDTO;
import aaa.pfa.carAuctionBackend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    public UserController(){};

    public UserController(UserService userService)
    {
        super();
        this.userService = userService;
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> updateUser(
            @PathVariable Long id,
            @RequestBody UserDTO dto
            ){
        User updateUser = userService.updateUser(id, dto);
        return ResponseEntity.ok(updateUser);
    }
}
