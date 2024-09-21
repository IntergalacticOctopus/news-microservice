package com.example.news.dto;

import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
public class NewsDto {
    private String title;
    private String theme;
    private Integer userId;
    private LocalDate publicationDate;

}
