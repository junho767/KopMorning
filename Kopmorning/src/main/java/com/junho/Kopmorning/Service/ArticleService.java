package com.junho.Kopmorning.Service;


import com.junho.Kopmorning.Config.SecurityUtil;
import com.junho.Kopmorning.DTO.ArticleListResponseDTO;
import com.junho.Kopmorning.DTO.ArticleResponseDTO;
import com.junho.Kopmorning.DTO.PageResponseDTO;
import com.junho.Kopmorning.Domain.Article;
import com.junho.Kopmorning.Domain.Member;
import com.junho.Kopmorning.Repository.ArticleRepository;
import com.junho.Kopmorning.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
// 해당 메서드나 클래스가 데이터베이스 트랜잭션을 읽기 전용으로 처리함을 나타냅니다
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;

    public List<ArticleListResponseDTO> findAll(){
        List<Article> articles = articleRepository.findAll();
        return articles.stream().map(ArticleListResponseDTO::of).collect(Collectors.toList());
    }

    public Page<PageResponseDTO> pageArticle(int pageNum){
        return articleRepository.searchAll(PageRequest.of(pageNum -1 ,20));
    }

    public ArticleResponseDTO getArticle(Long id){
        Article article = articleRepository.findById(id).orElseThrow(()-> new RuntimeException("존재하지 않는 게시물 입니다."));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || authentication.getPrincipal() == "anonymous"){
            return ArticleResponseDTO.of(article, false);
        } else{
            Member member = memberRepository.findById(Long.parseLong(authentication.getName())).orElseThrow(()->new RuntimeException("존재하지 않는 회원 입니다."));
            boolean result = article.getMember().equals(member);
            return ArticleResponseDTO.of(article, result);
        }
    }

    @Transactional
    public ArticleResponseDTO postArticle(String title, String content){
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(()->new RuntimeException("로그인이 안되어 있습니다,"));
        Article article = new Article(title,content,member);
        return ArticleResponseDTO.of(articleRepository.save(article),true);
    }

    public Member CurrentMember(){
        return memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(()-> new RuntimeException("로그인 유저 정보가 없습니다."));
    }

    public Article authorizationArticleWriter(Long id){
        Member member = CurrentMember();
        Article article = articleRepository.findById(id).orElseThrow(()->
                new RuntimeException("게시물이 존재하지 않습니다."));
        if(!article.getMember().equals(member)){
            throw new RuntimeException("작성자가 아닙니다.");
        }
        return article;
    }

    @Transactional
    public ArticleResponseDTO updateArticle(Long id,String title, String content){
        Article article = authorizationArticleWriter(id);
        return ArticleResponseDTO.of(articleRepository.save(article.updateArticle(article, title, content)),true);
    }

    @Transactional
    public void deleteArticle(Long id){
        Article article = authorizationArticleWriter(id);
        articleRepository.delete(article);
    }
}
