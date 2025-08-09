package aaa.pfa.carAuctionBackend.controller;

import aaa.pfa.carAuctionBackend.DTO.*;
import aaa.pfa.carAuctionBackend.model.Car;
import aaa.pfa.carAuctionBackend.repository.CarRepository;
import aaa.pfa.carAuctionBackend.services.CarService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CarController {

    private final CarService carService;

    public CarController(
            CarService carService) {
        this.carService = carService;
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
        return carService.uploadCar(dto).map( carUploadResponseDTO ->
                ResponseEntity.ok(carUploadResponseDTO))
                .orElse(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .build()
                );
    }

    @PostMapping("/cars/uploadPhotos")
    public ResponseEntity<Void> uploadCarPics(
            @Valid
            @RequestBody CarPictureDTO dto
            ){

        if(carService.uploadCarPics(dto)){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    //Spaghetti
    @PostMapping("/cars/getList")
    public ResponseEntity<List<CarDTO>> getCars(
            @RequestParam String filter
    ){
        List<CarDTO> list = carService.getCars(filter);
        if(!list.isEmpty()) return ResponseEntity.ok(list);
        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();
    }


    //Dynamic query
    @PostMapping("/cars/advQuery")
    public ResponseEntity<List<CarDTO>> getCarsAdv(
            @Valid
            @RequestBody CarFilterDTO body
    ) {
        List<CarDTO> cars = carService.dynamicQuery(body);
        return ResponseEntity.ok(cars);
    }




}
