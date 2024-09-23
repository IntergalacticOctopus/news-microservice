package com.example.news.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class NewsParamDto {
    private String theme;
    private Integer userId;
    private LocalDate publicationDate;
}
