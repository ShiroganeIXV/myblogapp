package com.dianawu.blogApp.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter@Setter
@Builder //The builder pattern is a design pattern that allows for the creation of complex objects step by step. It separates the construction of an object from its representation.
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "posts")
public class Post {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    private String url;

    @Lob //The @Lob annotation is used to specify that the property or field should be persisted as a large object to a database-supported large object type.
    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String content;

    private String shortDescription;

    @CreationTimestamp //This annotation is used to set the value of the annotated field to the current timestamp when the row is inserted
    private LocalDateTime createdOn;

    @UpdateTimestamp
    private LocalDateTime updatedOn;

}
