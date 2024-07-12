package jpabook.jpashop.repository;

import jpabook.jpashop.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoryRepository  extends JpaRepository<Category, Long> ,CategoryRepositoryCustom{

    public Category getCategoryByCategoryCd(String categoryCd);

}
