package jpabook.jpashop.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReplyMemberListDto {
    @QueryProjection
    public ReplyMemberListDto(Long id, Long parentIdeaid, String content, LocalDateTime createdDate) {
        this.id = id;
        this.parentIdeaid = parentIdeaid;
        this.content = content;
        this.createdDate = createdDate;
    }

    @QueryProjection
    public ReplyMemberListDto(Long id, Long parentIdeaid, String parentIdeaTitle, String content, LocalDateTime createdDate) {
        this.id = id;
        this.parentIdeaid = parentIdeaid;
        this.parentIdeaTitle = parentIdeaTitle;
        this.content = content;
        this.createdDate = createdDate;
    }

    private Long id; //댓글번호
    private Long parentIdeaid;
    private String parentIdeaTitle;
    private String content; //댓글 내용

    private LocalDateTime createdDate;


}
