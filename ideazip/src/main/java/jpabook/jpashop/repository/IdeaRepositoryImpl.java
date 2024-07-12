package jpabook.jpashop.repository;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jpabook.jpashop.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

import static jpabook.jpashop.entity.QCategory.category;
import static jpabook.jpashop.entity.QIdea.idea;
import static jpabook.jpashop.entity.QMember.member;
import static jpabook.jpashop.entity.QAttach.attach;
import static jpabook.jpashop.entity.QReply.reply;
import static jpabook.jpashop.entity.QThumbsUp.thumbsUp;

import static org.springframework.util.StringUtils.hasText;

public class IdeaRepositoryImpl implements IdeaRepositoryCustom {
    @Autowired
    private JPAQueryFactory queryFactory;   //난 빈등록했으니까..
    private BooleanExpression searchWord(String searchType, String searchWord) { // 검색타입 검색어와  항상 같이
        if (hasText(searchWord)) {
            switch (searchType) {
                case "T":  //제목
                    return idea.title.contains(searchWord);
                case "W":   // writer 작성자
                    return idea.member.username.contains(searchWord);
                case "C":  //content
                    return idea.content.contains(searchWord);
            }
        }
        return null;
    }

    private BooleanExpression searchCategory(String searchCategory) {
        return hasText(searchCategory) ? idea.category.categoryCd.eq(searchCategory) : null;
    }


    private BooleanExpression mwdStandard(String mwdStandard) {
        switch (mwdStandard) {
            case "M":
                return idea.createdDate.between(LocalDateTime.now().minusDays(30), LocalDateTime.now());
            case "W":
                return  idea.createdDate.between(LocalDateTime.now().minusDays(7), LocalDateTime.now());
            case "D":
                return idea.createdDate.between(LocalDateTime.now().minusDays(1), LocalDateTime.now());
        }
        return null;
    }


    @Override
    public List<IdeaHomeDto> getIdeaHomeListByViewCount(String mwdStandard) {
        //아마  파라미터들은 메소드 따로 만들어서 booleanExpression 해야될거 같아..
        List<IdeaHomeDto> content = queryFactory.select(new QIdeaHomeDto(idea.id, idea.title,idea.viewCount))
                .from(idea)
                .where(mwdStandard(mwdStandard))
                .orderBy(idea.viewCount.desc())
                .limit(10)
                .fetch();
        return content;
    }

    @Override
    public Page<IdeaListDto> ideaSearchPage(Pageable pageable, IdeaListSearch ideaListSearch) {
        List<IdeaListDto> content = queryFactory.select(new QIdeaListDto
                        (idea.id, category.categoryName, member.username, idea.title, idea.createdDate
                                , idea.viewCount
                                , queryFactory.select(reply.count()).from(reply).where(reply.idea.id.eq(idea.id))
                                , queryFactory.select(thumbsUp.count()).from(thumbsUp).where(thumbsUp.idea.id.eq(idea.id))
                        ))
                .from(idea)
                .leftJoin(idea.category, category)
                .leftJoin(idea.member, member)
                .where(searchWord(ideaListSearch.getSearchType(), ideaListSearch.getSearchWord()), searchCategory(ideaListSearch.getSearchCategory()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())    //offset : 어디서부터 가져올지,  limit : 얼마나 가져올지,    pageSize : 한 페이지의 글 개수
                .orderBy(idea.id.desc())
                .fetch();


        long total = queryFactory.select(idea).from(idea)
                .where(searchWord(ideaListSearch.getSearchType(), ideaListSearch.getSearchWord()), searchCategory(ideaListSearch.getSearchCategory()))
                .fetchCount();

        return new PageImpl<>(content, pageable, total);

    }


    @Override
    public IdeaViewDto getIdeaView(Long id) {
        List<AttachDto> attaches = queryFactory.select(new QAttachDto(attach.id, attach.idea.id, attach.attachCategory
                        , attach.attachFileName, attach.attachOriginalName, attach.attachFileSize
                        , attach.attachFancySize, attach.attachContentType, attach.attachPath, attach.attachDownHit
                        , attach.attachDelYn))
                .from(attach)
                .where(attach.idea.id.eq(id))
                .fetch();


        IdeaViewDto ideaViewDto = queryFactory.select(new QIdeaViewDto(idea.id, category.categoryName, member.username, idea.title, idea.content, idea.createdDate, idea.lastModifiedDate
                                , idea.viewCount
                                , queryFactory.select(reply.count()).from(reply).where(reply.idea.id.eq(idea.id))
                                , queryFactory.select(thumbsUp.count()).from(thumbsUp).where(thumbsUp.idea.id.eq(idea.id))
                        )
                ).from(idea)
                .leftJoin(idea.category, category)
                .leftJoin(idea.member, member)
                .where(idea.id.eq(id))
                .fetchOne();

        ideaViewDto.setAttaches(attaches);
        return ideaViewDto;
    }

    @Override
    public IdeaEditDto getIdeaEdit(Long id) {
        List<AttachDto> attaches = queryFactory.select(new QAttachDto(attach.id, attach.idea.id, attach.attachCategory
                        , attach.attachFileName, attach.attachOriginalName, attach.attachFileSize
                        , attach.attachFancySize, attach.attachContentType, attach.attachPath, attach.attachDownHit
                        , attach.attachDelYn))
                .from(attach)
                .where(attach.idea.id.eq(id))
                .fetch();
        IdeaEditDto ideaEditDto = queryFactory.select(new QIdeaEditDto(idea.id, category.categoryCd, member.username, idea.title, idea.content, idea.createdDate, idea.lastModifiedDate)
                ).from(idea)
                .leftJoin(idea.category, category)
                .leftJoin(idea.member, member)
                .where(idea.id.eq(id))
                .fetchOne();
        ideaEditDto.setAttaches(attaches);
        return ideaEditDto;
    }


}
