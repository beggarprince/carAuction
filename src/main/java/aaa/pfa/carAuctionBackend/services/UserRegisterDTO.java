package aaa.pfa.carAuctionBackend.services;

import jakarta.validation.constraints.NotBlank;

public record UserRegisterDTO(
        @NotBlank String username,
        @NotBlank String password,
        @NotBlank String name,
        @NotBlank String lastName
) {

}
