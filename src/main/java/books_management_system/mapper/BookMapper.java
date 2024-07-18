package books_management_system.mapper;

import books_management_system.dto.BookRequestDto;
import books_management_system.dto.BookResponseDto;
import books_management_system.entity.Book;

import java.time.LocalDate;

public class BookMapper {

    public static Book toBook(BookRequestDto bookRequestDto) {

        return Book
                .builder()
                .title(bookRequestDto.title())
                .author(bookRequestDto.author())
                .publishedDate(LocalDate.now())
                .isbn(bookRequestDto.isbn())
                .price(bookRequestDto.price())
                .build();
    }

    public static BookResponseDto toDto(Book book) {

        return new BookResponseDto(
                book.getBookId(),
                book.getTitle(),
                book.getAuthor(),
                book.getPublishedDate(),
                book.getIsbn(),
                book.getPrice()
        );
    }

}
