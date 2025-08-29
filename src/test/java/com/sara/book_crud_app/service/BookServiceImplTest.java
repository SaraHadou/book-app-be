package com.sara.book_crud_app.service;

import com.sara.book_crud_app.exception.BookNotFoundException;
import com.sara.book_crud_app.entity.Book;
import com.sara.book_crud_app.repository.BookRepository;
import com.sara.book_crud_app.service.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    private Book book;

    @BeforeEach
    void setUp() {
        book = new Book(1L, "The Psychology of Money", "Morgan Housel", 29.99);
    }

    @Test
    void saveBook_ShouldReturnSavedBook() {
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        var saved = bookService.saveBook(book);

        assertNotNull(saved);
        assertEquals("The Psychology of Money", saved.getTitle());
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void getBookById_WhenExists_ShouldReturnBook() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        var found = bookService.getBookById(1L);

        assertEquals("The Psychology of Money", found.getTitle());
    }

    @Test
    void getBookById_WhenNotFound_ShouldThrowException() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> bookService.getBookById(1L));
    }

    @Test
    void deleteBook_WhenExists_ShouldCallDelete() {
        when(bookRepository.existsById(1L)).thenReturn(true);

        bookService.deleteBook(1L);

        verify(bookRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteBook_WhenNotExists_ShouldThrowException() {
        when(bookRepository.existsById(1L)).thenReturn(false);

        assertThrows(BookNotFoundException.class, () -> bookService.deleteBook(1L));
    }
}
