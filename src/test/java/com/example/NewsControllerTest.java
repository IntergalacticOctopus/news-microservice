package com.example;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.List;

import com.example.news.controller.NewsController;
import com.example.news.mapper.NewsMapper;
import com.example.news.model.NewNewsDto;
import com.example.news.model.News;
import com.example.news.model.NewsDto;
import com.example.news.service.NewsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = NewsController.class)
public class NewsControllerTest {


    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Mock
    private NewsMapper newsMapper;

    @MockBean
    private NewsService newsService;

    NewNewsDto newNewsDto = new NewNewsDto();
    News news;
    NewsDto newsDto = new NewsDto();
    ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        newNewsDto.setTitle("TITLE");
        newNewsDto.setTheme("THEME");
        newNewsDto.setPublicationDate(LocalDate.of(2024, 6, 8));

        news = new News(1L, 1L, "TITLE", "THEME", LocalDate.of(2024, 6, 8));

        newsDto.setTitle("TITLE");
        newsDto.setTheme("THEME");
        newsDto.setUserId(1L);
        newsDto.setPublicationDate(LocalDate.of(2024, 6, 8));

    }

    @Test
    public void testGetNews_Successful() throws Exception {
        int page = 1;
        int size = 10;

        when(newsService.getNews(any())).thenReturn(List.of(newsDto));

        mvc.perform(MockMvcRequestBuilders.get("/news?page={page}&size={size}", page, size))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

        verify(newsService, times(1)).getNews(any());
    }

    @Test
    public void testGetNewsById_Successful() throws Exception {
        Long newsId = 1L;

        when(newsService.getNewsById(newsId)).thenReturn(newsDto);

        mvc.perform(MockMvcRequestBuilders.get("/news/{news_id}", newsId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(newsDto.getTitle()))
                .andDo(MockMvcResultHandlers.print());

        verify(newsService, times(1)).getNewsById(newsId);
    }

    @Test
    public void testAddNews_Successful() throws Exception {
        when(newsService.addNews(newNewsDto)).thenReturn(newsDto);

        mvc.perform(MockMvcRequestBuilders.post("/news")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newNewsDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(newsDto.getTitle()))
                .andDo(MockMvcResultHandlers.print());

        verify(newsService, times(1)).addNews(newNewsDto);
    }

    @Test
    void testDeleteNews() throws Exception {
        int newsId = 7;
        mvc.perform(MockMvcRequestBuilders.delete("/news/{news_id}", newsId))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

}
