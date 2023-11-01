package com.korea.test.category;


import com.korea.test.post.Post;
import com.korea.test.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@RequestMapping("/category")
@RequiredArgsConstructor
@Controller
public class CategoryController {

    private final PostService postService;
    private final CategoryService categoryService;

    @RequestMapping("/")
    public String main(Model model) {

        List<Post> postList = postService.getPostList();
        List<Category> categoryList = categoryService.getCategoryList();

        if (categoryList.isEmpty()) {
            return "redirect:/";
        }

        if (postList.isEmpty()) {
            postService.saveDefaultPost(categoryList.get(0));
            return "redirect:/";
        }

        model.addAttribute("categoryList", categoryList);
        model.addAttribute("postList", postList);
        model.addAttribute("targetPost", postList.get(0));
        model.addAttribute("targetCategory", categoryList.get(0));

        return "main";
    }

    @PostMapping("/createCategory")
    public String createCategory() {
        Category category = categoryService.saveDefaultCategory();
        postService.saveDefaultPost(category);
        return "redirect:/";
    }

    @RequestMapping("/{categoryId}")
    public String categoryDetail(@PathVariable("categoryId") Long categoryId, Model model) {

        Category category = categoryService.getCategoryById(categoryId);
        List<Category> categoryList = categoryService.getCategoryList();

        model.addAttribute("categoryList", categoryList);
        model.addAttribute("postList", category.getPostList());
        model.addAttribute("targetPost", category.getPostList().get(0));
        model.addAttribute("targetCategory" , category);

        return "main";
    }

}
