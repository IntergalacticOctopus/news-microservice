package com.example.news.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class UpdateNewsDto {
    @Size(min = 1, max = 256)
    private String title;
    private String theme;
    private Integer userId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate publicationDate;
}
