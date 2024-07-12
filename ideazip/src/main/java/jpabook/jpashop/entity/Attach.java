package jpabook.jpashop.entity;

import jpabook.jpashop.dto.AttachDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@DynamicInsert
public class Attach {    //부모글이 분류가 없다.  첨부파일별로 Entity만들기


    public Attach(Idea idea, String attachCategory, String attachFileName, String attachOriginalName, long attachFileSize, String attachFancySize, String attachContentType, String attachPath) {
        this.idea = idea;
        this.attachCategory = attachCategory;
        this.attachFileName = attachFileName;
        this.attachOriginalName = attachOriginalName;
        this.attachFileSize = attachFileSize;
        this.attachFancySize = attachFancySize;
        this.attachContentType = attachContentType;
        this.attachPath = attachPath;
    }

    @Id@GeneratedValue
    @Column(name = "attach_id")
    private Long id; /* 첨부파일 번호(PK) */
    @ManyToOne(fetch = FetchType.LAZY)
    private Idea idea;
    private String attachCategory; /* 상위글 분류(IDEA,Memmber등) */
    private String attachFileName; /* 실제 저장된 파일명 */
    private String attachOriginalName; /* 사용자가 올린 원래 파일명 */
    private long attachFileSize; /* 파일 사이즈   1024,  1024*1024 */
    private String attachFancySize; /* 팬시 사이즈  : 1KB , 1M */
    private String attachContentType; /* 컨텐츠 타입 */
    private String attachPath; /* 저장 경로(board/2020) */

    @ColumnDefault("0")
    private int attachDownHit; /* 다운로드 횟수 */
    @ColumnDefault("'N'")   //이거 주의해야함.....  문자열 기본값 저장할 때는 항상 ''신경쓰기
    private String attachDelYn; /* 삭제여부(스케쥴에 의해서 파일삭제처리) */


    public static  Attach createAttach(Idea idea,AttachDto attachDto){
        Attach attach=new Attach(idea, attachDto.getAttachCategory(),attachDto.getAttachFileName(),attachDto.getAttachOriginalName(),attachDto.getAttachFileSize()
        ,attachDto.getAttachFancySize(),attachDto.getAttachContentType(),attachDto.getAttachPath());
        attach.setIdea(idea);
        return attach;
    }



}
