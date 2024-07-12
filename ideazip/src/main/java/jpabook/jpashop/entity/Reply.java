package jpabook.jpashop.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Setter
public class Reply   extends  BaseTimeEntity{

    public Reply(String content) {
        this.content = content;
    }

    @Id@GeneratedValue
    private Long id;
    private String content; /* 댓글 내용 */

    @ManyToOne(fetch = FetchType.LAZY)
    private Idea idea;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;


    public static Reply createReply(String content, Member member, Idea idea){
        Reply reply=new Reply();
        reply.setContent(content);
        reply.setMember(member);
        reply.setIdea(idea);
        return reply;
    }



}
