package jpabook.jpashop.dto;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class IdeaHomeDto {

    @QueryProjection
    public IdeaHomeDto(Long id, String title,Long viewCount) {
        this.id = id;
        this.title = title;
        this.viewCount=viewCount;
    }

    private  Long id;
    private  String title;
    private Long viewCount;

}
