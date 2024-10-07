package com.example;

import com.example.news.dto.ParamsNewsDto;
import com.example.news.model.NewNewsDto;
import com.example.news.model.News;
import com.example.news.model.NewsDto;
import com.example.news.model.UpdateNewsDto;
import com.example.news.service.KafkaSender;
import com.example.news.mapper.NewsMapper;
import com.example.news.repository.NewsRepository;
import com.example.news.service.NewsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RequiredArgsConstructor
public class NewsServiceTest {
    @Mock
    private NewsRepository mockNewsRepository;
    @Mock
    private NewsMapper mockNewsMapper;
    @Mock
    private KafkaSender mockKafkaSender;
    @InjectMocks
    private NewsServiceImpl serviceUnderTest;
    News news = new News(1L, 1L, "TITLE", "THEME", LocalDate.of(2024, 6, 8));
    NewsDto newsDto = new NewsDto();
    NewNewsDto newNewsDto = new NewNewsDto();
    UpdateNewsDto updateNewsDto = new UpdateNewsDto();

    @BeforeEach
    void setUp() {
        newsDto.setTitle("TITLE");
        newsDto.setTheme("THEME");
        newsDto.setPublicationDate(LocalDate.of(2024, 6, 8));

        newNewsDto.setTitle("TITLE");
        newNewsDto.setTheme("THEME");
        newNewsDto.setPublicationDate(LocalDate.of(2024, 6, 8));

        updateNewsDto.setTitle("TITLE");
        updateNewsDto.setTheme("THEME");
        updateNewsDto.setUserId(1L);
        updateNewsDto.setPublicationDate(LocalDate.of(2024, 6, 8));

        mockNewsRepository = mock(NewsRepository.class);
        mockNewsMapper = mock(NewsMapper.class);
        mockKafkaSender = mock(KafkaSender.class);
        serviceUnderTest = new NewsServiceImpl(mockKafkaSender, mockNewsRepository, mockNewsMapper);
    }

    @Test
    void getNewsListByParametersTest() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        ParamsNewsDto paramsNewsDto = new ParamsNewsDto(1, 10, "THEME", 1L, LocalDate.of(2024, 6, 8));

        when(mockNewsRepository.getNewsByParams(paramsNewsDto.getTheme(), paramsNewsDto.getUser_id(), paramsNewsDto.getPublication_date(), pageRequest))
                .thenReturn(List.of(news));
        when(mockNewsMapper.toNewsDto(any())).thenReturn(newsDto);

        List<NewsDto> result = serviceUnderTest.getNews(paramsNewsDto);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void getNewsByIdTest() {
        Long newsId = 1L;

        when(mockNewsRepository.getByNewsId(newsId)).thenReturn(Optional.ofNullable(news));
        when(mockNewsMapper.toNewsDto(any())).thenReturn(newsDto);

        NewsDto result = serviceUnderTest.getNewsById(newsId);

        assertNotNull(result);
        assertEquals(result.getTitle(), news.getTitle());
        assertEquals(result.getTheme(), news.getTheme());
        assertEquals(result.getPublicationDate(), news.getPublicationDate());
    }

    @Test
    void addNewsTest() {
        when(mockNewsMapper.toNews(any())).thenReturn(news);
        when(mockNewsRepository.save(any())).thenReturn(news);
        when(mockNewsMapper.toNewsDto(any())).thenReturn(newsDto);

        NewsDto result = serviceUnderTest.addNews(newNewsDto);

        assertNotNull(result);
        assertEquals(result.getTitle(), news.getTitle());
        assertEquals(result.getTheme(), news.getTheme());
        assertEquals(result.getPublicationDate(), news.getPublicationDate());
    }

    @Test
    void shouldUpdateNews() {
        Long newsId = 1L;

        when(mockNewsRepository.findById(newsId)).thenReturn(Optional.of(news));
        when(mockNewsMapper.toNews(any())).thenReturn(news);
        News referenceNews = new News(5L, "New title", "Theme", LocalDate.of(2030, 1, 1));
        when(mockNewsRepository.save(any())).thenReturn(referenceNews);
        when(mockNewsMapper.toNewsDto(any())).thenReturn(newsDto);

        NewsDto result = serviceUnderTest.updateNews(newsId, updateNewsDto);

        assertNotNull(result);

        assertNotEquals(result.getTitle(), referenceNews.getTitle());
        assertNotEquals(result.getTheme(), referenceNews.getTheme());
        assertNotEquals(result.getPublicationDate(), referenceNews.getPublicationDate());
        assertNotEquals(result.getUserId(), referenceNews.getUserId());
    }

}
