package jpabook.jpashop.repository;

import jpabook.jpashop.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberRepositoryCustom {

    LoginDto findLoginDto(String id);
    MemberViewDto findMemberViewDto(String id);

    Page<IdeaListDto> memberIdeaList(Pageable pageable,String memberId);

    Page<ReplyMemberListDto> memberReplyList(Pageable pageable, String memberId);

    Page<ThumbsUpMemberListDto> memberThumbsUpList(Pageable pageable, String memberId);
}
