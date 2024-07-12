package jpabook.jpashop.service;

import jpabook.jpashop.dto.CategoryDto;
import jpabook.jpashop.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private  final CategoryRepository categoryRepository;

    public List<CategoryDto> categoryDtoList(){
        return categoryRepository.findCategoryDto();
    }

}
