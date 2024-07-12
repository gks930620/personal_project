package jpabook.jpashop.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jpabook.jpashop.dto.CategoryDto;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static jpabook.jpashop.entity.QCategory.category;

//이건 단순히 동적쿼리 및 페이징하는것도아닌데 querydsl썼어야됐나.. 스프링데이터jpa를 통해서  간단히 stream map 써서 dto 조회 하는것도 해보자..
public class CategoryRepositoryImpl  implements  CategoryRepositoryCustom{
    @Autowired
    JPAQueryFactory queryFactory;

    @Override
    public List<CategoryDto> findCategoryDto() {
        return queryFactory.select(Projections.constructor(CategoryDto.class, category.categoryCd,category.categoryName)
                ).from(category)
                .fetch();
    }
}
