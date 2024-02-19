package com.dianawu.blogApp.service.Impl;

import com.dianawu.blogApp.dto.CommentDto;
import com.dianawu.blogApp.entity.Comment;
import com.dianawu.blogApp.entity.Post;
import com.dianawu.blogApp.entity.User;
import com.dianawu.blogApp.mapper.CommentMapper;
import com.dianawu.blogApp.repository.CommentRepository;
import com.dianawu.blogApp.repository.PostRepository;
import com.dianawu.blogApp.repository.UserRepository;
import com.dianawu.blogApp.service.CommentService;
import com.dianawu.blogApp.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;

    private UserRepository userRepository;

    @Autowired
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
        return comments.stream()
                .map(CommentMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    @Override
    public List<CommentDto> findCommentsByPost() {
        String email = SecurityUtils.getCurrentUser().getUsername();
        User createBy = userRepository.findByEmail(email);
        Long userId = createBy.getId();
        List<Comment> comments = commentRepository.findCommentsByPost(userId);

        return comments.stream()
                .map(CommentMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
