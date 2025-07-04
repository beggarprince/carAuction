package aaa.pfa.carAuctionBackend.repository;

import aaa.pfa.carAuctionBackend.model.SaleItem;
import aaa.pfa.carAuctionBackend.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource
public interface SaleItemRepository
extends CrudRepository<SaleItem, Long> {

    @Override
    Optional<SaleItem> findById(Long id);
}
