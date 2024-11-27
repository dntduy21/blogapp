package com.tranduy.blog.service;



import com.tranduy.blog.dto.CommentDto;

import java.util.List;

public interface CommentService {
    void createComment(String url, CommentDto commentDto);

    List<CommentDto> findAllComments();

    void deleteComment(Long commentId);
    List<CommentDto> findCommentsByPost();
}
