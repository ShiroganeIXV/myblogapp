package com.dianawu.blogApp.dto;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class CommentDto {
    private Long id;

    @NotEmpty (message = "Name cannot be empty")
    private String name;

    @NotEmpty (message = "Email cannot be empty")
    @Email // validate email pattern
    private String email;

    @NotEmpty (message = "Comment body cannot be empty")
    private String content;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;

    Long postId;

}
