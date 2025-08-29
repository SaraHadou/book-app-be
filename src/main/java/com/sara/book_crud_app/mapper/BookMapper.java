package com.sara.book_crud_app.mapper;

import com.sara.book_crud_app.dto.request.BookRequestDto;
import com.sara.book_crud_app.dto.response.BookResponseDto;
import com.sara.book_crud_app.entity.Book;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookResponseDto toResponseDto(Book book);
    Book toEntity(BookRequestDto dto);

}