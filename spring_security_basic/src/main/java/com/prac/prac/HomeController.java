package com.prac.prac;

import com.prac.prac.security.UserInfo;
import com.prac.prac.security.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.PostConstruct;

@Controller
public class HomeController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    UserInfoRepository userInfoRepository;
    @PostConstruct
    public void init(){
        UserInfo userInfo1=new UserInfo("id1" ,  passwordEncoder.encode("password"),"ADMIN");
        UserInfo userInfo2=new UserInfo("id2" ,  passwordEncoder.encode("password"),"USER");
        userInfoRepository.save(userInfo1);
        userInfoRepository.save(userInfo2);
    }
    @GetMapping("/")
    public String home(){
        return "home";
    }
}
