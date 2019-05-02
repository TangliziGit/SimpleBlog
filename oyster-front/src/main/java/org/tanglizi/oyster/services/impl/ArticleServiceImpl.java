package org.tanglizi.oyster.services.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.tanglizi.oyster.dao.queries.ArticleQueries;
import org.tanglizi.oyster.dto.entities.Article;
import org.tanglizi.oyster.dao.repositories.ArticleRepository;
import org.tanglizi.oyster.services.ArticleService;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Resource
    private ArticleRepository articleRepository;

    @Override
    public Page<Article> findArticles(int pageNumber, int limit) {
        return articleRepository.findAll(
                PageRequest.of(pageNumber, limit, Sort.by("createTimestamp")));
    }

    @Override
    public Article findArticleById(Integer id) {
        return articleRepository.findById(id).orElse(null);
    }

    @Override
    public long findArticleCount() {
        return articleRepository.count();
    }

    @Override
    public Integer findNextArticleId(Integer articleId) {
        return articleRepository.findNextArticleId(articleId);
    }

    @Override
    public Integer findPrevArticleId(Integer articleId) {
        return articleRepository.findPrevArticleId(articleId);
    }

    @Override
    public Page<Article> findArticlesByLikeList(List<String> titleLkeList, List<String> contentLikeList,
                                                int pageNumber, int limit) {
        return articleRepository.findAll(new ArticleQueries(){{
            setCombineLogicType(LogicType.OR);
            setTitleLikeList(titleLkeList);
            setContentLikeList(contentLikeList);
        }}.toSpec(), PageRequest.of(pageNumber, limit, Sort.by("createTimestamp")));
    }
}