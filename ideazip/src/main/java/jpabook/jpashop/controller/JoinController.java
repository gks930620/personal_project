package jpabook.jpashop.controller;

import jpabook.jpashop.dto.MemberFormDto;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class JoinController {


    //이용약관 따윈 없다.


    private final MemberService memberService;


    @GetMapping("/join/joinForm")
    public String memberForm(){   // 굳이 이메일인증같은거 필요없다.

        return "join/joinForm";
    }

    @PostMapping("/join/regist")
    public String memberJoinPost(MemberFormDto memberFormDto) throws  Exception{
        memberService.insertMember(memberFormDto);
        return "redirect:/";   //보던곳으로 가도록 수정
    }


    /**
     *
     * @param id :  The user who will join our site want to use for login
     * @return false, if id is present. It means  the user can't use this id
     *          true, if id is not presend. It means the user can use this id
     */
    @ResponseBody
    @RequestMapping("/join/idCheck")
    public boolean canUseId(String id){
        boolean canUse= !memberService.idCheck(id);
        System.out.println("canUse = " + canUse);
        return canUse;
    }

}
