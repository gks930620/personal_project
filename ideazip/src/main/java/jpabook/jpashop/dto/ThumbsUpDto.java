package jpabook.jpashop.dto;

import jpabook.jpashop.entity.Idea;
import jpabook.jpashop.entity.Member;
import lombok.Data;

import javax.persistence.*;


@Data
public class ThumbsUpDto {
    private Long id;
    private Member member;
    private Idea idea;
}
