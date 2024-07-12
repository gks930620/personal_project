package jpabook.jpashop.repository;

import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jpabook.jpashop.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static jpabook.jpashop.entity.QCategory.category;
import static jpabook.jpashop.entity.QIdea.idea;
import static jpabook.jpashop.entity.QMember.member;
import static jpabook.jpashop.entity.QReply.reply;
import static jpabook.jpashop.entity.QThumbsUp.thumbsUp;


public class MemberRepositoryImpl implements MemberRepositoryCustom {

    @Autowired
    JPAQueryFactory queryFactory;

    @Override
    public LoginDto findLoginDto(String id) {
        return queryFactory.select(new QLoginDto(member.id, member.password
                        , member.username, member.role))
                .from(member)
                .where(member.id.eq(id), member.memberDelYn.eq("N"))
                .fetchOne();

    }

    @Override
    public MemberViewDto findMemberViewDto(String id) {
        MemberViewDto memberViewDto = queryFactory.select(new QMemberViewDto(member.id, member.username, member.email, member.hp
                        , member.birthday
                        , member.createdDate))
                .from(member)
                .where(member.id.eq(id))
                .fetchOne();
        return memberViewDto;
    }

    @Override
    public Page<IdeaListDto> memberIdeaList(Pageable pageable, String memberId) {
        List<IdeaListDto> content = queryFactory.select(new QIdeaListDto
                        (idea.id, category.categoryName, member.username, idea.title, idea.createdDate
                                , idea.viewCount
                                , queryFactory.select(reply.count()).from(reply).where(reply.idea.id.eq(idea.id))
                                , queryFactory.select(thumbsUp.count()).from(thumbsUp).where(thumbsUp.idea.id.eq(idea.id))
                        ))
                .from(idea)
                .leftJoin(idea.category, category)
                .leftJoin(idea.member, member)
                .where(idea.member.id.eq(memberId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())    //offset : 어디서부터 가져올지,  limit : 얼마나 가져올지,    pageSize : 한 페이지의 글 개수
                .orderBy(idea.id.desc())
                .fetch();


        long total = queryFactory.select(idea).from(idea)
                .where(idea.member.id.eq(memberId))
                .fetchCount();

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Page<ReplyMemberListDto> memberReplyList(Pageable pageable, String memberId) {
        List<ReplyMemberListDto> content = queryFactory.select(new QReplyMemberListDto(
                        reply.id, reply.idea.id,reply.idea.title, reply.content, reply.createdDate
                ))
                .from(reply)
                .leftJoin(reply.idea, idea)
                .where(reply.member.id.eq(memberId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(reply.idea.id.desc())
                .fetch();
        long total = queryFactory.select(reply).from(reply)
                .where(reply.member.id.eq(memberId))
                .fetchCount();
        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Page<ThumbsUpMemberListDto> memberThumbsUpList(Pageable pageable, String memberId) {
        List<ThumbsUpMemberListDto> content = queryFactory.select(new QThumbsUpMemberListDto(
                        thumbsUp.idea.id,thumbsUp.idea.title
                ))
                .from(thumbsUp)
                .leftJoin(thumbsUp.idea, idea)
                .where(thumbsUp.member.id.eq(memberId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(thumbsUp.idea.id.desc())
                .fetch();
        long total = queryFactory.select(thumbsUp).from(thumbsUp)
                .where(thumbsUp.member.id.eq(memberId))
                .fetchCount();

        return new PageImpl<>(content, pageable, total);
    }


}
