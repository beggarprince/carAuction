package aaa.pfa.carAuctionBackend.services;


import aaa.pfa.carAuctionBackend.model.Car;
import aaa.pfa.carAuctionBackend.model.User;
import aaa.pfa.carAuctionBackend.repository.CarRepository;
import aaa.pfa.carAuctionBackend.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Car registerCar(
            CarUploadDTO dto
    ){

        Optional<User> user = userRepository.findById(dto.id());

            Car car = new Car(
                    dto.make(),
                    dto.model(),
                    dto.price(),
                    dto.year(),
                    user.orElse(null)
                    );

        return carRepository.save(car);
    }
}
