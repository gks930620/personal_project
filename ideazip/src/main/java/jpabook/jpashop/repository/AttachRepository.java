package jpabook.jpashop.repository;

import jpabook.jpashop.entity.Attach;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachRepository extends JpaRepository<Attach,Long> , AttachRepositoryCustom{

}
