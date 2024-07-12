package jpabook.jpashop;

import jpabook.jpashop.dto.CategoryDto;
import jpabook.jpashop.dto.IdeaListDto;
import jpabook.jpashop.dto.IdeaListSearch;
import jpabook.jpashop.service.CategoryService;
import jpabook.jpashop.service.IdeaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Rollback(false)
public class IdeaControllerTest {

    @Autowired
    IdeaService ideaService;
    @Autowired
    CategoryService categoryService;

    @Test
    public void PageTest() throws Exception  {
        PageRequest of = PageRequest.of(21, 10);
        Page<IdeaListDto> pageResults = ideaService.getIdeaList(of, new IdeaListSearch());
        List<IdeaListDto> ideaList = pageResults.getContent();

        pageResults.getPageable().getPageNumber(); //현재페이지
        pageResults.getTotalElements(); //전체글수
        pageResults.getSize(); //한페이지당 글 개수 같은데
        pageResults.getNumber();
        pageResults.getNumberOfElements() ; // getSize랑 비슷하지만    마지막글이 6개일 땐 6return


        //필요한거 : 전체건수, 전체페이지,      한 페이지당 글 개수  ,현재페이지
        System.out.println("getTotalElements = " + pageResults.getTotalElements());
        System.out.println("getTotalPages = " + pageResults.getTotalPages());
        System.out.println("getSize = " + pageResults.getSize());
        System.out.println("getNumber = " + pageResults.getNumber());
        System.out.println("getNumberOfElements = " + pageResults.getNumberOfElements());
        System.out.println("getPageable getPageNumber = " + pageResults.getPageable().getPageNumber());
        System.out.println("getPageable getPageSize = " + pageResults.getPageable().getPageSize());

     }

     @Test
     public void categoryList() throws Exception  {
         List<CategoryDto> categoryDtos = categoryService.categoryDtoList();
         for(CategoryDto categoryDto : categoryDtos) {
             System.out.println("categoryDto = " + categoryDto);
         }
     }



}
