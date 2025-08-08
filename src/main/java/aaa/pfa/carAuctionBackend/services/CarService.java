package aaa.pfa.carAuctionBackend.services;


import aaa.pfa.carAuctionBackend.DTO.CarFilterDTO;
import aaa.pfa.carAuctionBackend.DTO.CarUploadDTO;
import aaa.pfa.carAuctionBackend.model.Car;
import aaa.pfa.carAuctionBackend.model.User;
import aaa.pfa.carAuctionBackend.repository.CarRepository;
import aaa.pfa.carAuctionBackend.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    CarRepository carRepository;
    UserRepository userRepository;

    public CarService(CarRepository carRepository,
                      UserRepository userRepository) {
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

//        Car car = new Car.Builder(
//                dto.make(),
//                dto.model(),
//                dto.year(),
//                dto.mileage(),
//                dto.price(),
//                user).transmission(dto.transmission())
//                .drive(dto.drive())
//                .fuel(dto.fuel())
//                .carType(dto.carType())
//                .title(dto.title())
//                .cylinder(dto.cylinder())
//                .color(dto.color())
//                .carCondition(dto.carCondition())
//                .description(dto.description())
//                .build();
            Car car =  makeCar(user, dto);

        user.addCarToUser(car);

        return carRepository.save(car);
    }

    public Car makeCar(User user, CarUploadDTO dto){
        Car car = new Car.Builder(
                dto.make(),
                dto.model(),
                dto.year(),
                dto.mileage(),
                dto.price(),
                user).transmission(dto.transmission())
                .drive(dto.drive())
                .fuel(dto.fuel())
                .carType(dto.carType())
                .title(dto.title())
                .cylinder(dto.cylinder())
                .color(dto.color())
                .carCondition(dto.carCondition())
                .description(dto.description())
                .build();

        return car;
    }



    public String FilteredListQuery(CarFilterDTO filters) {

        StringBuilder sql = new StringBuilder("SELECT * FROM cars WHERE 1=1");

        List<Object> params = new ArrayList<>();

        //category, make, model

        addFilter(sql, params, "category", filters.carType());

        addFilter(sql, params, "make", filters.make());

        //Model is text, not predefined

        if(filters.model() != null && !filters.model().trim().isEmpty()){
            sql.append(" AND model LIKE ?");
            params.add( filters.model().trim() );
        }

        addFilter(sql, params, "transmission", filters.transmission());
        addFilter(sql, params, "drive", filters.drive());
        addFilter(sql, params, "fuel", filters.fuel());
        addFilter(sql, params, "title", filters.titleStatus());
        addFilter(sql, params, "color", filters.paintColor());
        addFilter(sql, params, "car_condition", filters.carCondition());

        // Add price range filters
        if (filters.minPrice() != null) {
            sql.append(" AND price >= ?");
            params.add(filters.minPrice());
        }
        if (filters.maxPrice() != null) {
            sql.append(" AND price <= ?");
            params.add(filters.maxPrice());
        }

        // Add year range filters
        if (filters.minYear() != null) {
            sql.append(" AND year >= ?");
            params.add(filters.minYear());
        }
        if (filters.maxYear() != null) {
            sql.append(" AND year <= ?");
            params.add(filters.maxYear());
        }

        // Add mileage filter
        if (filters.maxMileage() != null) {
            sql.append(" AND mileage <= ?");
            params.add(filters.maxMileage());
        }


        for(int i = 0; i < params.size(); i++){
            System.out.println("Replacing parameter: " +params.get(i).toString());
            int index = sql.indexOf("?");
            if(index != -1){
                //numero
                if(params.get(i) instanceof Number){
                    sql.replace(index, index + 1,  params.get(i).toString() );

                }
                else {
                    sql.replace(index, index + 1, "'" + params.get(i).toString() + "'");
                }
            }
        }

        sql.append(" ORDER BY date_posted DESC");

        System.out.println(sql.toString());

        return sql.toString();

    }

    private void addFilter(
            StringBuilder sql,
            List<Object> params,
            String columnName,
            List<String> values
    ){
        if( values != null && !values.isEmpty()){

            sql.append(" AND ").append(columnName).append(" IN (");

                for(int i = 0; i < values.size(); i++){
                    sql.append("?");

                    if( i < values.size() -1){
                        sql.append(",");
                    }

                    params.add(values.get(i));
                }

                sql.append(")");
        }
    }

}
