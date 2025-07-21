package aaa.pfa.carAuctionBackend.controller;


import aaa.pfa.carAuctionBackend.model.User;
import aaa.pfa.carAuctionBackend.DTO.UserRegisterDTO;
import aaa.pfa.carAuctionBackend.DTO.UserResponseDTO;
import aaa.pfa.carAuctionBackend.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
//@RequestMapping("/api/auth")
public class RegisterUserController {
    private UserService userService;


    public RegisterUserController(){};

    @GetMapping("/register")
    public RedirectView register() {
        return new RedirectView("/register");
    }

    @Autowired
    public RegisterUserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(
            @Valid
            @RequestBody
            UserRegisterDTO dto){

        User newUser = userService.register(dto);

        UserResponseDTO body = new UserResponseDTO(
                newUser.id,
                newUser.username,
                newUser.name,
                newUser.lastName,
                newUser.role
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }
}
