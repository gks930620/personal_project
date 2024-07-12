package jpabook.jpashop.repository;

import jpabook.jpashop.entity.Idea;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IdeaRepository  extends JpaRepository<Idea,Long>, IdeaRepositoryCustom {

    //검색어가 있을수도 있고 없을수도있는 동적쿼리잖아..... querydsl활용해야지... 일단 킵
    //화면개발은 그냥 일단은 page만 갖고 해보자

//    @Query(value = "select i from Idea i ")
//    Page<Idea> findBySearch(Pageable pageable, IdeaListSearch ideaListSearch);

//    @EntityGraph(attributePaths = {"category","member"})
//    Page<Idea> findAll(Pageable pageable); //카운트쿼리는 따로 지정하자



}



