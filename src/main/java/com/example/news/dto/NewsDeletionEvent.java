package com.example.news.dto;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NewsDeletionEvent {
    @PositiveOrZero
    private Long newsId;
}
