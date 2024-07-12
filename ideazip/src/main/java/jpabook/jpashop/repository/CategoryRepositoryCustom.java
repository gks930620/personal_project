package jpabook.jpashop.repository;

import jpabook.jpashop.dto.CategoryDto;

import java.util.List;

public interface CategoryRepositoryCustom {

    public List<CategoryDto> findCategoryDto();
}
