package jpabook.jpashop.dto;

import com.querydsl.core.annotations.QueryProjection;
import jpabook.jpashop.entity.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class IdeaViewDto {


    @QueryProjection
    public IdeaViewDto(Long id, String categoryName, String username, String title, String content, LocalDateTime createdDate, LocalDateTime lastModifiedDate
    ) {
        this.id = id;
        this.categoryName = categoryName;
        this.username = username;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
    }
    @QueryProjection
    public IdeaViewDto(Long id, String categoryName, String username, String title, String content, LocalDateTime createdDate, LocalDateTime lastModifiedDate, Long viewCount, Long replyCount, Long thumbsUpCount) {
        this.id = id;
        this.categoryName = categoryName;
        this.username = username;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
        this.viewCount = viewCount;
        this.replyCount = replyCount;
        this.thumbsUpCount = thumbsUpCount;
    }

    private Long id;
    private String categoryName;
    private String username;
    private String title;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private List<AttachDto> attaches= new ArrayList<>();

    private Long viewCount;
    private Long replyCount;
    private Long thumbsUpCount;









}
