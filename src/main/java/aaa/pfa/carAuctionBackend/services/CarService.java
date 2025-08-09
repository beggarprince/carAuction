package aaa.pfa.carAuctionBackend.services;


import aaa.pfa.carAuctionBackend.DTO.*;
import aaa.pfa.carAuctionBackend.model.Car;
import aaa.pfa.carAuctionBackend.model.User;
import aaa.pfa.carAuctionBackend.repository.CarRepository;
import aaa.pfa.carAuctionBackend.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
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

            Car car =  makeCar(user, dto);

        user.addCarToUser(car);

        return carRepository.save(car);
    }

    public Car makeCar(User user, CarUploadDTO dto){

        return new Car.Builder(
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

    @Transactional
    public Optional<CarUploadResponseDTO> uploadCar(
            CarUploadDTO dto
    ){
        Car car = registerCar(dto);

        return Optional.of(createCarUploadResponseDTO(car));
    }

    private CarUploadResponseDTO createCarUploadResponseDTO(
            Car car
    ) {
        return new CarUploadResponseDTO(
                car.getId(),
                car.getUser().username,
                car.getMake(),
                car.getModel(),
                car.getYear(),
                car.getPrice(),
                car.getDatePosted()
        );
    }

    public Boolean uploadCarPics(CarPictureDTO dto){

        //Get car
        Car car = carRepository.findById(dto.carId()).orElse(null);

        if(car != null) {
            car.setPictureURL(dto.ids());
            carRepository.save(car);
            return true;
        }

        return false;
    }

    public List<CarDTO> getCars(String filter){
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
            CarDTO carDTO = createCarDTO(car);
            carDTOList.add(carDTO);
        }

        return carDTOList;
    }

    private static CarDTO createCarDTO(Car car) {
        return new CarDTO(
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
    }


    public List<CarDTO> dynamicQuery(
            CarFilterDTO body
    ){

        String query = FilteredListQuery(body);
        CarFilterDTO.validate(body);

        printCarFilterDTO(body);


        System.out.println(query);
        List<Car> retrievedCars = carRepository.findByDynamicQuery(query);
        //carRepository.findByCustomQuery(query);

        List<CarDTO> cars = new ArrayList<>();

        for(Car car: retrievedCars){
            CarDTO carDTO = createCarDTO(car);
            cars.add(carDTO);
        }

        return cars;
    }
    private void printCarFilterDTO(CarFilterDTO filter) {
        System.out.println("=== CarFilterDTO Values ===");

        if (filter.carType() != null && !filter.carType().isEmpty()) {
            System.out.println("Categories: " + filter.carType());
        }

        if (filter.make() != null && !filter.make().isEmpty()) {
            System.out.println("Make: " + filter.make());
        }

        if (filter.model() != null && !filter.model().trim().isEmpty()) {
            System.out.println("Model: " + filter.model());
        }

        if (filter.transmission() != null && !filter.transmission().isEmpty()) {
            System.out.println("Transmission: " + filter.transmission());
        }

        if (filter.drive() != null && !filter.drive().isEmpty()) {
            System.out.println("Drive: " + filter.drive());
        }

        if (filter.fuel() != null && !filter.fuel().isEmpty()) {
            System.out.println("Fuel: " + filter.fuel());
        }

        if (filter.titleStatus() != null && !filter.titleStatus().isEmpty()) {
            System.out.println("Title Status: " + filter.titleStatus());
        }

        if (filter.paintColor() != null && !filter.paintColor().isEmpty()) {
            System.out.println("Paint Color: " + filter.paintColor());
        }

        if (filter.carCondition() != null && !filter.carCondition().isEmpty()) {
            System.out.println("Car Condition: " + filter.carCondition());
        }

        if (filter.minPrice() != null) {
            System.out.println("Min Price: " + filter.minPrice());
        }

        if (filter.maxPrice() != null) {
            System.out.println("Max Price: " + filter.maxPrice());
        }

        if (filter.minYear() != null) {
            System.out.println("Min Year: " + filter.minYear());
        }

        if (filter.maxYear() != null) {
            System.out.println("Max Year: " + filter.maxYear());
        }

        if (filter.maxMileage() != null) {
            System.out.println("Max Mileage: " + filter.maxMileage());
        }

        System.out.println("========================");
    }


}
