package com.example;

import com.example.news.model.News;
import com.example.news.repository.NewsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class NewsRepositoryTest {
    @Autowired
    private NewsRepository newsRepository;

    private final String NEWS_TITLE = "First Title";
    private final String THEME_NAME = "First name";
    private final Long USER_ID = 100L;
    private final Long NEWS_ID = 100L;
    private final LocalDate PUBLICATION_DATE = LocalDate.of(2024, 6, 8);

    final PageRequest pageRequest = PageRequest.of(0, 10);

    private News firstNews;
    private News secondNews;
    private News thirdNews;
    private String firstTheme;
    private String secondTheme;
    private String thirdTheme;

    @BeforeEach
    void setUp() {
        firstTheme = THEME_NAME;
        secondTheme = "Second name";
        thirdTheme = "Third name";

        firstNews = new News(USER_ID, NEWS_TITLE, firstTheme, PUBLICATION_DATE);
        secondNews = new News(2L, "NEWS_TITLE", secondTheme, LocalDate.of(2020, 1, 1));
        thirdNews = new News(3L, "title", thirdTheme, LocalDate.of(2025, 1, 1));
        newsRepository.saveAll(List.of(firstNews, secondNews, thirdNews));

    }

    @Test
    void getNewsByThemeTest() {
        Optional<News> firstNewsFromDto = newsRepository.getByNewsId(1L);
        News news = firstNewsFromDto.get();
        assertEquals(1, news.getNewsId());
        assertEquals(NEWS_TITLE, news.getTitle());
        assertEquals(USER_ID, news.getUserId());
        assertEquals(PUBLICATION_DATE, news.getPublicationDate());
        assertEquals(THEME_NAME, news.getTheme());

        List<News> firstNewsListByParams = newsRepository.getNewsByParams(null, 2L, null, pageRequest);
        News firstNewsByParams = firstNewsListByParams.get(0);

        assertEquals(secondTheme, firstNewsByParams.getTheme());
        assertEquals("NEWS_TITLE", firstNewsByParams.getTitle());
        assertEquals(LocalDate.of(2020, 1, 1), firstNewsByParams.getPublicationDate());
        assertEquals(2, firstNewsByParams.getUserId());

        List<News> secondNewsListByParams = newsRepository.getNewsByParams(firstTheme, null, null, pageRequest);
        News secondNewsByParams = secondNewsListByParams.get(0);

        assertEquals(firstTheme, secondNewsByParams.getTheme());
        assertEquals(NEWS_TITLE, secondNewsByParams.getTitle());
        assertEquals(PUBLICATION_DATE, secondNewsByParams.getPublicationDate());
        assertEquals(USER_ID, secondNewsByParams.getUserId());

        List<News> thirdNewsListByParams = newsRepository.getNewsByParams(null, null, LocalDate.of(2025, 1, 1), pageRequest);
        News thirdNewsByParams = thirdNewsListByParams.get(0);

        assertEquals(thirdTheme, thirdNewsByParams.getTheme());
        assertEquals("title", thirdNewsByParams.getTitle());
        assertEquals(LocalDate.of(2025, 1, 1), thirdNewsByParams.getPublicationDate());
        assertEquals(3, thirdNewsByParams.getUserId());

        List<News> newsListByAllParams = newsRepository.getNewsByParams(THEME_NAME, USER_ID, PUBLICATION_DATE, pageRequest);
        News newsByAllParams = newsListByAllParams.get(0);

        assertEquals(firstTheme, newsByAllParams.getTheme());
        assertEquals(NEWS_TITLE, newsByAllParams.getTitle());
        assertEquals(PUBLICATION_DATE, newsByAllParams.getPublicationDate());
        assertEquals(USER_ID, newsByAllParams.getUserId());
    }

}
