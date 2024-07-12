package jpabook.jpashop.dto;

import lombok.Data;

@Data
public class MemberFormDto {
    private String id;
    private String password;
    private String username;
    private String email;
    private String hp; //전화번호
    private String birthday; //생일
}
