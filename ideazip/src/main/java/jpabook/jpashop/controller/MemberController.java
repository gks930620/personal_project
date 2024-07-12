package jpabook.jpashop.controller;


import jpabook.jpashop.dto.*;
import jpabook.jpashop.exception.NewPasswordNotMatchedException;
import jpabook.jpashop.exception.PasswordWrongException;
import jpabook.jpashop.service.IdeaService;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    //이용약관 따윈 없다.


    private final MemberService memberService;



    //여기서부터는 모두 security에 등록하자고   /member/memberView/ (memberInfo, 작성글,작성댓글,좋아요 글)
    @RequestMapping("/member/memberView")
    public String memberView(){
        return "member/memberView";
    }


    @RequestMapping("/member/memberView/info")
    public String memberViewInfo(Principal principal, Model model){
        String id = principal.getName(); //name이지만 id라는거 잊지말자.
        MemberViewDto member = memberService.getMember(id);
        model.addAttribute("member",member);
        return "member/memberViewInfo";
    }

    @RequestMapping("/member/memberView/writing")
    public String memberViewWriting(Model model, @PageableDefault(value = 10) Pageable pageable, Principal principal){
        Page<IdeaListDto> pageResults = memberService.memberIdeaListDto(pageable, principal.getName());
        List<IdeaListDto> ideaList = pageResults.getContent();
        model.addAttribute("page", pageResults);
        model.addAttribute("ideaList", ideaList);
        return "member/memberViewWriting";
    }

    @RequestMapping("/member/memberView/reply")
    public String memberViewReply( @PageableDefault(value = 10)Pageable pageable,Principal principal,Model model){
        Page<ReplyMemberListDto> pageResults = memberService.memberReplyListDto(pageable, principal.getName());
        List<ReplyMemberListDto> replyList = pageResults.getContent();
        model.addAttribute("page", pageResults);
        model.addAttribute("replyList", replyList);
        return "member/memberViewReply";
    }
    @RequestMapping("/member/memberView/thumbsUp")
    public String memberViewThumbsUp( @PageableDefault(value = 10)Pageable pageable,Principal principal,Model model){
        Page<ThumbsUpMemberListDto> pageResults = memberService.memberThumbsUpListDto(pageable, principal.getName());
        List<ThumbsUpMemberListDto> thumbsUpList = pageResults.getContent();
        model.addAttribute("page", pageResults);
        model.addAttribute("thumbsUpList", thumbsUpList);

        return "member/memberViewThumbsUp";
    }

















    @RequestMapping("/member/passwordUpdateForm")
    public String passwordUpdateForm(){
        return  "member/passwordUpdateForm";
    }

    @ResponseBody
    @RequestMapping("/member/passwordUpdate")
    public String passwordUpdate(String curPassword,String newPassword, String newPasswordCheck, Principal principal
    ,Model model){
        try {
            memberService.passwordUpdate(curPassword,newPassword,newPasswordCheck,principal.getName());
            return "passwordUpdated";
        } catch (PasswordWrongException e) {
            return "curPasswordWrong";
        }catch (NewPasswordNotMatchedException e){
            return "passwordCheckWrong";
        } catch (Exception e) {
            throw new RuntimeException("유저정보를 찾을 수 없습니다.",e);
        }
    }


    @RequestMapping("/member/passwordCheckForm")
    public String passwordCheckForm(){
        return "member/passwordCheckForm";
    }

    @RequestMapping("/member/passwordCheck")
    public String passwordCheck(String curPassword,Principal principal,Model model) throws  Exception{
        try {
            memberService.passwordCheck(curPassword,principal.getName());
            MemberViewDto member = memberService.getMember(principal.getName());
            model.addAttribute("member",member);
            return "member/memberEdit";
        } catch (PasswordWrongException e) {
            model.addAttribute("wrongPassword","비밀번호가 잘못되었습니다.");
            return "member/passwordCheckForm";
        } catch ( Exception e){
            throw new Exception(e);
        }
    }

    @RequestMapping("/member/memberEdit")
    public  String memberEdit(){
        return "member/memberEdit";
    }


    @RequestMapping("/member/memberUpdate")
    public String memberUpdate( MemberUpdateDto memberUpdateDto, Principal principal ) throws  Exception{
        System.out.println("memberUpdateDto = " + memberUpdateDto);
        memberUpdateDto.setId(principal.getName());
        memberService.updateMember(memberUpdateDto);
        return "redirect:/member/memberView";
    }

    @RequestMapping("/member/memberDelete")
    public String memberDelete(Principal principal) throws  Exception{
        //탈퇴와 동시에 security에서도 탈퇴하면 됨
        memberService.deleteMember(principal.getName());
        return "redirect:/login/logout";

    }







}
