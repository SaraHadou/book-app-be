package com.sara.book_crud_app.dto.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BookResponseDto {
    private Long id;
    private String title;
    private String author;
    private Double price;
}
