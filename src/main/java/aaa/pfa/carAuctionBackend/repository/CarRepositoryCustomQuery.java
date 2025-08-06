package aaa.pfa.carAuctionBackend.repository;

import aaa.pfa.carAuctionBackend.model.Car;

import java.util.List;

public interface CarRepositoryCustomQuery {

       List<Car> findByDynamicQuery(String sql);

}
