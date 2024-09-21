package com.example.news.exseption.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "themes")
public class Theme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "theme_id")
    private int themeId;

    @Column(name = "theme_name")
    private String themeName;

    public Theme(String themeName) {
        this.themeName = themeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Theme theme = (Theme) o;
        return themeId == theme.themeId && Objects.equals(themeName, theme.themeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(themeId, themeName);
    }

    @Override
    public String toString() {
        return "Theme{" +
                "themeId=" + themeId +
                ", themeName='" + themeName + '\'' +
                '}';
    }
}
