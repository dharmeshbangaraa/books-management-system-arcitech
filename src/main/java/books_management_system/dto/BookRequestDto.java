package books_management_system.dto;

import jakarta.validation.constraints.*;

import static books_management_system.constant.BMSConstant.*;

public record BookRequestDto(
        @NotEmpty(message = TITLE_CONSTRAINT)
        @Size(min = 1, max = 100, message = TITLE_LENGTH_CONSTRAINT)
        String title,

        @NotEmpty(message = AUTHOR_CONSTRAINT)
        @Size(min = 1, max = 100, message = AUTHOR_LENGTH_CONSTRAINT)
        String author,

        @NotEmpty(message = ISBN_CONSTRAINT)
        @Pattern(regexp = "^(97([89]))?\\d{9}(\\d|X)$", message = INVALID_ISBN)
        String isbn,

        @NotNull(message = PRICE_CONSTRAINT)
        @PositiveOrZero(message = VALID_PRICE_CONSTRAINT)
        Double price
) {
}
