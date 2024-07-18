package books_management_system.service.serviceImpl;

import books_management_system.dto.BookRequestDto;
import books_management_system.dto.BookResponseDto;
import books_management_system.entity.Book;
import books_management_system.exception.BMSException;
import books_management_system.mapper.BookMapper;
import books_management_system.repository.BookRepository;
import books_management_system.service.BookService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static books_management_system.constant.BMSConstant.BOOK_NOT_FOUND;

@Service
@AllArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public BookResponseDto addNewBook(BookRequestDto bookRequestDto) {
        log.info("Adding a new book with title: {}", bookRequestDto.title());
        Book book = BookMapper.toBook(bookRequestDto);
        bookRepository.save(book);
        log.info("Book with title '{}' has been added successfully", book.getTitle());
        return BookMapper.toDto(book);
    }

    @Override
    public List<BookResponseDto> getAllBooks() {
        log.info("Retrieving all books");
        List<BookResponseDto> bookResponseDtoList = new ArrayList<>();
        for (Book book : bookRepository.findAll()) {
            bookResponseDtoList.add(BookMapper.toDto(book));
        }
        log.info("Retrieved {} books", bookResponseDtoList.size());
        return bookResponseDtoList;
    }

    @Override
    public BookResponseDto getBookById(Long id) {
        log.info("Getting book details for ID: {}", id);
        Book book = bookRepository.findById(id).orElseThrow(() -> {
            log.error("Book not found with ID: {}", id);
            return new BMSException(BOOK_NOT_FOUND);
        });
        log.info("Book with ID {} retrieved successfully", id);
        return BookMapper.toDto(book);
    }

    @Override
    public BookResponseDto updateBookById(Long id, BookRequestDto bookRequestDto) {
        log.info("Updating book with ID: {}", id);
        Book book = bookRepository.findById(id).orElseThrow(() -> {
            log.error("Book not found with ID: {}", id);
            return new BMSException(BOOK_NOT_FOUND);
        });
        book.setAuthor(bookRequestDto.author());
        book.setIsbn(bookRequestDto.isbn());
        book.setTitle(bookRequestDto.title());
        book.setPrice(bookRequestDto.price());
        book.setPublishedDate(LocalDate.now());
        bookRepository.save(book);
        log.info("Book with ID {} updated successfully", id);
        return BookMapper.toDto(book);
    }

    @Override
    public BookResponseDto deleteBookById(Long id) {
        log.info("Deleting book with ID: {}", id);
        Book book = bookRepository.findById(id).orElseThrow(() -> {
            log.error("Book not found with ID: {}", id);
            return new BMSException(BOOK_NOT_FOUND);
        });
        bookRepository.delete(book);
        log.info("Book with ID {} has been deleted successfully", id);
        return BookMapper.toDto(book);
    }
}