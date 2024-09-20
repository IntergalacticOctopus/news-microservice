package com.example.news.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class NewNewsDto {
    @NotBlank
    @NotNull
    @Size(min = 1, max = 256)
    private String title;
    @NotNull
    private String theme;
    @NotNull
    private Integer userId;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate publicationDate;
}