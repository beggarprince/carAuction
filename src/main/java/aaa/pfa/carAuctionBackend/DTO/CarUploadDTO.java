package aaa.pfa.carAuctionBackend.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CarUploadDTO(
        @NotBlank String make,
        @NotBlank String model,
        @Positive int year,
        @Positive int mileage,
        @Positive double price,
        @NotNull Long id,
        String transmission,
        String drive,
        String fuel,
        String type,
        String title,
        String cylinder,
        String color,
        String condition,
        String description
) {

}
