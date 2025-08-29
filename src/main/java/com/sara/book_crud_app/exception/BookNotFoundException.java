package com.sara.book_crud_app.exception;

public class BookNotFoundException  extends RuntimeException {

    public BookNotFoundException(Long id) {
        super("Book not found with id: %s".formatted(id));
    }

}
