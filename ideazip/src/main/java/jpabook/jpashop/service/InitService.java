package jpabook.jpashop.service;

import jpabook.jpashop.dto.AttachDto;
import jpabook.jpashop.entity.*;
import jpabook.jpashop.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InitService {
    private final PasswordEncoder passwordEncoder;


    private final IdeaRepository ideaRepository;
    private final MemberRepository memberRepository;
    private final ThumbsUpRepository thumbsUpRepository;
    private final CategoryRepository categoryRepository;
    private final AttachRepository attachRepository;
    private final ReplyRepository replyRepository;

    @Transactional
    public void init() {
        Category category = new Category("BI01", "공공기관"); //    일단은 기발한아이디어(BI)로    공공기관, 기발제품
        Category category2 = new Category("BI02", "기발제품");
        categoryRepository.save(category);
        categoryRepository.save(category2);


        Member member1= new Member("id1",passwordEncoder.encode("password"),"한창희","abcd9393@naver.com","010-0000-0000","1983-06-20");
        Member member2= new Member("id2",passwordEncoder.encode("password"),"한창희","abcd9393@naver.com","010-0000-0000","1983-06-20");
        Member member3= new Member("id3",passwordEncoder.encode("password"),"한창희","abcd9393@naver.com","010-0000-0000","1983-06-20");
        Member member4= new Member("id4",passwordEncoder.encode("password"),"한창희","abcd9393@naver.com","010-0000-0000","1983-06-20");
        Member member5= new Member("id5",passwordEncoder.encode("password"),"한창희","abcd9393@naver.com","010-0000-0000","1983-06-20");

        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);
        memberRepository.save(member4);
        memberRepository.save(member5);


        for(int i=1; i<=5 ; i++){
            Idea idea=new Idea("공공기관 제안 아이디어1(더미)","개인이 실행하기는 어렵지만 있으면 좋을 거 같아 공공기관에 제안하는 기막힌 아이디어");
            idea.setCategory(category);
            idea.setMember(member1);
            ideaRepository.save(idea);
        }

        for(int i=1 ; i<=5 ; i++){
            Idea idea=new Idea("기발한 제품 제안 아이디어1(더미)","개인이 실행하기는 어렵지만 있으면 좋을 거 같아 만들어주세요 하고 제안하는 기막힌 아이디어");
            idea.setCategory(category2);
            idea.setMember(member2);
            ideaRepository.save(idea);


            ThumbsUp thumbsUp=new ThumbsUp();
            thumbsUp.setIdea(idea);
            thumbsUp.setMember(member1);
            thumbsUpRepository.save(thumbsUp);
        }


        Idea idea1=new Idea("공공기관 제안 아이디어 ","이건 그냥 조인이 잘 되어있는지 더미데이터 확인하느거에요");
        idea1.setCategory(category);
        idea1.setMember(member1);
        ideaRepository.save(idea1);
        for(int i=1; i<=3 ; i++){
            Reply reply=Reply.createReply("우와 이아이디어 짱!!", member1,idea1);  //createReply하는 순간 set 됩니다.
            replyRepository.save(reply);



            AttachDto attachDto=new AttachDto();
            attachDto.setAttachParentNo(idea1.getId());
            attachDto.setAttachCategory("IDEA");
            attachDto.setAttachFileName("0e2724f1-6766-4f9f-a036-c282c7d3bc04");
            attachDto.setAttachOriginalName("모르겠다.PNG");
            attachDto.setAttachFileSize(1024L);
            attachDto.setAttachFancySize("1KB");
            attachDto.setAttachContentType("파일");
            attachDto.setAttachPath("idea");
            Attach attach=Attach.createAttach(idea1, attachDto);
            attachRepository.save(attach);
        }



    }


}
