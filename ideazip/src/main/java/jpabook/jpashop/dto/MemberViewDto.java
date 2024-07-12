package jpabook.jpashop.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MemberViewDto {
    @QueryProjection
    public MemberViewDto(String id ,String username, String email, String hp, String birthday, LocalDateTime createdDate) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.hp = hp;
        this.birthday = birthday;
        this.createdDate = createdDate;
    }

    private String id;
    private String username;
    private String email;
    private String hp; //전화번호
    private String birthday; //생일
    private LocalDateTime createdDate ;
}
