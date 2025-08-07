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

    @PostMapping("/cars/uploadPhotos")
    public ResponseEntity<Void> uploadCarPics(
            @Valid
            @RequestBody CarPictureDTO dto
            ){

        System.out.println("At /cars/uploadPhotos");
        Car car = carRepository.findById(dto.carId()).orElse(null);
        if(car != null) {
            car.setPictureURL(dto.ids());
            carRepository.save(car);
        }else{
            System.out.println("Error updating car with pictures");
        }

        return ResponseEntity.ok().build();
    }

    //Spaghetti
    @PostMapping("/cars/getList")
    public ResponseEntity<List<CarDTO>> getCars(
            @RequestParam String filter
    ){
        List<Car> carList = new ArrayList<>();

        if (filter.equals("top5desc")) {
            carList = carRepository.findTop50ByOrderByDatePostedDesc();
        }
        else if(filter.contains("price")) {
         if (filter.contains("min_Price") && filter.contains("max_Price")) {

                int minPrice = Integer.parseInt(filter.split("min_Price=")[1].split("&")[0]);

                int maxPrice = Integer.parseInt(filter.split("max_Price=")[1]);

                carList = carRepository.findAllByPriceBetween(minPrice, maxPrice);
            } else if (filter.contains("min_Price")) {
                int price = Integer.parseInt(filter.split("min_Price=")[1]);
                carList = carRepository.findAllByPriceIsLessThan((double) price);
            } else if (filter.contains("maxPrice")) {
                int price = Integer.parseInt(filter.split("max_Price=")[1]);
                carList = carRepository.findAllByPriceGreaterThan(price);
            }
        }

        //Would probably be better to have lessthan and greaterthan inside the url regardless and check for ""
        else if(filter.contains("findAllByYear")){

            boolean lessThan = filter.contains("LessThan");

            boolean greaterThan = filter.contains("greaterThan");

            if(lessThan && greaterThan){
                int minYear = Integer.parseInt(filter.split("LessThan=")[1].split("0")[0]) ;
                int maxYear = Integer.parseInt(filter.split("GreaterThan=")[1]);
                carList = carRepository.findAllByYearBetween(minYear, maxYear);
            }

            else if(lessThan){
                int minYear = Integer.parseInt(filter.split("LessThan=")[1].split("0")[0]) ;
                carList = carRepository.findAllByYearLessThan(minYear);
            }

            else if(greaterThan){
                int maxYear = Integer.parseInt(filter.split("GreaterThan=")[1]);
                carList = carRepository.findAllByYearGreaterThan(maxYear);
            }

            }

        else if(filter.contains("color")){
            String color = filter.split("color=")[1];
            carList = carRepository.findAllByColor(color);
        }

        else if(filter.contains("make")){
            String make = filter.split("make=")[1];
            carList = carRepository.findAllByMake(make);
        }

        else if(filter.contains("mileage")){
            boolean lessThan = filter.contains("lessThan");
            boolean greaterThan = filter.contains("greaterThan");

            if(lessThan && greaterThan){
                int lessThanMileage = Integer.parseInt(filter.split("mileageLessThan=")[1].split("0")[0]);
                int greaterThanMileage = Integer.parseInt(filter.split("mileageGreaterThan=")[1]);
                carList = carRepository.findAllByMileageBetween(lessThanMileage, greaterThanMileage);
            }
            else if(lessThan){
                int mileage = Integer.parseInt(filter.split("=")[1]);
                carList = carRepository.findAllByYearLessThan(mileage);
            }
            else if(greaterThan){
                int mileage = Integer.parseInt(filter.split("=")[1]);
                carList = carRepository.findAllByMileageGreaterThan(mileage);
            }

        }

        else {
            System.out.println("Default case");
            carList = carRepository.findAll();
            // n.forEach(carList::add);
        }


        List<CarDTO> carDTOList = new ArrayList<>();

        for(Car car: carList){
            CarDTO carDTO = new CarDTO(
                    car.getId(),
                    car.getMake(),
                    car.getModel(),
                    car.getYear(),
                    car.getMileage(),
                    car.getPrice(),
                    car.getDatePosted().toString(),
                    car.getUser().username,
                    car.getUser().id,
                    car.getPicturesURL(),
                    car.getDescription()
            );
            carDTOList.add(carDTO);
        }

        return ResponseEntity.ok(carDTOList);
    }


    //Advanced query
    @PostMapping("/cars/advQuery")
    public ResponseEntity<List<CarDTO>> getCarsAdv(
            @Valid
            @RequestBody CarFilterDTO body
    ) {
        String query = carService.FilteredListQuery(body);

        //TODO test this i fucking hate jpa
        List<Car> listOfCars = carRepository.findByDynamicQuery(query);
                //carRepository.findByCustomQuery(query);


        return ResponseEntity.ok().build();
    }


}
