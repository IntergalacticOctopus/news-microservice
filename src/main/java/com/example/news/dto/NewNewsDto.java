package com.example.news.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;

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
    @PositiveOrZero
    private Integer userId;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate publicationDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewNewsDto that = (NewNewsDto) o;
        return Objects.equals(title, that.title) && Objects.equals(theme, that.theme) && Objects.equals(userId, that.userId) && Objects.equals(publicationDate, that.publicationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, theme, userId, publicationDate);
    }
}