package aaa.pfa.carAuctionBackend.DTO;

import java.util.List;

public record PictureDTO(
        Long id, // User or car
        List<String> pictureURLs
) {
}
