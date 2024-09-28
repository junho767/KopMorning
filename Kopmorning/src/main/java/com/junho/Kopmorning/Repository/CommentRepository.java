package com.junho.Kopmorning.Repository;

import com.junho.Kopmorning.Domain.Article;
import com.junho.Kopmorning.Domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long > {
    List<Comment> findByArticle(Article article);
}
