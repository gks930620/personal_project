package jpabook.jpashop.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jpabook.jpashop.dto.IdeaListDto;
import jpabook.jpashop.dto.QIdeaListDto;
import jpabook.jpashop.dto.QReplyViewDto;
import jpabook.jpashop.dto.ReplyViewDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static jpabook.jpashop.entity.QCategory.category;
import static jpabook.jpashop.entity.QIdea.idea;
import static jpabook.jpashop.entity.QMember.member;
import static jpabook.jpashop.entity.QReply.reply;

public class ReplyRepositoryImpl  implements  ReplyRepositoryCustom{

    @Autowired
    private JPAQueryFactory queryFactory;   //난 빈등록했으니까..

    @Override
    public Page<ReplyViewDto> replyList(Pageable pageable, Long reParentNo) {
        QueryResults<ReplyViewDto> result = queryFactory.select(new QReplyViewDto(reply.id, member.id, member.username, reply.content,reply.createdDate))
                .from(reply)
                .leftJoin(reply.member, member)
                .leftJoin(reply.idea, idea)
                .where(idea.id.eq(reParentNo))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(reply.id.desc())
                .fetchResults();

        List<ReplyViewDto> content=result.getResults();
        long total=result.getTotal();
        return new PageImpl<>(content,pageable,total);
    }
}
