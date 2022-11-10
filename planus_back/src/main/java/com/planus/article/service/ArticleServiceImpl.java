package com.planus.article.service;


import com.planus.article.dto.*;
import com.planus.db.entity.Area;
import com.planus.db.entity.Article;
import com.planus.db.entity.Trip;
import com.planus.db.entity.User;
import com.planus.db.repository.*;
import com.planus.exception.CustomException;
import com.planus.db.repository.ArticleRepository;
import com.planus.db.repository.TripRepository;
import com.planus.db.repository.UserRepository;
import com.planus.util.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    private final AreaRepository areaRepository;
    private final ArticleLikeRepository articleLikeRepository;

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

    @Override
    public SearchResDTO getArticleListByTitle(String token, String title, Pageable pageable) {
        Page<Article> articleList = articleRepository.findByTitleContains(title, pageable);

        return SearchResDTO.builder()
                .currentPage(articleList.getNumber())
                .totalPage(articleList.getTotalPages())
                .articleList(setArticleList(token, articleList))
                .build();
    }

    @Override
    public SearchResDTO getArticleListByArea(String token, int[] area, Pageable pageable) {
        Page<Article> articleList = articleRepository.findByArea(area, pageable);

        return SearchResDTO.builder()
                .currentPage(articleList.getNumber())
                .totalPage(articleList.getTotalPages())
                .articleList(setArticleList(token, articleList))
                .build();
    }

    private List<SearchDTO> setArticleList(String token, Page<Article> articleList) {
        long userId = -1;
        if(token!=null){
            userId = tokenProvider.getUserId(token.split(" ")[1]);
        }

        List<SearchDTO> searchDTOList = new ArrayList<>();

        for (Article article : articleList){
            long articleId = article.getArticleId();
            long tripId = article.getTrip().getTripId();

            SearchDTO searchDTO = SearchDTO.builder()
                    .articleId(articleId)
                    .tripId(article.getTrip().getTripId())
                    .areaList(areaRepository.findAllByTripAreaList_Trip_TripId(tripId).stream().map(Area::getSiName).collect(Collectors.toList()))
                    .imageUrl(areaRepository.findTop1ByTripAreaList_Trip_TripId(tripId).getImageUrl())
                    .period(article.getTrip().getPeriod())
                    .userId(article.getUser().getUserId())
                    .name(article.getUser().getName())
                    .title(article.getTitle())
                    .regDate(article.getRegDate().toString())
                    .hits(article.getHits())
                    .likes(article.getArticleLikeList().size())
                    .isLiked(articleLikeRepository.existsByArticleArticleIdAndUserUserId(articleId,userId))
                    .build();

            searchDTOList.add(searchDTO);
        }

        return searchDTOList;
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
