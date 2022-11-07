package com.planus.community.service;

import com.planus.community.dto.CommentDTO;
import com.planus.community.dto.CommentResDTO;
import com.planus.db.entity.Article;
import com.planus.db.entity.Comment;
import com.planus.db.repository.ArticleRepository;
import com.planus.db.repository.CommentRepository;
import com.planus.db.repository.UserRepository;
import com.planus.util.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    private TokenProvider tokenProvider;
    private ArticleRepository articleRepository;
    private CommentRepository commentRepository;
    private UserRepository userRepository;

    @Autowired
    public CommentServiceImpl(TokenProvider tokenProvider, ArticleRepository articleRepository, CommentRepository commentRepository, UserRepository userRepository) {
        this.tokenProvider = tokenProvider;
        this.articleRepository = articleRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }

    @Override
    public long addComment(String token, long articleId, String content) {
        Article article = articleRepository.findByArticleId(articleId);

        Comment comment = Comment.builder()
                .content(content)
                .regDate(LocalDateTime.now())
                .article(article)
                .user(userRepository.findByUserId(tokenProvider.getUserId(token.split(" ")[1])))
                .build();

        commentRepository.save(comment);

        return comment.getCommentId();
    }

    @Override
    @Transactional
    public long modifyComment(String token, long commentId, String content) {
        if (tokenProvider.getUserId(token.split(" ")[1])==commentRepository.findByCommentId(commentId).getUser().getUserId()){
            Comment comment = commentRepository.findByCommentId(commentId);
            comment.updateComment(content, LocalDateTime.now());
            commentRepository.save(comment);
            return commentId;
        }else{
            return commentId;
        }
    }

    @Override
    public CommentResDTO getArticleComment(long articleId, Pageable pageable) {
        Page<Comment> commentList = commentRepository.findByArticleArticleIdOrderByRegDateDesc(articleId, pageable);

        return CommentResDTO.builder()
                .currentPage(commentList.getNumber())
                .totalPage(commentList.getTotalPages())
                .commentList(setCommentList(commentList))
                .build();
    }

    @Override
    public CommentResDTO getMyComment(String token, Pageable pageable) {
        Page<Comment> commentList = commentRepository.findByUserUserIdOrderByRegDateDesc(tokenProvider.getUserId(token.split(" ")[1]), pageable);

        return CommentResDTO.builder()
                .currentPage(commentList.getNumber())
                .totalPage(commentList.getTotalPages())
                .commentList(setCommentList(commentList))
                .build();
    }

    private List<CommentDTO> setCommentList(Page<Comment> commentList) {
        List<CommentDTO> commentDTOList = new ArrayList<>();

        for(Comment comment : commentList){
            CommentDTO commentDTO = CommentDTO.builder()
                    .commentId(comment.getCommentId())
                    .content(comment.getContent())
                    .regDate(comment.getRegDate().toString())
                    .articleId(comment.getArticle().getArticleId())
                    .userId(comment.getUser().getUserId())
                    .build();

            commentDTOList.add(commentDTO);
        }

        return commentDTOList;
    }

    @Override
    public void delComment(String token, long commentId) {
        if (tokenProvider.getUserId(token.split(" ")[1])==commentRepository.findByCommentId(commentId).getUser().getUserId()){
            commentRepository.deleteByCommentId(commentId);
        }else{
            return;
        }
    }
}
