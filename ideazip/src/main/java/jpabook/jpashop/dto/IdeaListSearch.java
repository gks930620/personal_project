package jpabook.jpashop.dto;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
public class IdeaListSearch {

    private String searchType;   //작성자(member)인지, 제목인지
    private String searchWord;
    private String searchCategory;  //나중에 select 태그에서 선택할수있게


//    private LocalDateTime createdDate;    //나중에 혹시 검색기간조회기능도만들거면 ..


}
