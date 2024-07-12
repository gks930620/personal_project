package jpabook.jpashop.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jpabook.jpashop.dto.AttachDto;
import jpabook.jpashop.dto.QAttachDto;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static jpabook.jpashop.entity.QAttach.attach;

public class AttachRepositoryImpl implements  AttachRepositoryCustom {
    @Autowired
    private JPAQueryFactory queryFactory;

    @Override
    public AttachDto getAttachDto(Long id) {
        AttachDto attachDto = queryFactory.select(new QAttachDto(attach.id, attach.idea.id, attach.attachCategory
                        , attach.attachFileName, attach.attachOriginalName, attach.attachFileSize
                        , attach.attachFancySize, attach.attachContentType, attach.attachPath, attach.attachDownHit
                        , attach.attachDelYn))
                .from(attach)
                .where(attach.id.eq(id))
                .fetchOne();
        return attachDto;
    }
}
