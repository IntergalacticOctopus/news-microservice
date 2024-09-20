package com.example.news.mapper;

import com.example.news.dto.NewNewsDto;
import com.example.news.dto.NewsDto;
import com.example.news.model.News;
import com.example.themes.model.Theme;
import org.springframework.stereotype.Component;

@Component
public class NewsMapper {
    public NewsDto toNewsDto (News news) {
        return NewsDto.builder()
                .title(news.getTitle())
                .theme(news.getTheme().getThemeName())
                .userId(news.getUserId())
                .publicationDate(news.getPublicationDate())
                .build();
    }

    public News toNews (NewNewsDto newNewsDto, Theme theme) {
        return News.builder()
                .userId(newNewsDto.getUserId())
                .title(newNewsDto.getTitle())
                .theme(theme)
                .publicationDate(newNewsDto.getPublicationDate())
                .build();
    }
}
