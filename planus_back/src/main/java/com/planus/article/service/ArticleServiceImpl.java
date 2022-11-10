package com.planus.article.service;


import com.planus.article.dto.ArticleListResDTO;
import com.planus.article.dto.ArticleReqDTO;
import com.planus.article.dto.ArticleResDTO;
import com.planus.db.entity.Article;
import com.planus.db.entity.Trip;
import com.planus.db.entity.User;
import com.planus.db.repository.ArticleRepository;
import com.planus.db.repository.TripRepository;
import com.planus.db.repository.UserRepository;
import com.planus.util.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final TripRepository tripRepository;
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;

    @Override
    @Transactional(readOnly = true)
    public ArticleResDTO findAllArticles(Pageable pageable) {
        Page<Article> articles = articleRepository.findAllByOrderByRegDateDesc(pageable);
        System.out.println(pageable);
        System.out.println(articles);
        List<ArticleListResDTO> articleList = articles.stream().map(article -> ArticleListResDTO.toResDTO(article)).collect(Collectors.toList());

        return ArticleResDTO.toDTO(pageable, articleList);
    }

    @Override
    public long createArticle(String token, ArticleReqDTO articleReqDTO) {
        Trip trip = tripRepository.findByTripId(articleReqDTO.getTripId());
        User user = userRepository.findByUserId(tokenProvider.getUserId(token.split(" ")[1]));
        Article article = ArticleReqDTO.toEntity(articleReqDTO, trip, user);
        articleRepository.save(article);
        return article.getArticleId();
    }

//    @Override
//    @Transactional(readOnly = true)
//    public ArticleResDTO findOneArticle(long articleId) {
//        Article article = articleRepository.findById(articleId).orElseThrow(() -> new CustomException("존재하지 않는 게시글입니다."));
//
//        // entity -> ResDTO
//        // planResDTOList 만들기
//
//        ArticleResDTO.toResDTO(article, timetableList);
//
//
//
//    }
}
