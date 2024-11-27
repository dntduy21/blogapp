package com.tranduy.blog.service.impl;


import com.tranduy.blog.dto.PostDto;
import com.tranduy.blog.entity.Post;
import com.tranduy.blog.entity.User;
import com.tranduy.blog.mapper.PostMapper;
import com.tranduy.blog.repository.PostRepository;
import com.tranduy.blog.repository.UserRepository;
import com.tranduy.blog.service.PostService;
import com.tranduy.blog.util.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private PostRepository postRepository;
    private UserRepository userRepository;

    public PostServiceImpl(PostRepository postRepository,UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<PostDto> findAllPosts() {

        List<Post> posts = postRepository.findAll();
        return posts.stream().
                map((post)-> PostMapper.mapToPostDto(post)).
                collect(Collectors.toList());
    }

    @Override
    public void createPost(PostDto postDto) {
        String email = SecurityUtils.getCurrentUser().getUsername();
        User user = userRepository.findByEmail(email);
        Post post = PostMapper.mapToPost(postDto);
        post.setCreatedBy(user);
        postRepository.save(post);
    }

    @Override
    public PostDto findPostById(Long postId) {
        Post post = postRepository.findById(postId).get();
        return PostMapper.mapToPostDto(post);
    }

    @Override
    public void updatePost(PostDto postDto) {
        String email = SecurityUtils.getCurrentUser().getUsername();
        User createdBy = userRepository.findByEmail(email);
        Post post = PostMapper.mapToPost(postDto);
        post.setCreatedBy(createdBy);
        postRepository.save(post);

    }

    @Override
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }

    @Override
    public PostDto findPostByUrl(String postUrl) {
        Post post = postRepository.findByUrl(postUrl).get();
        return PostMapper.mapToPostDto(post);
    }

    @Override
    public List<PostDto> searchPost(String query) {
        List<Post> posts = postRepository.searchPosts(query);
        return posts.stream().
                map((post)->PostMapper.mapToPostDto(post)).
                collect(Collectors.toList());
    }

    @Override
    public List<PostDto> findPostByUser() {
        String email = SecurityUtils.getCurrentUser().getUsername();
        User user = userRepository.findByEmail(email);
        Long userId = user.getId();
        List<Post> posts = postRepository.findPostsByUser(userId);
        return posts.stream().map((post)->PostMapper.mapToPostDto(post)).collect(Collectors.toList());
    }

}
