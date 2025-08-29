package com.sara.book_crud_app.controller;

import com.sara.book_crud_app.dto.request.BookRequestDto;
import com.sara.book_crud_app.dto.response.BookResponseDto;
import com.sara.book_crud_app.entity.Book;
import com.sara.book_crud_app.mapper.BookMapper;
import com.sara.book_crud_app.service.BookService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;
    private final BookMapper bookMapper;

    public BookController(@Qualifier("bookServiceImpl") BookService bookService, BookMapper bookMapper) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
    }

    @GetMapping
    public List<BookResponseDto> getAllBooks() {
        return bookService.getAllBooks().stream()
                .map(bookMapper::toResponseDto).toList();
    }

    @GetMapping("/{id}")
    public BookResponseDto getBook(@PathVariable Long id) {
        return bookMapper.toResponseDto(bookService.getBookById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookResponseDto createBook(@RequestBody BookRequestDto book) {
        Book newBook = bookMapper.toEntity(book);
        return bookMapper.toResponseDto(bookService.saveBook(newBook));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BookResponseDto updateBook(@PathVariable Long id, @RequestBody BookRequestDto book) {
        Book updatedBook = bookMapper.toEntity(book);
        updatedBook.setId(id);
        return bookMapper.toResponseDto(bookService.updateBook(updatedBook));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }

}
