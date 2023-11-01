package com.korea.test.category;

import com.korea.test.post.Post;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100)
    private String title;
    @Column(nullable = false)
    private LocalDateTime createDate;

    @ManyToOne
    private Category parent;

    @OneToMany(mappedBy = "category", cascade = CascadeType.REMOVE)
    private List<Post> postList;

    @OneToMany(mappedBy = "parent")
    private List<Category> childList;
}
