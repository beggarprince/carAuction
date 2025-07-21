package aaa.pfa.carAuctionBackend.services;

import aaa.pfa.carAuctionBackend.model.User;
import jakarta.validation.constraints.NotBlank;

import java.util.Date;

public record CarUploadDTO(
        @NotBlank String make,
        @NotBlank String model,
        @NotBlank int year,
        @NotBlank double price,
        @NotBlank Long id
) {

}
