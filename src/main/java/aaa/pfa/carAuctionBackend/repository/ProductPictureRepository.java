package aaa.pfa.carAuctionBackend.repository;

import aaa.pfa.carAuctionBackend.model.ProductPicture;
import org.bson.types.Binary;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ProductPictureRepository
extends MongoRepository<ProductPicture,String> {


    ProductPicture getById(String id);

    List<ProductPicture> getByOwner(String owner);

    public long count();
}
