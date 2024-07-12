package jpabook.jpashop.dto;


import lombok.Data;

@Data
public class ReplyInsertDto {
    private Long  reParentNo;   //부모글
    private String content;
    private String writer;  //현재 로그인한사람 id 세팅

}
