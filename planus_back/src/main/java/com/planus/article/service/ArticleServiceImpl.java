package com.planus.article.service;


import com.planus.article.dto.*;
import com.planus.db.entity.Area;
import com.planus.db.entity.Article;
import com.planus.db.entity.Trip;
import com.planus.db.entity.User;
import com.planus.db.repository.*;
import com.planus.exception.CustomException;
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

    @Override
    public SearchResDTO getArticleListByTitle(String title, Pageable pageable) {
        Page<Article> articleList = articleRepository.findByTitleContains(title, pageable);

        return SearchResDTO.builder()
                .currentPage(articleList.getNumber())
                .totalPage(articleList.getTotalPages())
                .articleList(setArticleList(articleList))
                .build();
    }

    @Override
    public SearchResDTO getArticleListByArea(int[] area, Pageable pageable) {
        Page<Article> articleList = articleRepository.findByArea(area, pageable);

        return SearchResDTO.builder()
                .currentPage(articleList.getNumber())
                .totalPage(articleList.getTotalPages())
                .articleList(setArticleList(articleList))
                .build();
    }

    private List<SearchDTO> setArticleList(Page<Article> articleList) {
        List<SearchDTO> searchDTOList = new ArrayList<>();

        for (Article article : articleList){
            long articleId = article.getArticleId();
            long tripId = article.getTrip().getTripId();
            long userId = article.getUser().getUserId();

            SearchDTO searchDTO = SearchDTO.builder()
                    .articleId(articleId)
                    .tripId(article.getTrip().getTripId())
                    .areaList(areaRepository.findAllByTripAreaList_Trip_TripId(tripId).stream().map(Area::getSiName).collect(Collectors.toList()))
                    .imageUrl(areaRepository.findTop1ByTripAreaList_Trip_TripId(tripId).getImageUrl())
                    .period(article.getTrip().getPeriod())
                    .userId(userId)
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
