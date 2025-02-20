package com.page.turner.PageTurner.service;

import com.page.turner.PageTurner.entity.Book;
import com.page.turner.PageTurner.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public Book getBookByID(Long id) throws Exception {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            return book.get();
        } else {
            throw new Exception("Book with ID " + id + " not found");
        }
    }

    public Book addBook(Book book) throws Exception {
        try {
            return bookRepository.save(book);
        } catch (Exception e) {
            throw new Exception("Unable to add book " + e);
        }
    }

    public Page<Book> getBooks(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    public List<Book> getBookByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    public List<Book> getBookByAuthor(String author) {
        return bookRepository.findByAuthor(author);
    }
}
