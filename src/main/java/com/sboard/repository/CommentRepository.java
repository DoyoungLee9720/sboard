package com.sboard.repository;

import com.sboard.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    //List<Comment> findByParent(int parent);
}
