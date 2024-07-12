package jpabook.jpashop.dto;


import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CategoryDto {

    public CategoryDto(String categoryCd, String categoryName) {
        this.categoryCd = categoryCd;
        this.categoryName = categoryName;
    }

    private Long id;
    private String categoryCd;  //카테고리 코드 JB00
    private String categoryName; //   카테고리 이름 BC00의 실제이름은 직업분류 라던가. JB01은  "교사" 등


}
