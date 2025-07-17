package aaa.pfa.carAuctionBackend.model;


import jakarta.persistence.Id;
import org.bson.types.Binary;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@Document("ProductPicture")
public class ProductPicture {

    @Id
    @Field(targetType = FieldType.OBJECT_ID)
    private String id;

    private final String owner;

    private final Binary imageData;

    public ProductPicture(String owner,
                          Binary imageData) {
        super();
        this.owner = owner;
        this.imageData = imageData;
    }

    public String getId() {
        return id;
    }

    public String getOwner() {
        return owner;
    }

    public Binary getImageData() {
        return imageData;
    }

}
