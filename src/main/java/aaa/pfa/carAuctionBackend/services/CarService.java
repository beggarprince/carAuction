package aaa.pfa.carAuctionBackend.services;


import aaa.pfa.carAuctionBackend.model.Car;
import aaa.pfa.carAuctionBackend.repository.CarRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarService {
    @Autowired
    CarRepository carRepository;

    public CarService(){}

    public CarService(CarRepository carRepository){
        super();
        this.carRepository = carRepository;
    }

    @Transactional
    public Car registerCar(
            CarUploadDTO dto
    ){

        Car car = new Car(
                dto.make(),
                dto.model(),
                dto.price(),
                dto.year(),
                dto.user()
        );

        return carRepository.save(car);
    }
}
