package aaa.pfa.carAuctionBackend.repository;

import aaa.pfa.carAuctionBackend.model.Car;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;


public class CarRepositoryCustomQueryImpl implements CarRepositoryCustomQuery{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Car> findByDynamicQuery(String query) {
        return entityManager.createNativeQuery(query, Car.class).getResultList();
    }

}
