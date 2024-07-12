package jpabook.jpashop;


import com.querydsl.jpa.impl.JPAQueryFactory;
import jpabook.jpashop.dto.AttachDto;
import jpabook.jpashop.dto.QAttachDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static jpabook.jpashop.entity.QAttach.attach;
import static jpabook.jpashop.entity.QIdea.idea;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Rollback(false)
public class IdeaRepositoryTest {


    //왜 테스트환경 실행이 안되지?


    @Autowired
    private JPAQueryFactory queryFactory;
    
    @Test
    public  void manyToOneTest() throws Exception  {
        List<AttachDto> attaches = queryFactory.select(new QAttachDto(attach.id, attach.idea.id, attach.attachCategory
                        , attach.attachFileName, attach.attachOriginalName, attach.attachFileSize
                        , attach.attachFancySize, attach.attachContentType, attach.attachPath, attach.attachDownHit
                        , attach.attachDelYn))
                .fetch();



     }



}
