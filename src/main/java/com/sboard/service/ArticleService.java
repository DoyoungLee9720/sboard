package com.sboard.service;

import com.sboard.dto.ArticleDTO;
import com.sboard.entity.Article;
import com.sboard.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
@Service
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final ModelMapper modelMapper;
    public int insertArticle(ArticleDTO articleDTO){
        // 첨부 파일 객체(MultipartFile) 가져오기
        List<MultipartFile> files = articleDTO.getFiles();
        log.info("files size : " + files.size());

        // ModelMapper를 이용해서 DTO를 Entity로 변환
        Article article = modelMapper.map(articleDTO, Article.class);
        log.info(article);

        // 저장
        Article savedArticle = articleRepository.save(article);
        return savedArticle.getNo();
    }
    public ArticleDTO selectArticle(int no){
        return null;
    }

    public List<ArticleDTO> selectArticleAll(){
        // 모든 Article 엔티티를 가져옴
        List<Article> articles = articleRepository.findAll();

        // ModelMapper를 이용해서 Entity 리스트를 DTO 리스트로 변환
        List<ArticleDTO> articleDTOs = articles.stream()
                .map(article -> modelMapper.map(article, ArticleDTO.class)) // ModelMapper 사용
                .collect(Collectors.toList());
        return articleDTOs;
    }
    public void updateArticle(ArticleDTO articleDTO){

    }
    public void deleteArticle(int no){

    }
}
