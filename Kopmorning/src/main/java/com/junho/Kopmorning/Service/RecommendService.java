package com.junho.Kopmorning.Service;

import com.junho.Kopmorning.Config.SecurityUtil;
import com.junho.Kopmorning.DTO.RecommendDTO;
import com.junho.Kopmorning.Domain.Article;
import com.junho.Kopmorning.Domain.Member;
import com.junho.Kopmorning.Domain.Recommend;
import com.junho.Kopmorning.Repository.ArticleRepository;
import com.junho.Kopmorning.Repository.MemberRepository;
import com.junho.Kopmorning.Repository.RecommendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecommendService {
    private final MemberRepository memberRepository;
    private final RecommendRepository recommendRepository;
    private final ArticleRepository articleRepository;

    // 추천 조회
    public RecommendDTO allRecommend(Long id){
        Article article = articleRepository.findById(id).orElseThrow(()->
                new RuntimeException("게시물이 존재하지 않습니다."));
        List<Recommend> recommends = recommendRepository.findByArticle(article);
        int size = recommends.size();
        if(size == 0){
            return RecommendDTO.noRecommend();
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null || authentication.getPrincipal() == "anonymouse"){
            return new RecommendDTO(size, false);
        } else {
            Member member = memberRepository.findById(Long.parseLong(authentication.getName()))
                    .orElseThrow(()-> new RuntimeException("존재하지 않는 회원 입니다."));
            boolean result = recommends.stream().anyMatch(recommend -> recommend.getMember().equals(member));
            return new RecommendDTO(size, result);
        }
    }

    @Transactional
    public void createRecommend(Long id){
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(()->new RuntimeException("존재하지 않는 회원입니다."));
        Article article = articleRepository.findById(id)
                .orElseThrow(()->new RuntimeException("게시물이 존재하지 않습니다."));

        Recommend recommend = new Recommend(member, article);
        recommendRepository.save(recommend);
    }

    @Transactional
    public void deleteRecommend(Long id){
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(()->new RuntimeException("존재하지 않는 회원입니다."));
        Article article = articleRepository.findById(id)
                .orElseThrow(()->new RuntimeException("게시물이 존재하지 않습니다."));
        Recommend recommend = recommendRepository.findByArticle(article)
                .stream()
                .filter(r -> r.getMember().equals(member))
                .findFirst()
                .orElseThrow(()->new RuntimeException("추천이 없습니다."));
        recommendRepository.delete(recommend);
    }
}
