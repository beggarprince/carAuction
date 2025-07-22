package aaa.pfa.carAuctionBackend.DTO;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record MultipleProductImageDTO(
        List<MultipartFile> file,
        String owner
) {
}
