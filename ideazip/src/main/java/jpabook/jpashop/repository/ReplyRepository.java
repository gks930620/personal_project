package jpabook.jpashop.repository;

import jpabook.jpashop.dto.IdeaListDto;
import jpabook.jpashop.dto.IdeaListSearch;
import jpabook.jpashop.dto.ReplyViewDto;
import jpabook.jpashop.entity.Reply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository  extends JpaRepository<Reply, Long> ,ReplyRepositoryCustom{

}
