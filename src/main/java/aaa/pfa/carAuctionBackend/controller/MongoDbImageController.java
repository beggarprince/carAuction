package aaa.pfa.carAuctionBackend.controller;


import aaa.pfa.carAuctionBackend.DTO.MultipleProductImageDTO;
import aaa.pfa.carAuctionBackend.DTO.ProductImageDTO;
import aaa.pfa.carAuctionBackend.model.ProductPicture;
import aaa.pfa.carAuctionBackend.repository.ProductPictureRepository;
import aaa.pfa.carAuctionBackend.services.MongoDbImageService;
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

    private final MongoDbImageService mongoDbImageService;
    public MongoDbImageController(MongoDbImageService mongoDbImageService) {
        this.mongoDbImageService = mongoDbImageService;
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

        return ResponseEntity.ok(
                mongoDbImageService.uploadImageToMongo(file, owner)
        );
    }

    @GetMapping(value ="/{id}", produces= MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> getImage(
            @PathVariable("id") String id){

        byte[] data = mongoDbImageService.getImage(id);

        return ResponseEntity.ok()
                .body(data);
    }

    @PostMapping(value="/multiple", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, List<String>>>
    uploadMultiplePics(
            @RequestParam("file") List<MultipartFile> files,
            @RequestParam("owner") String owner
            ) throws IOException{

        return ResponseEntity.ok(Collections.singletonMap(
                "ids",
                mongoDbImageService.uploadMultiplePics(files, owner)
                ));
    }

}
