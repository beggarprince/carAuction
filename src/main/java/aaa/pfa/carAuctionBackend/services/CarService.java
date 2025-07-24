package aaa.pfa.carAuctionBackend.services;


import aaa.pfa.carAuctionBackend.DTO.CarUploadDTO;
import aaa.pfa.carAuctionBackend.model.Car;
import aaa.pfa.carAuctionBackend.model.User;
import aaa.pfa.carAuctionBackend.repository.CarRepository;
import aaa.pfa.carAuctionBackend.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CarService {

    CarRepository carRepository;
    UserRepository userRepository;

    public CarService(CarRepository carRepository,
                      UserRepository userRepository){
        super();
        this.carRepository = carRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Car registerCar(@Valid CarUploadDTO dto) {
        Optional<User> optionalUser = userRepository.findById(dto.id());

        User user = optionalUser.orElseThrow(() ->
                new EntityNotFoundException("User not found with ID: " + dto.id()));

        System.out.println("User id is:" + user.id);

        Car car = new Car(
                dto.make(),
                dto.model(),
                dto.year(),
                dto.mileage(),
                dto.price(),
                user
        );

        user.addCarToUser(car);

        return carRepository.save(car);
    }
}
