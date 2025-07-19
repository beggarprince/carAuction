package aaa.pfa.carAuctionBackend.controller;

import aaa.pfa.carAuctionBackend.model.Car;
import aaa.pfa.carAuctionBackend.model.User;
import aaa.pfa.carAuctionBackend.security.JwtService;
import aaa.pfa.carAuctionBackend.services.CarService;
import aaa.pfa.carAuctionBackend.services.CarUploadDTO;
import aaa.pfa.carAuctionBackend.services.CarUploadResponseDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class RegisterCarController {

    private CarService carService;

    public RegisterCarController(
            ) {
    }

    @GetMapping("/uploadCar")
    public RedirectView register(){
        return new RedirectView("/uploadCar.html");
    }



    @PostMapping("/uploadCar")
    public ResponseEntity<CarUploadResponseDTO> uploadCar(
            @Valid
            @RequestBody CarUploadDTO dto
    ){

        Car newCar = carService.registerCar(dto);

        //I assume if the user is missing the request would not be valid and we exit
        CarUploadResponseDTO body = new CarUploadResponseDTO(
                newCar.getId(),
                newCar.getUser().username,
                newCar.getMake(),
                newCar.getModel(),
                newCar.getYear(),
                newCar.getPrice(),
                newCar.getDatePosted()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }


}
