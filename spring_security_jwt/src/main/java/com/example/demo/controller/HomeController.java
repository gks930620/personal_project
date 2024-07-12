package com.example.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;

@Controller
@RequiredArgsConstructor
public class HomeController {


    @PostConstruct
    public void init(){
    }

    @RequestMapping("/")
    public String home(Model model){
        return "home";
    }

}
