package jpabook.jpashop.repository;

import jpabook.jpashop.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IdeaRepositoryCustom  {

    public List<IdeaHomeDto> getIdeaHomeListByViewCount(String mwdStandard);

    public Page<IdeaListDto> ideaSearchPage(Pageable pageable ,IdeaListSearch ideaListSearch); //실제로 화면에 보여줄 DTO
    public IdeaViewDto getIdeaView(Long id);
    public IdeaEditDto getIdeaEdit(Long id);

}
