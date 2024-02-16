package com.dianawu.blogApp.mapper;

import com.dianawu.blogApp.dto.PostDto;
import com.dianawu.blogApp.entity.Post;

public class PostMapper {

    //! map Entity -> Dto
    public static PostDto mapToDto(Post post) {

        PostDto postDto = PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .url(post.getUrl())
                .content(post.getContent())
                .shortDescription(post.getShortDescription())
                .createdOn(post.getCreatedOn())
                .updatedOn(post.getUpdatedOn())
                .build();

        return postDto;

    }

    //! map DTO -> Entity
    public static Post mapToEntity(PostDto postDto) {

        Post post = Post.builder()
                .id(postDto.getId())
                .title(postDto.getTitle())
                .url(postDto.getUrl())
                .content(postDto.getContent())
                .shortDescription(postDto.getShortDescription())
                .createdOn(postDto.getCreatedOn())
                .updatedOn(postDto.getUpdatedOn())
                .build();

        return post;

    }


}
