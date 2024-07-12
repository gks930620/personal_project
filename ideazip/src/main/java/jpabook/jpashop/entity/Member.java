package jpabook.jpashop.entity;


import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@DynamicInsert
@Setter
public class Member  extends  BaseTimeEntity implements Persistable<String> {
    public Member(String id, String password, String username, String email, String hp, String birthday) {
        this.id = id;
        this.password = password;
        this.username = username;
        this.email = email;
        this.hp = hp;
        this.birthday = birthday;
    }

    @Id
    @Column(name = "member_id")
    private String id;
    @Override
    public boolean isNew() {
        return createdDate==null;
    }



    private String password;

    @ColumnDefault("'USER'")
    private String role;

    private String username;



    private String birthday; //생일
    private String email;
    private String hp; //전화번호


    private String memberDelYn="N";





    @OneToMany(fetch = FetchType.LAZY,mappedBy = "member")
    private List<ThumbsUp> thumbUps= new ArrayList<>();


    @OneToMany(fetch = FetchType.LAZY,mappedBy = "member")
    private List<Reply> replies= new ArrayList<>();


}
