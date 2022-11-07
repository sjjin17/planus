package com.planus.article.service;


import com.planus.article.dto.ArticleListResDTO;
import com.planus.article.dto.ArticleReqDTO;
import com.planus.db.entity.Article;
import com.planus.db.entity.Trip;
import com.planus.db.entity.User;
import com.planus.db.repository.ArticleRepository;
import com.planus.db.repository.TripRepository;
import com.planus.db.repository.UserRepository;
import com.planus.util.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final TripRepository tripRepository;
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    @Override
    public List<ArticleListResDTO> findAllArticles() {
        List<Article> articles = articleRepository.findAll();
        System.out.println(articles);
        return articles.stream().map(article -> ArticleListResDTO.toResDTO(article)).collect(Collectors.toList());
    }

    @Override
    public String createArticle(ArticleReqDTO articleReqDTO) {
        // user검증
        System.out.println(articleReqDTO);
        // 내 모든 여행이 조회 가능해야 함 -> 따로 api 만들어야 함
        // articleReqDTO -> article
        System.out.println(articleReqDTO.getTripId());
        Trip trip = tripRepository.findByTripId(articleReqDTO.getTripId());
        User user = userRepository.findByUserId(articleReqDTO.getUserId());
        System.out.println(trip);
        System.out.println(user);
        Article article = ArticleReqDTO.toEntity(articleReqDTO,trip, user);
        System.out.println(2);
        System.out.println(article);
        System.out.println(3);
        articleRepository.save(article);
        return article.getTitle();
    }
}
