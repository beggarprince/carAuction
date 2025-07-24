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

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final UserDetailsServiceService userDetailsServiceService;

    private final UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository, UserDetailsServiceService udss)
    {
        super();
        this.userService = userService;
        this.userRepository = userRepository;
        this.userDetailsServiceService = udss;
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

        System.out.println(user.name);

        //Since this is just the user registration we have no cars to send
        List<Long> c = new ArrayList<Long>();

        return ResponseEntity.ok(
                new UserDTO(user.username,
                        user.name,
                        user.lastName,
                        user.id,
                        c
                )

        );
    }


    @GetMapping("/getAllUsers")
    public ResponseEntity<List<UserDTO>> getAllUsers(
    ){
        List<UserDTO> dtoList = new ArrayList<>();

        Iterable<User> userList = userRepository.findAll();

        for(User user: userList){
            UserDTO dto = new UserDTO(
                    user.username,
                    user.name,
                    user.lastName,
                    user.id,
                    user.carIds()
            );
            dtoList.add(dto);
        }

        return ResponseEntity.ok(dtoList);
    }



}
