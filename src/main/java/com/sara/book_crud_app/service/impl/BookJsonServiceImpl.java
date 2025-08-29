package com.sara.book_crud_app.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sara.book_crud_app.exception.BookNotFoundException;
import com.sara.book_crud_app.entity.Book;
import com.sara.book_crud_app.service.BookService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class BookJsonServiceImpl implements BookService {

    private final Path filePath = Paths.get("data/books.json");
    private final ObjectMapper objectMapper = new ObjectMapper();
    private List<Book> books;

    public BookJsonServiceImpl() {
        try {

            Files.createDirectories(filePath.getParent());  // Create parent directories if not exist
            if (!Files.exists(filePath)) {                  // If file does not exist, create it with empty array
                Files.write(filePath, "[]".getBytes());
            }

            books = Arrays.asList(objectMapper.readValue(filePath.toFile(), Book[].class));
            books = new ArrayList<>(books);                 // to mutable for adding and removing

        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize JSON file", e);
        }

    }

    @Override
    public List<Book> getAllBooks() {
        return books;
    }

    @Override
    public Book saveBook(Book book) {
        long nextId = books.stream().mapToLong(Book::getId).max().orElse(0) + 1;
        book.setId(nextId);
        books.add(book);
        saveToFile();
        return book;
    }

    @Override
    public Book updateBook(Book book) {
        int index = IntStream.range(0, books.size())
                .filter(i -> books.get(i).getId() == book.getId())
                .findFirst()
                .orElse(-1);

        if (index != -1) {
            books.set(index, book);
            saveToFile();
            return book;
        } else {
            throw new RuntimeException("Book not found with id: " + book.getId());
        }
    }

    @Override
    public Book getBookById(Long id) {
        return books.stream()
                .filter(b -> b.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    @Override
    public void deleteBook(Long id) {
        Book book = books.stream()
                .filter(b -> b.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new BookNotFoundException(id));
        books.remove(book);
        saveToFile();
    }

    private void saveToFile() {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(filePath.toFile(), books);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write to JSON file", e);
        }
    }

}
