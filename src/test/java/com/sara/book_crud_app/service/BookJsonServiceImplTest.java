package com.sara.book_crud_app.service;


import com.sara.book_crud_app.exception.BookNotFoundException;
import com.sara.book_crud_app.model.Book;
import com.sara.book_crud_app.service.impl.BookJsonServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BookJsonServiceImplTest {

    private BookJsonServiceImpl bookService;
    private final Path testFilePath = Paths.get("data/books.json");

    @BeforeAll
    void setup() throws IOException {
        // Ensure clean test file
        Files.createDirectories(testFilePath.getParent());
        Files.write(testFilePath, "[]".getBytes());
        bookService = new BookJsonServiceImpl();
    }

    @AfterEach
    void cleanup() throws IOException {
        // Clear file after each test
        Files.write(testFilePath, "[]".getBytes());
    }

    @Test
    void testSaveBook() {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");
        book.setAuthor("Author A");
        book.setPrice(10.0);

        Book saved = bookService.saveBook(book);

        assertNotNull(saved.getId());
        assertEquals("Test Book", saved.getTitle());

        List<Book> all = bookService.getAllBooks();
        assertEquals(1, all.size());
    }

    @Test
    void testGetBookById_Success() {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Book 1");
        book.setAuthor("Author 1");
        book.setPrice(15.0);

        Book saved = bookService.saveBook(book);
        Book fetched = bookService.getBookById(saved.getId());

        assertEquals(saved.getId(), fetched.getId());
        assertEquals("Book 1", fetched.getTitle());
    }

    @Test
    void testGetBookById_NotFound() {
        assertThrows(BookNotFoundException.class, () -> bookService.getBookById(999L));
    }

    @Test
    void testDeleteBook_Success() {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Book to delete");
        book.setAuthor("Author X");
        book.setPrice(20.0);

        Book saved = bookService.saveBook(book);
        bookService.deleteBook(saved.getId());

        assertEquals(0, bookService.getAllBooks().size());
        assertThrows(BookNotFoundException.class, () -> bookService.getBookById(saved.getId()));
    }

    @Test
    void testDeleteBook_NotFound() {
        assertThrows(BookNotFoundException.class, () -> bookService.deleteBook(123L));
    }
}