package com.example.news.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class NewsDto {
    private String title;
    private String theme;
    private Integer userId;
    private LocalDate publicationDate;

}
