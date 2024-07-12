package jpabook.jpashop.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Idea extends  BaseTimeEntity{

    public Idea(String title,String content){
        this.title=title;
        this.content=content;
    }



    @Id@GeneratedValue
    @Column(name = "idea_id" )  //pk에는 updatable false 해야되는거 아닌가?
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")   //연관관계주인은 요기.    Category의 maapedBy는  Idea테이블의 컬럼이름이 아닌 엔티티의 필드이름. 즉 category
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String title;

    @Lob
    @Column
    private String content; //String 말고 좀더 긴거


    private String ideaDelYn="N";

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "idea")
    private List<Attach> attaches= new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "idea")
    private List<ThumbsUp> thumbUps= new ArrayList<>();


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "idea")
    private List<Reply> replies=new ArrayList<>();

    private Long viewCount=0L;


    public static Idea createIdea(Member member,Category category, String title, String content ){
        Idea idea=new Idea(title,content);
        idea.setMember(member);
        idea.setCategory(category);
        return idea;
    }



}
