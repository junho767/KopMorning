package com.junho.Kopmorning.Domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Entity // JPA 엔티티 임을 선언
@Getter
@Setter
@Builder // 빌더 패턴 제공
@AllArgsConstructor // 모든 필드를 파라미터로 받는 생성자를 자동 생성
@NoArgsConstructor // 파라미터가 없는 기본 생성자를 자동 생성
@Table(name="Article")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "LONGTEXT", nullable = true)
    private String content;

    @CreationTimestamp
    @Column
    private LocalDateTime createdAt = LocalDateTime.now();

    @UpdateTimestamp
    @Column
    private LocalDateTime updatedAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "article", cascade = CascadeType.REMOVE) // CascadeType.REMOVE 는 부모 객체 삭제 시 하위 객체도 제거하게 함.
    private List<Comment> commentList = new LinkedList<>();

    @OneToMany(mappedBy = "article", cascade = CascadeType.REMOVE)
    private List<Comment> recommentList = new ArrayList<>();

    public Article(String title, String content, Member member){
        this.title = title;
        this.content = content;
        this.member = member;
    }

    public Article updateArticle(Article article, String title, String content){
        article.setTitle(title);
        article.setContent(content);

        return article;
    }
}

