package jpabook.jpashop.service;

import jpabook.jpashop.dto.ReplyInsertDto;
import jpabook.jpashop.dto.ReplyViewDto;
import jpabook.jpashop.entity.Idea;
import jpabook.jpashop.entity.Member;
import jpabook.jpashop.entity.Reply;
import jpabook.jpashop.repository.IdeaRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ReplyService {
    private final ReplyRepository replyRepository;
    private final MemberRepository memberRepository;
    private final IdeaRepository ideaRepository;

    public Page<ReplyViewDto> getReplyList(Pageable pageable, Long reParentNo) {
        Page<ReplyViewDto> result = replyRepository.replyList(pageable, reParentNo);
        return result;
    }


    public Long replyInsert(ReplyInsertDto reply) {
        Member member = memberRepository.getOne(reply.getWriter());
        Idea idea = ideaRepository.getOne(reply.getReParentNo());
        Reply replyInsert=Reply.createReply(reply.getContent(), member,idea);
        Reply save = replyRepository.save(replyInsert);
        return save.getId();
    }

    public Long replyUpdate(Long reId, String content,String userId)  throws  Exception{
        Reply reply = replyRepository.getOne(reId);
        if(! isSameUser(reply.getMember().getId(),userId)){
            throw new Exception("당신은 댓글을 작성한 사람이 아닙니다.");
        }
        reply.setContent(content);    //Trasactional을 적용했다면  밑에 없어도될텐데....  Trasactional 생각해보자.
        replyRepository.save(reply);
        return reply.getId();
    }
    public void  replyDelete(Long reId,String userId)  throws  Exception{
        Reply reply = replyRepository.getOne(reId);
        if(! isSameUser(reply.getMember().getId(),userId)){
            throw new Exception("당신은 댓글을 작성한 사람이 아닙니다.");
        }
        replyRepository.delete(reply);
    }


    private boolean isSameUser(String  memId,String userId){
        return userId.equals(memId) ? true : false;
    }

}
