package aaa.pfa.carAuctionBackend.DTO;

import java.util.Date;

public record CarUploadResponseDTO(
        Long id,
        String username,
        String make,
        String model,
        int year,
        double price,
        Date datePosted
) {
}
