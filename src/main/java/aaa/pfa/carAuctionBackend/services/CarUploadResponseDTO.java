package aaa.pfa.carAuctionBackend.services;

import java.util.Date;

public record CarUploadResponseDTO(
        Long id,
        String username,
        String make,
        String model,
        int year,
        int price,
        Date datePosted
) {
}
