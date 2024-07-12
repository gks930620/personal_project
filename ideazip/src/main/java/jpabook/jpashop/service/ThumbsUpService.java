package jpabook.jpashop.service;

import jpabook.jpashop.entity.Idea;
import jpabook.jpashop.entity.Member;
import jpabook.jpashop.entity.ThumbsUp;
import jpabook.jpashop.repository.IdeaRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.ThumbsUpRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class ThumbsUpService {

    private  final ThumbsUpRepository thumbsUpRepository;
    private  final MemberRepository memberRepository;
    private  final IdeaRepository ideaRepository;

    private  ThumbsUp findThumbsUp(String userId, Long ideaId){
        Member member = memberRepository.getOne(userId);
        Idea idea = ideaRepository.getOne(ideaId);
       return  thumbsUpRepository.findByMemberAndIdea(member, idea);  //없을 때는 null
    }

    public boolean isThumbsUpChecked(String userId, Long ideaId){
        ThumbsUp thumbsUp = findThumbsUp(userId,ideaId);
        return thumbsUp ==null ? false : true;
    }

    public void insertThumbUp(String userId, Long ideaId){
        Member member = memberRepository.getOne(userId);
        Idea idea = ideaRepository.getOne(ideaId);
        ThumbsUp thumbsUp=new ThumbsUp();
        thumbsUp.setMember(member);
        thumbsUp.setIdea(idea);
        thumbsUpRepository.save(thumbsUp);
    }


    public void deleteThumbUp(String userId, Long ideaId){
        ThumbsUp thumbsUp = findThumbsUp(userId,ideaId);
      thumbsUpRepository.delete(thumbsUp);
    }


}
