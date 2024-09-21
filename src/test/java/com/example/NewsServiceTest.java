package com.example;

import com.example.news.dto.NewNewsDto;
import com.example.news.dto.NewsDto;
import com.example.news.dto.NewsParamDto;
import com.example.news.dto.UpdateNewsDto;
import com.example.news.mapper.NewsMapper;
import com.example.news.model.News;
import com.example.news.repository.NewsRepository;
import com.example.news.service.NewsServiceImpl;
import com.example.news.exseption.model.Theme;
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
    @InjectMocks
    private NewsServiceImpl serviceUnderTest;
    News news = new News(1, 1, "TITLE", new Theme("THEME"), LocalDate.of(2024, 6, 8));
    NewsDto newsDto = new NewsDto("TITLE", "THEME", 1, LocalDate.of(2024, 6, 8));
    NewNewsDto newNewsDto = new NewNewsDto("TITLE", "THEME", 1, LocalDate.of(2024, 6, 8));
    UpdateNewsDto updateNewsDto = new UpdateNewsDto("TITLE", "THEME", 1, LocalDate.of(2024, 6, 8));

    @BeforeEach
    void setUp() {
        mockNewsRepository = mock(NewsRepository.class);
        mockNewsMapper = mock(NewsMapper.class);
        serviceUnderTest = new NewsServiceImpl(mockNewsRepository, mockNewsMapper);
    }

    @Test
    void getNewsListByParametersTest() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        NewsParamDto paramDto = new NewsParamDto("THEME", 1, LocalDate.of(2024, 6, 8));

        when(mockNewsRepository.getNewsByParams(paramDto.getTheme(), paramDto.getUserId(), paramDto.getPublicationDate(), pageRequest))
                .thenReturn(List.of(news));
        when(mockNewsMapper.toNewsDto(any())).thenReturn(new NewsDto("TITLE", "THEME", 1, LocalDate.of(2024, 6, 8)));

        List<NewsDto> result = serviceUnderTest.getNews(pageRequest, paramDto);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void getNewsByIdTest() {
        int newsId = 1;

        when(mockNewsRepository.getByNewsId(newsId)).thenReturn(Optional.ofNullable(news));
        when(mockNewsMapper.toNewsDto(any())).thenReturn(newsDto);

        NewsDto result = serviceUnderTest.getNewsById(newsId);

        assertNotNull(result);
        assertEquals(result.getTitle(), news.getTitle());
        assertEquals(result.getTheme(), news.getTheme().getThemeName());
        assertEquals(result.getPublicationDate(), news.getPublicationDate());
        assertEquals(result.getUserId(), news.getUserId());
    }

    @Test
    void addNewsTest() {
        when(mockNewsMapper.toNews(any(), any())).thenReturn(news);
        when(mockNewsRepository.save(any())).thenReturn(news);
        when(mockNewsMapper.toNewsDto(any())).thenReturn(newsDto);

        NewsDto result = serviceUnderTest.addNews(newNewsDto);

        assertNotNull(result);
        assertEquals(result.getTitle(), news.getTitle());
        assertEquals(result.getTheme(), news.getTheme().getThemeName());
        assertEquals(result.getPublicationDate(), news.getPublicationDate());
        assertEquals(result.getUserId(), news.getUserId());
    }

    @Test
    void shouldUpdateNews() {
        // Arrange
        int newsId = 1;

        when(mockNewsRepository.findById(newsId)).thenReturn(Optional.of(news));
        when(mockNewsMapper.toNews(any(), any())).thenReturn(news);
        when(mockNewsRepository.save(any())).thenReturn(new News(5, "New title", new Theme("Theme"), LocalDate.of(2030, 1, 1)));
        when(mockNewsMapper.toNewsDto(any())).thenReturn(new NewsDto("New title", "Theme", 5,  LocalDate.of(2030, 1, 1)));

        // Act
        NewsDto result = serviceUnderTest.updateNews(newsId, updateNewsDto);

        // Assert
        assertNotNull(result);

        assertNotEquals(result.getTitle(), news.getTitle());
        assertNotEquals(result.getTheme(), news.getTheme().getThemeName());
        assertNotEquals(result.getPublicationDate(), news.getPublicationDate());
        assertNotEquals(result.getUserId(), news.getUserId());
    }

}
