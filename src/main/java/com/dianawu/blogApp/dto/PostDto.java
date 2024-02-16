package com.dianawu.blogApp.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data //Include ToString, EqualsAndHashCode, Getter, and Setter and RequiredArgsConstructor (final fields or non-null fields)
@Builder
public class PostDto {
    private Long id;
    private String title;
    private String url;
    private String content;
    private String shortDescription;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
}
