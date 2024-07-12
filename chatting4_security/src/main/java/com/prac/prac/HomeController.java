package com.prac.prac;

import com.prac.prac.security.UserInfo;
import com.prac.prac.security.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private  final PasswordEncoder passwordEncoder;
    private  final UserInfoRepository userInfoRepository;

    @PostConstruct
    public void init(){
        UserInfo userInfo1=new UserInfo("id1" ,  passwordEncoder.encode("password"),"ADMIN");
        UserInfo userInfo2=new UserInfo("id2" ,  passwordEncoder.encode("password"),"USER");
        userInfoRepository.save(userInfo1);
        userInfoRepository.save(userInfo2);
    }

    @RequestMapping("/")
    public String home(){
        return "home";
    }
}
