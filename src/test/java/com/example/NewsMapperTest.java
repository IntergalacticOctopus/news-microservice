package com.example;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;

import com.example.news.dto.NewNewsDto;
import com.example.news.dto.NewsDto;
import com.example.news.mapper.NewsMapper;
import com.example.news.model.News;
import com.example.news.model.Theme;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class NewsMapperTest {

    private final String NEWS_TITLE = "Example News Title";
    private final String THEME_NAME = "Example Theme Name";
    private final Integer USER_ID = 100;
    private final Integer NEWS_ID = 100;
    private final LocalDate PUBLICATION_DATE = LocalDate.of(2024, 6, 8);

    private NewsMapper mapper;
    private Theme mockedTheme;

    @BeforeEach
    void setUp() {
        mapper = new NewsMapper();
        mockedTheme = mock(Theme.class);

        when(mockedTheme.getThemeName()).thenReturn(THEME_NAME);
    }

    @Test
    void toNewsDtoTest() {
        News news = new News(NEWS_ID, USER_ID, NEWS_TITLE, mockedTheme, PUBLICATION_DATE);

        NewsDto newsDto = mapper.toNewsDto(news);

        assertEquals(NEWS_TITLE, newsDto.getTitle());
        assertEquals(THEME_NAME, newsDto.getTheme());
        assertEquals(USER_ID, newsDto.getUserId());
        assertEquals(PUBLICATION_DATE, newsDto.getPublicationDate());
    }

    @Test
    void toNewsTest() {
        NewNewsDto newsDto = new NewNewsDto(NEWS_TITLE, THEME_NAME, USER_ID, PUBLICATION_DATE);

        News result = mapper.toNews(newsDto, mockedTheme);

        assertEquals(NEWS_TITLE, result.getTitle());
        assertEquals(THEME_NAME, result.getTheme().getThemeName());
        assertEquals(USER_ID, result.getUserId());
        assertEquals(PUBLICATION_DATE, result.getPublicationDate());
    }
}
