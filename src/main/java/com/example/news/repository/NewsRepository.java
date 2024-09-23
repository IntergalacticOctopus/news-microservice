package com.example.news.repository;

import com.example.news.model.News;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Integer> {

    @Query("SELECT n FROM News AS n WHERE " +
            "(LOWER(n.theme.themeName) = LOWER(:theme) OR :theme IS NULL) AND " +
            "(n.userId = :userId OR :userId IS NULL) AND " +
            "(n.publicationDate = :publicationDate OR CAST(:publicationDate AS DATE) IS NULL)")
    List<News> getNewsByParams(@Param("theme") String theme,
                               @Param("userId") Integer userId,
                               @Param("publicationDate") LocalDate publicationDate,
                               Pageable pageable);

    Optional<News> getByNewsId(Integer news_id);
}

