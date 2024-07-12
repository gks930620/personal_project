package jpabook.jpashop.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class ThumbsUpMemberListDto {
    @QueryProjection
    public ThumbsUpMemberListDto(Long parentIdeaid, String parentIdeaTitle) {
        this.parentIdeaid = parentIdeaid;
        this.parentIdeaTitle = parentIdeaTitle;
    }

    private Long parentIdeaid;
    private String parentIdeaTitle;



}
