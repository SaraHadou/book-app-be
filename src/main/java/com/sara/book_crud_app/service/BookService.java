package com.sara.book_crud_app.service;

import com.sara.book_crud_app.entity.Book;

import java.util.List;

public interface BookService {
    List<Book> getAllBooks();
    Book saveBook(Book book);
    Book updateBook(Book book);
    Book getBookById(Long id);
    void deleteBook(Long id);
}
