package aaa.pfa.carAuctionBackend.services;

public record UserResponseDTO(
        Long id,
        String username,
        String name,
        String lastName,
        String role
) {
}
