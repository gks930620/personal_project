package jpabook.jpashop.controller;

import jpabook.jpashop.dto.IdeaListDto;
import jpabook.jpashop.dto.ReplyInsertDto;
import jpabook.jpashop.dto.ReplyViewDto;
import jpabook.jpashop.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ReplyController {
    private final ReplyService replyService;
    @ResponseBody
    @PostMapping("/reply/replyList")
    public List<ReplyViewDto> replyList(
            @PageableDefault(size = 10) Pageable pageable
    , Long reParentNo , Principal principal){
        Page<ReplyViewDto> pageResults = replyService.getReplyList(pageable, reParentNo);
        List<ReplyViewDto> replyList=pageResults.getContent();
        if(principal !=null){
            String loginId=principal.getName();
            for(ReplyViewDto reply : replyList){
            if(reply.getUserId().equals(loginId)) reply.setIsUserIdAndLoginIdSame(true);
            }
        }
        return replyList;
    }


    @ResponseBody
    @PostMapping("/reply/replyRegist")
    public Long replyRegist(ReplyInsertDto reply, Principal principal){
        reply.setWriter(principal.getName());
        Long replyId = replyService.replyInsert(reply);
        return replyId;
    }


    //service에서  id 동일한지 확인하기. update, 삭제 등등
    @ResponseBody
    @PostMapping("/reply/replyUpdate")
    public Long replyUpdate(Long reId, String content , Principal principal) throws Exception {
        return replyService.replyUpdate(reId,content, principal.getName());

    }

    @ResponseBody
    @PostMapping("/reply/replyDelete")
    public Long replyDelete(Long reId ,Principal principal) throws  Exception{
        replyService.replyDelete(reId,principal.getName());
        return reId;
    }






}
