package jpabook.jpashop.controller;

import jpabook.jpashop.dto.IdeaHomeDto;
import jpabook.jpashop.entity.Idea;
import jpabook.jpashop.entity.Member;
import jpabook.jpashop.repository.IdeaRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.ThumbsUpRepository;
import jpabook.jpashop.service.IdeaService;
import jpabook.jpashop.service.InitService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class HomeController {
    private  final IdeaService ideaService;

    private  final InitService initService;


    //jpaRepository.save()는 원리 기억하자.    save 할 때 새로운객체인지아닌지는 식별자가  null (or 0, primitive 타입일 때) 로 판단.
    //@Entity클래스에    id에 @GenerateValue를 하면   persist후   식별자에 값 생성.
    //만약 @Generatevalue를 안 쓰고 식별자에 값을 save()전에 넣고  save를 실행하면   save()메소드내에서 상당한 시간낭비를 하게 된다.
    // 결론은  @generatevalue를 하면 걍 save 하면 되긴함.    jpa의 새로운객체 판별방법은 기억하고있자.
    @PostConstruct  //객체만들고(빈등록) 이거 실행해주세요
    public void initDummyData(){
          initService.init(); //시작 더미데이터
    }





    @RequestMapping("/")
    public String home(){
        return "home";
    }

    
    //일단 조회수만,  좋아욧는 나중에
    @RequestMapping("/home/ideaList")
    public String homeIdeaList(String mwdStandard, Model model){
        List<IdeaHomeDto> ideaHomeList = ideaService.getIdeaHomeListByViewCount(mwdStandard);
        model.addAttribute("ideaHomeList",ideaHomeList);
        return mwdStandard.equals("M") ? "homeMonth"  : mwdStandard.equals("W") ? "homeWeek" : "homeDay" ;
    }

}
