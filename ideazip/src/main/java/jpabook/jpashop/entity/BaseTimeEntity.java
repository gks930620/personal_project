package jpabook.jpashop.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.stereotype.Service;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass // 이 클래스에는 Entity가 없다. 다른 클래스에서 이 클래스를 상속할 때 Entity로, 즉 칼럼으로 인식하겠다는  뜻이다.
@EntityListeners(AuditingEntityListener.class) // JPA audting 기능.  자동으로 JPA가 Trasaction 시점에 필드의 값(시간)들을 채워넣음
@Getter

public class BaseTimeEntity {

    @CreatedDate
    @Column(updatable = false)
    protected LocalDateTime createdDate;

    @LastModifiedDate
    protected LocalDateTime lastModifiedDate;



    //creatdby,modifiedBy는 글쎼... 작성자가 명확한 경우에는 굳이?
}
