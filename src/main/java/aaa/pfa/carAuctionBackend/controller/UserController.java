package aaa.pfa.carAuctionBackend.controller;


import aaa.pfa.carAuctionBackend.model.User;
import aaa.pfa.carAuctionBackend.services.UserDTO;
import aaa.pfa.carAuctionBackend.services.UserDetailsServiceService;
import aaa.pfa.carAuctionBackend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserDetailsServiceService userDetailsServiceService;

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

    @GetMapping("/me")
    public ResponseEntity<UserDTO> currentUser( Authentication authentication){

        if(authentication == null){
            System.out.println("Authentication is null");

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String username = authentication.getName();

        User user= userDetailsServiceService.returnByUsername(username);

        return ResponseEntity.ok(
                new UserDTO(user.username, user.name, user.lastName)
        );
    }

}
