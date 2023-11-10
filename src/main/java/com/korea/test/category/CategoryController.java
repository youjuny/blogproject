package com.korea.test.category;


import com.korea.test.post.Post;
import com.korea.test.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RequestMapping("/category")
@RequiredArgsConstructor
@Controller
public class CategoryController {

    private final PostService postService;
    private final CategoryService categoryService;

    @RequestMapping("/{categoryId}")
    public String categoryDetail(@PathVariable("categoryId") Long categoryId, Model model) {

        Category category = categoryService.getCategoryById(categoryId);
        List<Category> categoryList = categoryService.getParentCategoryList();

        List<Category> notCheckableList = categoryService.getNotCheckableCategoryList(category, new ArrayList<>());

        model.addAttribute("categoryList", categoryList);
        model.addAttribute("postList", category.getPostList());
        model.addAttribute("targetPost", category.getPostList().get(0));
        model.addAttribute("targetCategory" , category);
        model.addAttribute("notCheckableList", notCheckableList);

        return "main";
    }
    @RequestMapping("/")
    public String main(Model model) {

        List<Post> postList = postService.getPostList();
        List<Category> categoryList = categoryService.getParentCategoryList();
        System.out.println(categoryList.size());
        if(categoryList.isEmpty()) {
            return "redirect:/";
        }

        if(postList.isEmpty()) {
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
        return "redirect:/category/" + category.getId();
    }

    @RequestMapping("/add-group")
    public String addGroup(Long categoryId) {
        Category category = categoryService.saveGroupCategory(categoryId);
        postService.saveDefaultPost(category);
        return "redirect:/category/" + category.getId();
    }

    @PostMapping("/categoryDelete")
    public String categoryDelete(Long id) {
        if (id != null) {
            this.categoryService.deleteById(id);
            return "redirect:/";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/categoryUpdate")
    public String categoryUpdate(Long id, String title) {
        Category category = categoryService.getCategoryById(id);

        if(title.trim().length() == 0 ) {
            title = "카테고리 제목없음";
        }

        category.setTitle(title);

        categoryService.save(category);
        return "redirect:/category/" + id;
    }


    @PostMapping("/move")
    public String moveCategory(Long moveCategoryId, Long destinationId) {
        categoryService.moveCategoryTo(moveCategoryId, destinationId);
        return "redirect:/category/" + moveCategoryId;

    }


}
