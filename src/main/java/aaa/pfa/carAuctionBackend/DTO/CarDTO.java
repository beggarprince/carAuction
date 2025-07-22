package aaa.pfa.carAuctionBackend.DTO;

import java.util.List;

public record CarDTO(
         Long id,
         String make,
         String model,
         int year,
         int mileage,
         double price,
         String datePosted,
         String owner,
         Long user_id,
         List<String> picUrl
) {

}
