package jpabook.jpashop.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
 public class ReplyViewDto {
 @QueryProjection
 public ReplyViewDto(Long id, String userId, String writer, String content) {
  this.id = id;
  this.userId = userId;
  this.writer = writer;
  this.content = content;
 }

 @QueryProjection
 public ReplyViewDto(Long id, String userId, String writer, String content, LocalDateTime createdDate) {
  this.id = id;
  this.userId = userId;
  this.writer = writer;
  this.content = content;
  this.createdDate = createdDate;
 }

  private Long id; //댓글번호
  private String userId;   //작성자 id  ( 본인일 때만 수정 삭제 보이게)
  private String writer;  //작성자
  private String content; //댓글 내용
  private LocalDateTime createdDate;
  private Boolean  isUserIdAndLoginIdSame;


}
