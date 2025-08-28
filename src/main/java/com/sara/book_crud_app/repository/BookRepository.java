package com.sara.book_crud_app.repository;

import com.sara.book_crud_app.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
