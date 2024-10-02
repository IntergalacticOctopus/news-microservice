package com.example.news.dto;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class ParamsNewsDto {
    @Parameter(description = "Номер страницы для пагинации")
    @PositiveOrZero
    Integer page;
    @Parameter(description = "Количество записей на странице")
    @PositiveOrZero
    Integer size;
    @Parameter(description = "Значение для фильтрация по теме")
    String theme;
    @Parameter(description = "Значение для фильтрация по ID автора")
    @PositiveOrZero
    Integer user_id;
    @Parameter(description = "Для фильтрация по дате публикации")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate publication_date;

    public PageRequest getPageable() {
        return PageRequest.of(
                page != null ? page - 1: 0,
                size != null ? size : 10
        );
    }
}
