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
import com.planus.exception.CustomException;
import com.planus.util.TokenProvider;
import lombok.RequiredArgsConstructor;
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
    public List<ArticleListResDTO> findAllArticles() {
        List<Article> articles = articleRepository.findAll();
        System.out.println(articles);
        List<ArticleListResDTO> articleResDTO = articles.stream().map(article -> ArticleListResDTO.toResDTO(article)).collect(Collectors.toList());
        System.out.println(articleResDTO);
        return articleResDTO;
    }

    @Override
    public long createArticle(ArticleReqDTO articleReqDTO) {
        // 내 모든 여행이 조회 가능해야 함 -> 따로 api 만들어야 함
        // articleReqDTO -> article
        Article article = ArticleReqDTO.toEntity(articleReqDTO);
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
