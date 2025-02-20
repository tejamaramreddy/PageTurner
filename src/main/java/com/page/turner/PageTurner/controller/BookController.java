package com.page.turner.PageTurner.controller;


import com.page.turner.PageTurner.entity.Book;
import com.page.turner.PageTurner.model.ApiResponse;
import com.page.turner.PageTurner.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<Book>>> getBooks(@RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Book> bookPage = bookService.getBooks(pageable);

            ApiResponse<Page<Book>> response = new ApiResponse<>("success", "Books found ", bookPage, HttpStatus.OK);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Book>> getBookByID(@PathVariable Long id) {
        try {
            Book book = bookService.getBookByID(id);
            ApiResponse<Book> response = new ApiResponse<>("success", "Book found", book, HttpStatus.OK);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            ApiResponse<Book> errorResponse = new ApiResponse<>("Failed to fetch", ex.getMessage(), new Book(), HttpStatus.NOT_FOUND);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }

    }

    @PostMapping("/")
    public ResponseEntity<ApiResponse<Book>> addNewBook(@RequestBody Book book) {
        try {
            if (book.getTitle() == null) {
                ApiResponse<Book> errorResponse = new ApiResponse<>("Failed to add the new book", "Request body is empty", null, HttpStatus.BAD_REQUEST);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            }

            List<Book> books = bookService.getBookByTitle(book.getTitle().trim());
            if (books.isEmpty()) {
                Book newBook = bookService.addBook(book);
                ApiResponse<Book> response = new ApiResponse<>("Success", "Book added", newBook, HttpStatus.CREATED);
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            } else {
                ApiResponse<Book> response = new ApiResponse<>("Duplicate", "Book is already in database", book, HttpStatus.CONFLICT);
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            }

        } catch (Exception e) {
            ApiResponse<Book> errorResponse = new ApiResponse<>("Failed to add the new book", e.getMessage(), new Book(), HttpStatus.CREATED);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/authors")
    public ResponseEntity<ApiResponse<List<Book>>> getBooksByAuthor(@RequestParam String author) {
        List<Book> books = null;
        try {
            if (!author.isEmpty()) {
                books = bookService.getBookByAuthor(author);

                ApiResponse<List<Book>> response = new ApiResponse<>(
                        "Success",
                        "Books by " + author,
                        books,
                        HttpStatus.OK
                );
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                ApiResponse<List<Book>> response = new ApiResponse<>(
                        "Failed",
                        "Author name must not be empty",
                        books,
                        HttpStatus.BAD_REQUEST
                );
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            ApiResponse<List<Book>> response = new ApiResponse<>(
                    "Failed",
                    e.getMessage(),
                    books,
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<Book>>> searchBooksByTitle(@RequestParam String searchTerm) {
        try {
            List<Book> books = bookService.findBookByLikeTitle(searchTerm.trim());
            if (books.isEmpty()) {
                ApiResponse<List<Book>> response = new ApiResponse<>("Success", "No Books which match " + searchTerm, books, HttpStatus.NO_CONTENT);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
            }
            ApiResponse<List<Book>> response = new ApiResponse<>("Success", "Books which match " + searchTerm, books, HttpStatus.OK);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
