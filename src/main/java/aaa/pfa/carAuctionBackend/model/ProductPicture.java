package aaa.pfa.carAuctionBackend.model;


import jakarta.persistence.Id;
import org.bson.types.Binary;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("ProductPicture")
public class ProductPicture {
    @Id
    private String id;
    private String owner;

    private Binary imageData;

    public ProductPicture(String ownerId,
                          Binary imageData) {
        super();
        this.owner = ownerId;
        this.imageData = imageData;
    }

    public String getId() {
        return id;
    }

    public Binary getImageData() {
        return imageData;
    }
}
