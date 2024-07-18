package books_management_system.controller;

import books_management_system.constant.BMSConstant;
import books_management_system.dto.BookRequestDto;
import books_management_system.dto.BookResponseDto;
import books_management_system.dto.ResponseDto;
import books_management_system.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class BookControllerTest {

    @InjectMocks
    private BookController bookController;

    @Mock
    private BookService bookService;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void testAddBook_Success() {
        BookRequestDto bookRequestDto = new BookRequestDto(
                "Test Book",
                "Test Author",
                "9291282492012",
                19.34
        );

        BookResponseDto bookResponseDto = new BookResponseDto(
                1L,
                "Test Book",
                "Test Author",
                LocalDate.now(),
                "9291282492012",
                19.34
        );

        when(bookService.addNewBook(any(BookRequestDto.class))).thenReturn(bookResponseDto);

        ResponseEntity<ResponseDto<BookResponseDto>> response = bookController.addBook(bookRequestDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(String.format(BMSConstant.BOOK_ADDED_SUCCESSFULLY, bookResponseDto.title()), response.getBody().getMessage());
    }

    @Test
    void testAddBook_Failure() {
        BookRequestDto bookRequestDto = new BookRequestDto(
                "Test Book",
                "Test Author",
                "9291282492012",
                19.34
        );

        when(bookService.addNewBook(any(BookRequestDto.class))).thenReturn(null);

        ResponseEntity<ResponseDto<BookResponseDto>> response = bookController.addBook(bookRequestDto);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(BMSConstant.SOMETHING_WENT_WRONG, response.getBody().getMessage());
    }

    @Test
    void testGetBooks() {
        BookResponseDto bookResponseDto = new BookResponseDto(
                1L,
                "Test Book",
                "Test Author",
                LocalDate.now(),
                "9291282492012",
                19.34
        );
        List<BookResponseDto> bookList = Collections.singletonList(bookResponseDto);

        when(bookService.getAllBooks()).thenReturn(bookList);

        ResponseEntity<ResponseDto<List<BookResponseDto>>> response = bookController.getBooks();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(BMSConstant.LIST_ALL_BOOKS, response.getBody().getMessage());
    }

    @Test
    void testGetBook_Success() {

        BookResponseDto bookResponseDto = new BookResponseDto(
                1L,
                "Test Book",
                "Test Author",
                LocalDate.now(),
                "9291282492012",
                19.34
        );

        when(bookService.getBookById(anyLong())).thenReturn(bookResponseDto);

        ResponseEntity<ResponseDto<BookResponseDto>> response = bookController.getBook(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(String.format(BMSConstant.BOOK_RETRIEVED_SUCCESSFULLY, bookResponseDto.title()), response.getBody().getMessage());
    }

    @Test
    void testGetBook_Failure() {
        when(bookService.getBookById(anyLong())).thenReturn(null);

        ResponseEntity<ResponseDto<BookResponseDto>> response = bookController.getBook(1L);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(BMSConstant.SOMETHING_WENT_WRONG, response.getBody().getMessage());
    }

    @Test
    void testUpdateBook() {

        BookRequestDto bookRequestDto = new BookRequestDto(
                "Test Book",
                "Test Author",
                "9291282492012",
                19.34
        );

        BookResponseDto bookResponseDto = new BookResponseDto(
                1L,
                "Test Book",
                "Test Author",
                LocalDate.now(),
                "9291282492012",
                19.34
        );

        when(bookService.updateBookById(anyLong(), any(BookRequestDto.class))).thenReturn(bookResponseDto);

        ResponseEntity<BookResponseDto> response = bookController.updateBook(1L, bookRequestDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testDeleteBook_Success() {

        BookResponseDto bookResponseDto = new BookResponseDto(
                1L,
                "Test Book",
                "Test Author",
                LocalDate.now(),
                "9291282492012",
                19.34
        );

        when(bookService.deleteBookById(anyLong())).thenReturn(bookResponseDto);

        ResponseEntity<ResponseDto<BookResponseDto>> response = bookController.deleteBook(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(String.format(BMSConstant.BOOK_DELETED_SUCCESSFULLY, bookResponseDto.title()), response.getBody().getMessage());
    }

    @Test
    void testDeleteBook_Failure() {
        when(bookService.deleteBookById(anyLong())).thenReturn(null);

        ResponseEntity<ResponseDto<BookResponseDto>> response = bookController.deleteBook(1L);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(BMSConstant.SOMETHING_WENT_WRONG, response.getBody().getMessage());
    }
}