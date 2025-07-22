package aaa.pfa.carAuctionBackend.DTO;

import java.util.List;

public record CarPictureDTO(
        Long carId,
        List<String> ids
) {
}
