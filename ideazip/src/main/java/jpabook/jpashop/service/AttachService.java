package jpabook.jpashop.service;

import jpabook.jpashop.dto.AttachDto;
import jpabook.jpashop.entity.Attach;
import jpabook.jpashop.entity.Category;
import jpabook.jpashop.entity.Idea;
import jpabook.jpashop.entity.Member;
import jpabook.jpashop.repository.AttachRepository;
import jpabook.jpashop.repository.CategoryRepository;
import jpabook.jpashop.repository.IdeaRepository;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttachService {

    @Autowired
    private final IdeaRepository ideaRepository;
    private final AttachRepository attachRepository;

    public void insertAttaches(List<AttachDto> attachDtos,Long parentId){
        //Attach를 만들고,,,
        Idea idea=ideaRepository.getOne(parentId);
        List<Attach> attaches=new ArrayList<>();
        for(AttachDto attachDto : attachDtos){
            Attach attach=Attach.createAttach(idea,attachDto);
            attaches.add(attach);
        }
        attachRepository.saveAll(attaches);
    }


    public AttachDto getAttachDto(Long id){
        AttachDto attachDto = attachRepository.getAttachDto(id);
        return attachDto;
    }




}
