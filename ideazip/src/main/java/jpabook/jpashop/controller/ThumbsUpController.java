package jpabook.jpashop.controller;

import jpabook.jpashop.service.ThumbsUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ThumbsUpController {

    private final ThumbsUpService thumbsUpService;


    @ResponseBody
    @RequestMapping("/thumbsUp/isThumbsUpChecked")
    public boolean isThumbsUpChecked(Principal principal, Long ideaId) {
        if (principal == null) {
            return false;
        }

        return thumbsUpService.isThumbsUpChecked(principal.getName(), ideaId);
    }


    @ResponseBody
    @RequestMapping("/thumbsUp/thumbsUpOnOff")   //이건 security에서  로그인한사람만 허용 ,  로그인 안한 사람은 어디로 가야하오? alert 정도로 하자
    public String thumbsUpCheck(Principal principal, Long ideaId){
        boolean thumbsUpChecked = thumbsUpService.isThumbsUpChecked(principal.getName(), ideaId);
        if(thumbsUpChecked){
            thumbsUpService.deleteThumbUp(principal.getName(), ideaId);
            return "off"; //원래 있으면 꺼야지
        }else{
            thumbsUpService.insertThumbUp(principal.getName(), ideaId);
            return "on"; //원래 없으면 켜야지
        }
    }

}
