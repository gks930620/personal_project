package jpabook.jpashop.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class IdeaFormDto {

    private String memberId;   // 이거는 security pricipal에서 얻음
    private String categoryCd;
    private String title;
    private String content;


}
