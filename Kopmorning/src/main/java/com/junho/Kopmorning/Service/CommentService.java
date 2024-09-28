package com.junho.Kopmorning.Service;

import com.junho.Kopmorning.Config.SecurityUtil;
import com.junho.Kopmorning.DTO.CommentResponseDTO;
import com.junho.Kopmorning.Domain.Article;
import com.junho.Kopmorning.Domain.Comment;
import com.junho.Kopmorning.Domain.Member;
import com.junho.Kopmorning.Repository.ArticleRepository;
import com.junho.Kopmorning.Repository.CommentRepository;
import com.junho.Kopmorning.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;

    public List<CommentResponseDTO> getComment(Long id){
        // 1. 게시물 조회
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("게시물이 존재하지 않습니다."));

        // 2. 게시물에 속한 댓글 목록 조회
        List<Comment> commentList = commentRepository.findByArticle(article);
        if(commentList.isEmpty()){
            return Collections.emptyList();
        }

        // 3. 현재 인증 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 4. 인증 여부에 따라 댓글 처리
        if(authentication == null || authentication.getPrincipal().equals("anonymousUser")){
            // 비로그인 사용자일 경우 모든 댓글을 기본 정보로 반환
            return commentList
                    .stream()
                    .map(comment -> CommentResponseDTO.of(comment, false))
                    .collect(Collectors.toList());
        } else {
            // 로그인한 사용자일 경우
            Member member = memberRepository.findById(Long.parseLong(authentication.getName()))
                    .orElseThrow(() -> new RuntimeException("존재하지 않는 유저입니다."));

            // 댓글을 작성자 기준으로 분류
            Map<Boolean, List<Comment>> collect = commentList.stream()
                    .collect(Collectors.partitioningBy(
                            comment -> comment.getMember().equals(member)
                    ));

            // 작성자 본인의 댓글과 다른 댓글을 각각 DTO로 변환
            List<CommentResponseDTO> tCollect = collect.get(true)
                    .stream()
                    .map(t -> CommentResponseDTO.of(t, true))
                    .toList();
            List<CommentResponseDTO> fCollect = collect.get(false)
                    .stream()
                    .map(f -> CommentResponseDTO.of(f, false))
                    .toList();

            // 두 리스트를 합치고, 댓글 ID 기준으로 정렬하여 반환
            return Stream
                    .concat(tCollect.stream(), fCollect.stream())
                    .sorted(Comparator.comparing(CommentResponseDTO::getCommentId))
                    .toList();
        }
    }
    @Transactional
    public CommentResponseDTO createComment(Long id, String text){
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(()->new RuntimeException("존재하지 않는 유저입니다."));
        Article article = articleRepository.findById(id)
                .orElseThrow(()->new RuntimeException("존재하지 않는 게시판 입니다."));

        Comment comment = Comment.builder()
                .article(article)
                .member(member)
                .text(text)
                .build();
        return CommentResponseDTO.of(comment, true);
    }

    @Transactional
    public void deleteComment(Long id){
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(()->new RuntimeException("존재하지 않는 유저입니다."));
        Comment comment = commentRepository.findById(id)
                .orElseThrow(()->new RuntimeException("존재하지 않는 댓글입니다."));
        if(comment.getMember().equals(member)){
            throw new RuntimeException("댓글 작성자가 아닙니다.");
        }
        commentRepository.delete(comment);
    }
}
