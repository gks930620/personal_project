package jpabook.jpashop.repository;

import jpabook.jpashop.entity.Idea;
import jpabook.jpashop.entity.Member;
import jpabook.jpashop.entity.ThumbsUp;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ThumbsUpRepository extends JpaRepository<ThumbsUp,Long> , ThumbsUpRepositoryCustom {
    ThumbsUp  findByMemberAndIdea(Member member, Idea idea);
}
