package com.korea.test.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@Controller
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @RequestMapping("/")
    public String main(Model model) {
        //1. DB에서 데이터 꺼내오기
        List<Post> postList = postRepository.findAll();

        if (postList.isEmpty()) {
            saveDefault();
            return "redirect:/";
        }

        //2. 꺼내온 데이터를 템플릿으로 보내기
        model.addAttribute("postList", postList);
        model.addAttribute("targetPost", postList.get(0));

        return "main";
    }

    @PostMapping("/write")
    public String write() {
        saveDefault();
        return "redirect:/";
    }

    private void saveDefault() {
        Post post = new Post();
        post.setTitle("new title..");
        post.setContent("");
        post.setCreateDate(LocalDateTime.now());

        postRepository.save(post);
    }



    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable Long id) {
        Post post = postRepository.findById(id).get();
        model.addAttribute("targetPost", post);
        model.addAttribute("postList", postRepository.findAll());

        return "main";
    }
    @PostMapping("/update")
    public String update(Long id, String title, String content) {
        Post post = postRepository.findById(id).get();

        if(title.trim().length() == 0 ) {
            title = "제목없음";
        }

        post.setTitle(title);
        post.setContent(content);

        postRepository.save(post);
        return "redirect:/detail/" + id;
    }

    @PostMapping("/delete")
    public String delete(Long id) {
        this.postRepository.deleteById(id); ;
        return "redirect:/";
    }

}
