package aaa.pfa.carAuctionBackend.controller;


import aaa.pfa.carAuctionBackend.model.Car;
import aaa.pfa.carAuctionBackend.model.User;
import aaa.pfa.carAuctionBackend.repository.UserRepository;
import aaa.pfa.carAuctionBackend.DTO.UserDTO;
import aaa.pfa.carAuctionBackend.services.UserDetailsServiceService;
import aaa.pfa.carAuctionBackend.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        super();
        this.userService = userService;
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(
            @PathVariable Long id,
            @RequestBody UserDTO dto
    ) {

        return userService.updateUser(id, dto).map(userDTO -> ResponseEntity.ok(userDTO))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/me")
    public ResponseEntity<UserDTO> currentUser(Authentication authentication) {

        if (authentication == null) {
            System.out.println("Authentication is null");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        }

        return userService.currentUser(authentication).map(userDTO ->
                        ResponseEntity.ok(userDTO))
                .orElse(ResponseEntity.notFound().build());

    }


    @GetMapping("/getAllUsers")
    public ResponseEntity<List<UserDTO>> getAllUsers(
    ) {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/api/user/getUser={username}")
    public ResponseEntity<UserDTO> getSpecificUser(
            @PathVariable String username) {
               return  userService.getByUsername(username).map(userDTO ->
                               ResponseEntity.ok(userDTO))
                .orElse(ResponseEntity.notFound().build());
    }


}
