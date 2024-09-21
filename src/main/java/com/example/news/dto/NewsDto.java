package com.example.news.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.management.Descriptor;
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
