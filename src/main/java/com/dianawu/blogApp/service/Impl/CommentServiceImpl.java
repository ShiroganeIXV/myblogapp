package com.dianawu.blogApp.service.Impl;

import com.dianawu.blogApp.dto.CommentDto;
import com.dianawu.blogApp.entity.Comment;
import com.dianawu.blogApp.entity.Post;
import com.dianawu.blogApp.mapper.CommentMapper;
import com.dianawu.blogApp.repository.CommentRepository;
import com.dianawu.blogApp.repository.PostRepository;
import com.dianawu.blogApp.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    CommentRepository commentRepository;
    PostRepository postRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
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
        return comments.stream()
                .map(CommentMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
