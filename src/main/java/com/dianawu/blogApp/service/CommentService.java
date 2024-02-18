package com.dianawu.blogApp.service;

import com.dianawu.blogApp.dto.CommentDto;

public interface CommentService {
    void createComment(String postUrl, CommentDto commentDto);
}
