package aaa.pfa.carAuctionBackend.controller;

import aaa.pfa.carAuctionBackend.model.Car;
import aaa.pfa.carAuctionBackend.repository.CarRepository;
import aaa.pfa.carAuctionBackend.DTO.CarDTO;
import aaa.pfa.carAuctionBackend.services.CarService;
import aaa.pfa.carAuctionBackend.DTO.CarUploadDTO;
import aaa.pfa.carAuctionBackend.DTO.CarUploadResponseDTO;
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
    private final CarRepository carRepository;

    public CarController(
            CarService carService,
            CarRepository carRepository) {
        this.carService = carService;
        this.carRepository = carRepository;
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

    @PostMapping("/cars/getList")
    public ResponseEntity<List<CarDTO>> getCars(
            @RequestParam String filter
    ){
        List<Car> carList = new ArrayList<>();
        switch(filter){
            case "top5desc":
                carList = carRepository.findTop5ByOrderByDatePostedDesc();
                break;
            case "top5pricedesc":
                break;
                default:
                    carList  = carRepository.findAll();
                   // n.forEach(carList::add);
                    break;
        }

        List<CarDTO> carDTOList = new ArrayList<>();

        for(Car car: carList){
            CarDTO carDTO = new CarDTO(
                    car.getId(),
                    car.getMake(),
                    car.getModel(),
                    car.getYear(),
                    car.getPrice(),
                    car.getDatePosted().toString(),
                    car.getUser().username
            );
            carDTOList.add(carDTO);
        }

        return ResponseEntity.ok(carDTOList);
    }


}
