package books_management_system.service;

import books_management_system.dto.BookRequestDto;
import books_management_system.dto.BookResponseDto;
import books_management_system.entity.Book;

import java.util.List;

public interface BookService {

    public BookResponseDto addNewBook(BookRequestDto book);
    public List<BookResponseDto> getAllBooks();
    public BookResponseDto getBookById(Long id);
    public BookResponseDto updateBookById(Long id, BookRequestDto bookRequestDto);
    public BookResponseDto deleteBookById(Long id);
}
