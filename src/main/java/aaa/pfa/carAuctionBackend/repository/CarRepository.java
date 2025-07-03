package aaa.pfa.carAuctionBackend.repository;

import aaa.pfa.carAuctionBackend.model.Car;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface CarRepository extends CrudRepository<Car, Long> {
}
