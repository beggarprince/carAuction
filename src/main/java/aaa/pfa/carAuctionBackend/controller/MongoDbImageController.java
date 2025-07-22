package aaa.pfa.carAuctionBackend.controller;


import aaa.pfa.carAuctionBackend.DTO.MultipleProductImageDTO;
import aaa.pfa.carAuctionBackend.DTO.ProductImageDTO;
import aaa.pfa.carAuctionBackend.model.ProductPicture;
import aaa.pfa.carAuctionBackend.repository.ProductPictureRepository;
import jakarta.validation.Valid;
import org.bson.types.Binary;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/images")
public class MongoDbImageController {
    private final ProductPictureRepository productPictureRepository;

    public MongoDbImageController(ProductPictureRepository productPictureRepository) {
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

    @GetMapping(value ="/{id}", produces= MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> getImage(
            @PathVariable("id") String id){
        ProductPicture img = productPictureRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        byte[] data = img.getImageData().getData();
        return ResponseEntity.ok()
                .body(data);
    }

    @PostMapping(value="/multiple", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, List<String>>>
    uploadMultiplePics(
            @RequestParam("file") List<MultipartFile> files,
            @RequestParam("owner") String owner
            ) throws IOException{

        System.out.println("At /api/images/multiple");

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

        return ResponseEntity.ok(Collections.singletonMap("ids", ids));
    }

}
