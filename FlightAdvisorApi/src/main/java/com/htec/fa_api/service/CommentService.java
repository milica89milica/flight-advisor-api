package com.htec.fa_api.service;

import com.htec.fa_api.model.Comment;
import com.htec.fa_api.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<Comment> getAll() {
        return commentRepository.getAllByActive((byte) 1);
    }
}
