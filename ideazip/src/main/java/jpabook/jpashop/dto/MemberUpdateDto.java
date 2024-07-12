package jpabook.jpashop.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MemberUpdateDto {
    private  String id;
    private String username;
    private String email;
    private String hp; //전화번호
    private String birthday; //생일


}
