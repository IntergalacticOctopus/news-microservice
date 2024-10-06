package com.example;

import com.example.news.model.*;
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
//    @Mock
//    private NewsRepository mockNewsRepository;
//    @Mock
//    private NewsMapper mockNewsMapper;
//    @Mock
//    private KafkaSender mockKafkaSender;
//    @InjectMocks
//    private NewsServiceImpl serviceUnderTest;
//    News news = new News(1, 1, "TITLE", "THEME", LocalDate.of(2024, 6, 8));
//    NewsDto newsDto = new NewsDto("TITLE", "THEME", 1, LocalDate.of(2024, 6, 8));
//    NewNewsDto newNewsDto = new NewNewsDto("TITLE", "THEME", 1, LocalDate.of(2024, 6, 8));
//    UpdateNewsDto updateNewsDto = new UpdateNewsDto("TITLE", "THEME", 1, LocalDate.of(2024, 6, 8));
//
//    @BeforeEach
//    void setUp() {
//        mockNewsRepository = mock(NewsRepository.class);
//        mockNewsMapper = mock(NewsMapper.class);
//        mockKafkaSender = mock(KafkaSender.class);
//        serviceUnderTest = new NewsServiceImpl(mockKafkaSender, mockNewsRepository, mockNewsMapper);
//    }
//
//    @Test
//    void getNewsListByParametersTest() {
//        PageRequest pageRequest = PageRequest.of(0, 10);
//        ParamsNewsDto paramsNewsDto = new ParamsNewsDto (1, 10, "THEME", 1, LocalDate.of(2024, 6, 8));
//
//        when(mockNewsRepository.getNewsByParams(paramsNewsDto.getTheme(), paramsNewsDto.getUser_id(), paramsNewsDto.getPublication_date(), pageRequest))
//                .thenReturn(List.of(news));
//        when(mockNewsMapper.toNewsDto(any())).thenReturn(new NewsDto("TITLE", "THEME", 1, LocalDate.of(2024, 6, 8)));
//
//        List<NewsDto> result = serviceUnderTest.getNews(paramsNewsDto);
//
//        assertNotNull(result);
//        assertEquals(1, result.size());
//    }
//
//    @Test
//    void getNewsByIdTest() {
//        int newsId = 1;
//
//        when(mockNewsRepository.getByNewsId(newsId)).thenReturn(Optional.ofNullable(news));
//        when(mockNewsMapper.toNewsDto(any())).thenReturn(newsDto);
//
//        NewsDto result = serviceUnderTest.getNewsById(newsId);
//
//        assertNotNull(result);
//        assertEquals(result.getTitle(), news.getTitle());
//        assertEquals(result.getTheme(), news.getTheme());
//        assertEquals(result.getPublicationDate(), news.getPublicationDate());
//        assertEquals(result.getUserId(), news.getUserId());
//    }
//
//    @Test
//    void addNewsTest() {
//        when(mockNewsMapper.toNews(any())).thenReturn(news);
//        when(mockNewsRepository.save(any())).thenReturn(news);
//        when(mockNewsMapper.toNewsDto(any())).thenReturn(newsDto);
//
//        NewsDto result = serviceUnderTest.addNews(newNewsDto);
//
//        assertNotNull(result);
//        assertEquals(result.getTitle(), news.getTitle());
//        assertEquals(result.getTheme(), news.getTheme());
//        assertEquals(result.getPublicationDate(), news.getPublicationDate());
//        assertEquals(result.getUserId(), news.getUserId());
//    }
//
//    @Test
//    void shouldUpdateNews() {
//        // Arrange
//        int newsId = 1;
//
//        when(mockNewsRepository.findById(newsId)).thenReturn(Optional.of(news));
//        when(mockNewsMapper.toNews(any())).thenReturn(news);
//        when(mockNewsRepository.save(any())).thenReturn(new News(5, "New title", "Theme", LocalDate.of(2030, 1, 1)));
//        when(mockNewsMapper.toNewsDto(any())).thenReturn(new NewsDto("New title", "Theme", 5,  LocalDate.of(2030, 1, 1)));
//
//        // Act
//        NewsDto result = serviceUnderTest.updateNews(newsId, updateNewsDto);
//
//        // Assert
//        assertNotNull(result);
//
//        assertNotEquals(result.getTitle(), news.getTitle());
//        assertNotEquals(result.getTheme(), news.getTheme());
//        assertNotEquals(result.getPublicationDate(), news.getPublicationDate());
//        assertNotEquals(result.getUserId(), news.getUserId());
//    }

}
