package aaa.pfa.carAuctionBackend.repository;

import aaa.pfa.carAuctionBackend.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface CarRepository extends JpaRepository<Car, Long> {


    @Query
    List<Car> findTop5ByOrderByDatePostedDesc();

    List<Car> findAllByPriceIsLessThan(Double price);

    List<Car> findAllByMileageGreaterThan(int mileage);


}
