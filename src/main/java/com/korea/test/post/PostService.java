package com.korea.test.post;

import com.korea.test.category.Category;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    public void saveDefaultPost(Category category) {
        Post post = new Post();
        post.setTitle("new title..");
        post.setContent("");
        post.setCreateDate(LocalDateTime.now());
        post.setCategory(category);

        postRepository.save(post);
    }

    public List<Post> getPostList() {
        return postRepository.findAll();
    }

    public Post getPostById(Long id) {
        Optional<Post> op = postRepository.findById(id);
        if(op.isPresent()) {
            return op.get();
        }

        throw new IllegalArgumentException("없는 게시물번호입니다.");
    }

    public void save(Post post) {
        postRepository.save(post);
    }

    public void deleteById(Long id) {
        postRepository.deleteById(id);
    }

    public List<Post> getPostListByCategory(Category targetcategory) {
        return postRepository.findByCategory(targetcategory);
    }


    public List<Post> getPostListSortedByTitleAsc() {
        return postRepository.findAllByOrderByTitleAsc();
    }

    public List<Post> getPostListSortedByTitleDesc() {
        return postRepository.findAllByOrderByTitleDesc();
    }
}
