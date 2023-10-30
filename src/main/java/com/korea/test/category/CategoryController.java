package com.korea.test.category;

import com.korea.test.post.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.List;

@RequestMapping("/category")
@Controller
public class CategoryController {
    @Autowired
    private CategoryRepository categoryRepository;

    @RequestMapping("/")
    public String main(Model model) {
        List<Category> categoryList = categoryRepository.findAll();
        if (categoryList.isEmpty()) {
            saveDefault();
            return "redirect:/";
        }
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("targetCategory", categoryList.get(0));

        return "main";
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable Long id) {
        Category category= categoryRepository.findById(id).get();
        model.addAttribute("targetCategory", category);
        model.addAttribute("categoryList", categoryRepository.findAll());

        return "main";
    }

    @PostMapping("/create")
    public String categoryCreate() {
        saveDefault();
        return "redirect:/";
    }

    private void saveDefault() {
        Category category = new Category();
        category.setTitle("new category");
        categoryRepository.save(category);
    }

}
