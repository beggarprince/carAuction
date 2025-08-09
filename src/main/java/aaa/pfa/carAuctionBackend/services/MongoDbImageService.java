package aaa.pfa.carAuctionBackend.services;

import aaa.pfa.carAuctionBackend.DTO.ProductImageDTO;
import aaa.pfa.carAuctionBackend.model.ProductPicture;
import aaa.pfa.carAuctionBackend.repository.ProductPictureRepository;
import org.bson.types.Binary;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class MongoDbImageService {

    private final ProductPictureRepository productPictureRepository;

    public MongoDbImageService(ProductPictureRepository productPictureRepository) {
        this.productPictureRepository = productPictureRepository;
    }

    public ProductImageDTO uploadImageToMongo(
            MultipartFile pic,
            String owner
    ) throws IOException {

        if (pic == null || pic.isEmpty()) {
            throw new IllegalArgumentException("Image file cannot be null or empty");
        }

        ProductPicture picture = productPictureRepository.save(
                new ProductPicture(owner,
                        new Binary(pic.getBytes())));

        return new ProductImageDTO(
                picture.getId(),
                picture.getOwner());
    }

    public byte[] getImage(String id) {
        ProductPicture img = productPictureRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        byte[] data = img.getImageData().getData();
        if (data == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Image data is null");
        }
        return data;
    }

    public List<String> uploadMultiplePics(List<MultipartFile> files, String owner) throws IOException {
        if (files.size() > 6) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot upload more than 6 images.");
        }

        List<String> ids = new ArrayList<>();

        for(MultipartFile f: files){
            ProductPicture p = new ProductPicture(
                    owner,
                    new Binary(f.getBytes())
            );
            ProductPicture saved = productPictureRepository.save(p);
            ids.add(saved.getId());
        }
        return ids;
    }
}
