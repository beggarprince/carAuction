package aaa.pfa.carAuctionBackend.DTO;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;

public record CarFilterDTO(
        List<String> categories,
        List<String> make,
        String model,
        List<String> transmission,
        List<String> drive,
        List<String> fuel,
        List<String> titleStatus,
        List<String> paintColor,
        List<String> carCondition,

        Integer minPrice,
        Integer maxPrice,

        Integer minYear,
        Integer maxYear,

        Integer maxMileage
) {

    public static void validate(CarFilterDTO dto) {
        validateAlphanumeric(dto.model(), "model");
        validateAlphanumericList(dto.categories(), "categories");
        validateAlphanumericList(dto.make(), "make");
        validateAlphanumericList(dto.transmission(), "transmission");
        validateAlphanumericList(dto.drive(), "drive");
        validateAlphanumericList(dto.fuel(), "fuel");
        validateAlphanumericList(dto.titleStatus(), "titleStatus");
        validateAlphanumericList(dto.paintColor(), "paintColor");
        validateAlphanumericList(dto.carCondition(), "carCondition");
        System.out.println("Strings are alphanumeric");
    }

    private static void validateAlphanumeric(String value, String fieldName) {
        if(value == null) return;
        if ( !value.matches("^[a-zA-Z0-9]*\\s?[a-zA-Z0-9]*$")) {
            throw new IllegalArgumentException(fieldName + " contains invalid characters " + value);
        }
    }

    private static void validateAlphanumericList(List<String> values, String fieldName) {
        if (values != null) {
            for (String value : values) {
                validateAlphanumeric(value, fieldName);
            }
        }
    }
}