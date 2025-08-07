package aaa.pfa.carAuctionBackend.DTO;

import java.util.List;

public record CarFilterDTO(
        List<String> categories,
        List<String> make,
        String model, // This is text, check this
        List<String> transmission,
        List<String> drive,
        List<String> fuel,
        List<String> titleStatus,
        List<String> paintColor,
        List<String> carCondition,

        // Price range filters (optional)
        Integer minPrice,
        Integer maxPrice,

        // Year range filters (optional)
        Integer minYear,
        Integer maxYear,

        // Mileage range filters (optional)
        Integer maxMileage
) {
}