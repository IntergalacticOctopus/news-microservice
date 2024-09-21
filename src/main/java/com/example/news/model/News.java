package com.example.news.model;

import com.example.news.exseption.model.Theme;
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
    private Integer newsId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "title")
    private String title;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "theme_id")
    private Theme theme;

    @Column(name = "publication_date")
    private LocalDate publicationDate;

    public News(Integer userId, String title, Theme theme, LocalDate publicationDate) {
        this.userId = userId;
        this.title = title;
        this.theme = theme;
        this.publicationDate = publicationDate;
    }

    @Override
    public String toString() {
        return "News{" +
                "newsId=" + newsId +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                ", theme=" + theme +
                ", publicationDate=" + publicationDate +
                '}';
    }
}
