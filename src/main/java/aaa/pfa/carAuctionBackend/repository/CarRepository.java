package aaa.pfa.carAuctionBackend.repository;

import aaa.pfa.carAuctionBackend.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface CarRepository extends JpaRepository<Car, Long> , CarRepositoryCustomQuery{


    @Query
    List<Car> findTop50ByOrderByDatePostedDesc(); //50 latest

    List<Car> findAllByPriceIsLessThan(Double price);

    List<Car> findAllByMileageGreaterThan(int mileage);

    List<Car> findAllByMileageLessThan(int mileageIsLessThan);

    List<Car> findAllByMileageBetween(int mileageAfter, int mileageBefore);

    List<Car> findAllByMake(String make);

    List<Car> findAllByColor(String color);

    List<Car> findAllByYearGreaterThan(int year);

    List<Car> findAllByYearBetween(int yearAfter, int yearBefore);

    List<Car> findAllByYearLessThan(int year);

    List<Car> findAllByPriceBetween(double priceAfter,
                                    double priceBefore);

    List<Car> findAllByPriceGreaterThan(double priceIsGreaterThan);

    //List<Car> findByCustomQuery(String sql);

}
