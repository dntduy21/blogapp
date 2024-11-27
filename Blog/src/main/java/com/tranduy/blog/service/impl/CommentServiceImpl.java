package com.tranduy.blog.service.impl;


import com.tranduy.blog.dto.CommentDto;
import com.tranduy.blog.entity.Comment;
import com.tranduy.blog.entity.Post;
import com.tranduy.blog.entity.User;
import com.tranduy.blog.mapper.CommentMapper;
import com.tranduy.blog.repository.CommentRepository;
import com.tranduy.blog.repository.PostRepository;
import com.tranduy.blog.repository.UserRepository;
import com.tranduy.blog.service.CommentService;
import com.tranduy.blog.util.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private UserRepository userRepository;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository,UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void createComment(String postUrl, CommentDto commentDto) {

        Post post = postRepository.findByUrl(postUrl).get();
        Comment comment = CommentMapper.mapToEntity(commentDto);
        comment.setPost(post);
        commentRepository.save(comment);
    }

    @Override
    public List<CommentDto> findAllComments() {
        List<Comment> comments = commentRepository.findAll();
        return comments.stream().map(CommentMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    @Override
    public List<CommentDto> findCommentsByPost() {
        String email = SecurityUtils.getCurrentUser().getUsername();
        User createdBy =userRepository.findByEmail(email);
        Long userId = createdBy.getId();
        List<Comment> comments = commentRepository.findCommentByPost(userId);
        return comments.stream().map((comment)->CommentMapper.mapToDto(comment)).collect(Collectors.toList());
    }


}
