package aaa.pfa.carAuctionBackend.DTO;

import java.util.List;

public record CarFilterDTO(
        List<String> categories,
        List<String> makes,
        String model, // This is text, check this
        List<String> transmissions,
        List<String> drives,
        List<String> fuels,
        List<String> titleStatuses,
        List<String> paintColors,
        List<String> conditions,

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