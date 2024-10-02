package com.example.news.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@AllArgsConstructor
@Data
public class NewNewsDto {
    @NotBlank
    @NotNull
    @Size(min = 1, max = 256)
    private String title;
    @NotNull
    private String theme;
    @NotNull
    @PositiveOrZero
    private Integer userId;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate publicationDate;
}