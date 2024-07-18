package books_management_system.dto;

import java.time.LocalDate;

public record BookResponseDto(
        Long id,
        String title,
        String author,
        LocalDate publishedDate,
        String isbn,
        Double price
) {

}
