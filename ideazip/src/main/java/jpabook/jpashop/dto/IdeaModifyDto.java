package jpabook.jpashop.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class IdeaModifyDto {

    private Long id;
    private String memberId;   // 이거는 security pricipal에서 얻음
    private String categoryCd;
    private String title;
    private String content;
    private Long[] delAttachNos;           //첨부파일 삭제를 위한 번호

}
