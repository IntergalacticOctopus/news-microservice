package com.example.news.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "news")
public class News {
    @Id
    @Column(name = "news_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long newsId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "title")
    private String title;

    @Column(name = "theme")
    private String theme;

    @Column(name = "publication_date")
    private LocalDate publicationDate;

    public News(Long userId, String title, String theme, LocalDate publicationDate) {
        this.userId = userId;
        this.title = title;
        this.theme = theme;
        this.publicationDate = publicationDate;
    }
}
