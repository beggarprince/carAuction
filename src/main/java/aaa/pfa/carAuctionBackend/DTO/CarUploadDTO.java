package aaa.pfa.carAuctionBackend.DTO;

import jakarta.validation.constraints.NotBlank;

public record CarUploadDTO(
        @NotBlank String make,
        @NotBlank String model,
        @NotBlank int year,
        @NotBlank int mileage,
        @NotBlank double price,
        @NotBlank Long id
) {

}
