package com.example.demo.web;

import com.example.demo.config.auth.LoginUser;
import com.example.demo.config.auth.dto.SessionUser;
import com.example.demo.service.PostsService;
import com.example.demo.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {
    private final PostsService postsService;
    private  final HttpSession httpSession;
    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {  //LoginUserArgumetnResolver에 의해 httpsession.getAttribute("user")가 null이면 null, 아니면 그 객체 전달.
        model.addAttribute("posts", postsService.findAllDesc());
        if (user != null) {
            model.addAttribute("userName", user.getName());
        }
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);

        return "posts-update";
    }
}
