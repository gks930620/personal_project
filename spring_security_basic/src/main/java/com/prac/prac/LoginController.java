package com.prac.prac;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {


    @RequestMapping("/login")   //loginPageURl에서 지정한 url
    public String loginPage() {
        return "login";
    }

    @RequestMapping("/manage")  //ROLE_ADMIN 이 없으면 들어올 수 없어요..
    public String manage() {
        return "manage";
    }

    @RequestMapping("/hch")
    public String hch()  //  인증(로그인)만 하면 되고
    {
        return "hch";
    }

    @RequestMapping("/anything")  // 그 외것들은 그냥 접근 가능.      사실 "/"도  인증필요없이 접근가능
    public String anything() {
        return "anything";
    }


//    // 로그인정보를 서버에서 사용할 때는 이렇게 사용함
//    @RequestMapping("/someURL")
//    public String someURL(Principal principal){
//        String id = principal.getName();   //메소드이름은 getName()이지만  사실상 로그인할 때 썻던 id를 줍니다
//
//        //id로 DB조회해서 회원정보 얻는 등 이것저것 할 수 있다.
//        return "some";
//    }

}
