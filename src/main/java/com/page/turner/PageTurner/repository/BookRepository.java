package com.page.turner.PageTurner.repository;

import com.page.turner.PageTurner.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findById(Long id);                       // Find a book by ID

    List<Book> findAll();                         // Find all books

    void deleteById(Long id);                    // Delete book by ID

    long count();                                 // Count the number of books

    // Custom methods based on naming convention
    List<Book> findByTitle(String title);         // Find books by title

    List<Book> findByAuthor(String author);       // Find books by author

    List<Book> findByTitleContainingIgnoreCase(String title);

    List<Book> findByTitleLike(String title);     // Find books with titles like the search string

    List<Book> findByTitleAndAuthor(String title, String author); // Find books by title and author

    List<Book> findByAuthorOrderByPublishedDateDesc(String author); // Find books by author, ordered by published date

    // Pagination and sorting
    List<Book> findAllByOrderByPublishedDateDesc(); // Sort all books by published date

    // Custom query using @Query
    @Query("SELECT b FROM Book b WHERE b.author = :author AND b.publishedDate > :date")
    List<Book> findBooksByAuthorAndPublishedAfter(String author, LocalDate date);
}
