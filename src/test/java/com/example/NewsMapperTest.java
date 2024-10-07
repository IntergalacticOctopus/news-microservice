package com.example;

import static org.junit.jupiter.api.Assertions.*;

import com.example.news.model.NewNewsDto;
import com.example.news.model.NewsDto;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import java.time.LocalDate;
import com.example.news.mapper.NewsMapper;
import com.example.news.model.News;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class NewsMapperTest {

    private final String NEWS_TITLE = "Example News Title";
    private final String THEME_NAME = "Example Theme Name";
    private final Long USER_ID = 100L;
    private final Long NEWS_ID = 100L;
    private final LocalDate PUBLICATION_DATE = LocalDate.of(2024, 6, 8);

    private NewsMapper mapper;
    NewNewsDto newsDto = new NewNewsDto();

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(NewsMapper.class);
    }

    @Test
    void toNewsDtoTest() {
        News news = new News(NEWS_ID, USER_ID, NEWS_TITLE, THEME_NAME, PUBLICATION_DATE);

        NewsDto newsDto = mapper.toNewsDto(news);

        assertEquals(NEWS_TITLE, newsDto.getTitle());
        assertEquals(THEME_NAME, newsDto.getTheme());
        assertEquals(USER_ID, newsDto.getUserId());
        assertEquals(PUBLICATION_DATE, newsDto.getPublicationDate());
    }

    @Test
    void toNewsTest() {
        newsDto.setTitle(NEWS_TITLE);
        newsDto.setTheme(THEME_NAME);
        newsDto.setUserId(USER_ID);
        newsDto.setPublicationDate(PUBLICATION_DATE);

        News result = mapper.toNews(newsDto);

        assertEquals(NEWS_TITLE, result.getTitle());
        assertEquals(THEME_NAME, result.getTheme());
        assertEquals(USER_ID, result.getUserId());
        assertEquals(PUBLICATION_DATE, result.getPublicationDate());
    }
}
