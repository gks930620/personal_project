package jpabook.jpashop.service;

import jpabook.jpashop.dto.*;
import jpabook.jpashop.entity.Attach;
import jpabook.jpashop.entity.Category;
import jpabook.jpashop.entity.Idea;
import jpabook.jpashop.entity.Member;
import jpabook.jpashop.repository.AttachRepository;
import jpabook.jpashop.repository.CategoryRepository;
import jpabook.jpashop.repository.IdeaRepository;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IdeaService {

    //왜케 서비스단에서 하는게 없지???   exception 처리라도 해야지..

    private final IdeaRepository ideaRepository;
    private  final CategoryRepository categoryRepository;
    private final MemberRepository memberRepository;
    private  final AttachRepository attachRepository;

    public List<IdeaHomeDto> getIdeaHomeListByViewCount(String mwdStandard){
        List<IdeaHomeDto> ideaHomeList = ideaRepository.getIdeaHomeListByViewCount(mwdStandard);
        return ideaHomeList;
    }


    public Page<IdeaListDto> getIdeaList( Pageable pageable,IdeaListSearch ideaListSearchDto) {
        Page<IdeaListDto> pageResults = ideaRepository.ideaSearchPage(pageable, ideaListSearchDto);
        return pageResults;
    }



    public IdeaViewDto getIdeaView(Long id){
        return ideaRepository.getIdeaView(id);
    }
    public IdeaEditDto getIdeaEdit(Long id){
        return ideaRepository.getIdeaEdit(id);
    }

    //save 한번에 select 2번은 괜찮나??    이부분을 많이 고민했는데  실제 category나 member의 메소드 실행안하고 단순히 프록시객체만 가지고 진행하면 쿼리 안날라감.
    public Long insertIdea(IdeaFormDto ideaFormDto){
        Member member= memberRepository.getOne(ideaFormDto.getMemberId());
        Category category= categoryRepository.getCategoryByCategoryCd(ideaFormDto.getCategoryCd());
        Idea idea = Idea.createIdea(member, category, ideaFormDto.getTitle(), ideaFormDto.getContent());
        return ideaRepository.save(idea).getId();   //Entity를 그대로 controller주면 안되고, Id만 주도록.
    }


    public void updateIdea(IdeaModifyDto ideaModifyDto) throws  Exception{
        Idea idea = ideaRepository.findById(ideaModifyDto.getId()).get();
        //글쓴사람이 같은지 확인. member의 id만 확인. 화면에서는 같은 아이디(즉 글쓴사람)이 아니면 수정 버튼 안 보이게 했음
        if(isDiffrentMember(ideaModifyDto.getMemberId(), idea.getMember().getId())){  //idea가 페치조인된게 아니라서 여기서 member select 한번 더 나가겠지만..
            throw new Exception("글쓴 사람이 아닙니다.");
        }


        Long[] deleteNos=ideaModifyDto.getDelAttachNos();
        for(Long delId : deleteNos){
            attachRepository.deleteById(delId);
        }

        Category category= categoryRepository.getCategoryByCategoryCd(ideaModifyDto.getCategoryCd());
        idea.setCategory(category);
        idea.setTitle( ideaModifyDto.getTitle());
        idea.setContent( ideaModifyDto.getContent());
    }

    public void increaseViewCount(Long id){
        Idea idea = ideaRepository.findById(id).get();
        idea.setViewCount(idea.getViewCount()+1);
        ideaRepository.save(idea);
    }



    private boolean isDiffrentMember(String loginId, String dbId){
        return !loginId.equals(dbId);
    }


}
