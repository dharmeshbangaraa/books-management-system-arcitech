package books_management_system.service.serviceImpl;

import books_management_system.dto.BookRequestDto;
import books_management_system.dto.BookResponseDto;
import books_management_system.entity.Book;
import books_management_system.exception.BMSException;
import books_management_system.mapper.BookMapper;
import books_management_system.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static books_management_system.constant.BMSConstant.BOOK_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    private Book book;
    private BookRequestDto bookRequestDto;
    private BookResponseDto bookResponseDto;

    @BeforeEach
    public void setUp() {
        bookRequestDto = new BookRequestDto("Test Title", "Test Author", "1234567890", 100.0);
        book = BookMapper.toBook(bookRequestDto);
        book.setBookId(1L);

        bookResponseDto = BookMapper.toDto(book);
    }

    @Test
    public void testAddNewBook() {
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        BookResponseDto response = bookService.addNewBook(bookRequestDto);
        assertNotNull(response);
        assertEquals(book.getTitle(), response.title());

        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    public void testGetAllBooks() {
        when(bookRepository.findAll()).thenReturn(Arrays.asList(book));

        List<BookResponseDto> books = bookService.getAllBooks();
        assertNotNull(books);
        assertEquals(1, books.size());
        assertEquals(book.getTitle(), books.get(0).title());

        verify(bookRepository, times(1)).findAll();
    }

    @Test
    public void testGetBookById() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        BookResponseDto response = bookService.getBookById(1L);
        assertNotNull(response);
        assertEquals(book.getTitle(), response.title());

        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetBookByIdNotFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        BMSException thrown = assertThrows(BMSException.class, () -> {
            bookService.getBookById(1L);
        });
        assertEquals(BOOK_NOT_FOUND, thrown.getMessage());

        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    public void testUpdateBookById() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        BookResponseDto response = bookService.updateBookById(1L, bookRequestDto);
        assertNotNull(response);
        assertEquals(book.getTitle(), response.title());

        verify(bookRepository, times(1)).findById(1L);
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    public void testUpdateBookByIdNotFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        BMSException thrown = assertThrows(BMSException.class, () -> {
            bookService.updateBookById(1L, bookRequestDto);
        });
        assertEquals(BOOK_NOT_FOUND, thrown.getMessage());

        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    public void testDeleteBookById() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        BookResponseDto response = bookService.deleteBookById(1L);
        assertNotNull(response);
        assertEquals(book.getTitle(), response.title());

        verify(bookRepository, times(1)).findById(1L);
        verify(bookRepository, times(1)).delete(any(Book.class));
    }

    @Test
    public void testDeleteBookByIdNotFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        BMSException thrown = assertThrows(BMSException.class, () -> {
            bookService.deleteBookById(1L);
        });
        assertEquals(BOOK_NOT_FOUND, thrown.getMessage());

        verify(bookRepository, times(1)).findById(1L);
    }
}