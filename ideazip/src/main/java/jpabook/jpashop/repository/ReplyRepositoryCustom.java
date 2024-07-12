package jpabook.jpashop.repository;

import jpabook.jpashop.dto.ReplyViewDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReplyRepositoryCustom {
    public Page<ReplyViewDto> replyList(Pageable pageable , Long reParentNo); //실제로 화면에 보여줄 DTO
}
