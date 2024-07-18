package books_management_system.controller;

import books_management_system.constant.BMSConstant;
import books_management_system.dto.BookRequestDto;
import books_management_system.dto.BookResponseDto;
import books_management_system.dto.ResponseDto;
import books_management_system.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/books")
@AllArgsConstructor
@Tag(name = "Books", description = "Book Management APIs")
public class BookController {

    private BookService bookService;

    @Operation(summary = "Add a new book", description = "Add a new book to the library")
    @ApiResponse(responseCode = "200", description = "Book added successfully")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @PostMapping
    public ResponseEntity<ResponseDto<BookResponseDto>> addBook(@RequestBody @Valid BookRequestDto bookRequestDto) {
        BookResponseDto newBook = this.bookService.addNewBook(bookRequestDto);
        if(Objects.nonNull(newBook)) {
            return ResponseEntity.ok(new ResponseDto<>(String.format(BMSConstant.BOOK_ADDED_SUCCESSFULLY, newBook.title()), newBook, null));
        }
        return new ResponseEntity<>(new ResponseDto<>(BMSConstant.SOMETHING_WENT_WRONG, new ArrayList<>()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Operation(summary = "Get all books", description = "Retrieve a list of all books")
    @ApiResponse(responseCode = "200", description = "List all books")
    @GetMapping
    public ResponseEntity<ResponseDto<List<BookResponseDto>>> getBooks() {
        return ResponseEntity.ok(new ResponseDto<>(BMSConstant.LIST_ALL_BOOKS, this.bookService.getAllBooks(), List.of()));
    }

    @Operation(summary = "Get book by ID", description = "Retrieve a book by its ID")
    @ApiResponse(responseCode = "200", description = "Book retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Book not found")
    @GetMapping("/id")
    public ResponseEntity<ResponseDto<BookResponseDto>> getBook(@RequestParam @Valid Long id) {
        BookResponseDto bookResponseDto = bookService.getBookById(id);
        if(Objects.nonNull(bookResponseDto)) {
            return ResponseEntity.ok(new ResponseDto<>(String.format(BMSConstant.BOOK_RETRIEVED_SUCCESSFULLY,bookResponseDto.title()), bookResponseDto, List.of()));
        }
        return new ResponseEntity<>(new ResponseDto<>(BMSConstant.SOMETHING_WENT_WRONG, List.of()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Operation(summary = "Update book", description = "Update a book's details")
    @ApiResponse(responseCode = "200", description = "Book updated successfully")
    @ApiResponse(responseCode = "404", description = "Book not found")
    @PutMapping
    public ResponseEntity<BookResponseDto> updateBook(@RequestParam Long id, @RequestBody @Valid BookRequestDto bookRequestDto) {
        return ResponseEntity.ok(bookService.updateBookById(id, bookRequestDto));
    }

    @Operation(summary = "Delete book", description = "Delete a book by its ID")
    @ApiResponse(responseCode = "200", description = "Book deleted successfully")
    @ApiResponse(responseCode = "404", description = "Book not found")
    @DeleteMapping
    public ResponseEntity<ResponseDto<BookResponseDto>> deleteBook(@RequestParam @Valid Long id) {
        BookResponseDto bookResponseDto = bookService.deleteBookById(id);
        if(Objects.nonNull(bookResponseDto)) {
            return ResponseEntity.ok(new ResponseDto<>(String.format(BMSConstant.BOOK_DELETED_SUCCESSFULLY,bookResponseDto.title()), bookResponseDto, List.of()));
        }
        return new ResponseEntity<>(new ResponseDto<>(BMSConstant.SOMETHING_WENT_WRONG, List.of()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}