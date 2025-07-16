package aaa.pfa.carAuctionBackend.controller;


import aaa.pfa.carAuctionBackend.model.ProductImageDTO;
import aaa.pfa.carAuctionBackend.model.ProductPicture;
import aaa.pfa.carAuctionBackend.repository.ProductPictureRepository;
import org.bson.types.Binary;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import java.awt.*;
import java.io.IOException;

@RestController
@RequestMapping("/api/images")
public class UploadImageController {
    private final ProductPictureRepository productPictureRepository;

    public UploadImageController(ProductPictureRepository productPictureRepository) {
        this.productPictureRepository = productPictureRepository;
    }

    @GetMapping("/upload")
    public RedirectView uploadImage(){
        return new RedirectView("/picUpload");
    }

    @PostMapping
    public ResponseEntity<ProductImageDTO> upload(
            @RequestParam("file") MultipartFile file,
            @RequestParam("owner") String owner
    )    throws IOException {
        ProductPicture productPicture =
        productPictureRepository.save(new ProductPicture(owner,
                new Binary(file.getBytes())));

        return ResponseEntity.ok(new ProductImageDTO(productPicture.getId(),
                productPicture.getOwner()));
    }
}
