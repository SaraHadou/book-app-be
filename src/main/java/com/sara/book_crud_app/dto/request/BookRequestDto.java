package com.sara.book_crud_app.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookRequestDto {
    private String title;
    private String author;
    private Double price;
}