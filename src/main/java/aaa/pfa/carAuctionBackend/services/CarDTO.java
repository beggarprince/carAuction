package aaa.pfa.carAuctionBackend.services;

public record CarDTO(
         Long id,
         String make,
         String model,
         int year,
         double price,
         String datePosted,
         String owner
) {

}
