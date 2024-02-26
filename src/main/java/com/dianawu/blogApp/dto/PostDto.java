package com.dianawu.blogApp.dto;

import com.dianawu.blogApp.entity.User;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data //Include ToString, EqualsAndHashCode, Getter, and Setter and RequiredArgsConstructor (final fields or non-null fields)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private Long id;
    @NotEmpty (message = "Title is required")
    private String title;
    private String url;
    @NotEmpty (message = "Content is required")
    private String content;
    @NotEmpty (message = "Description is required")
    private String shortDescription;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;

    private User createdBy;
    private Set<CommentDto> comments;
}
