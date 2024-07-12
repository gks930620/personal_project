package jpabook.jpashop.repository;

import jpabook.jpashop.dto.MemberViewDto;
import jpabook.jpashop.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MemberRepository  extends JpaRepository<Member,String>, MemberRepositoryCustom {




}
