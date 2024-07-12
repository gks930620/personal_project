package jpabook.jpashop.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import jpabook.jpashop.entity.BaseTimeEntity;
import jpabook.jpashop.entity.Category;
import jpabook.jpashop.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class IdeaListDto   {

    @QueryProjection
    public IdeaListDto(Long id, String categoryName, String username, String title,LocalDateTime createdDate) {
        this.id = id;
        this.categoryName = categoryName;
        this.username = username;
        this.title = title;
        this.createdDate=createdDate;
    }

    @QueryProjection
    public IdeaListDto(Long id, String categoryName, String username, String title, LocalDateTime createdDate, Long replyCount) {
        this.id = id;
        this.categoryName = categoryName;
        this.username = username;
        this.title = title;
        this.createdDate = createdDate;
        this.replyCount = replyCount;
    }
    @QueryProjection
    public IdeaListDto(Long id, String categoryName, String username, String title, LocalDateTime createdDate, Long viewCount, Long replyCount, Long thumbsUpCount) {
        this.id = id;
        this.categoryName = categoryName;
        this.username = username;
        this.title = title;
        this.createdDate = createdDate;
        this.viewCount = viewCount;
        this.replyCount = replyCount;
        this.thumbsUpCount = thumbsUpCount;
    }

    private Long id;
    private String categoryName;
    private String username;
    private String title;
    private LocalDateTime createdDate;

    private Long viewCount;
    private Long replyCount;
    private Long thumbsUpCount;






}
