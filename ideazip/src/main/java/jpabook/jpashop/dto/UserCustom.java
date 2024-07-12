package jpabook.jpashop.dto;

import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class UserCustom  extends User {
    private String name;
    private String role;

    public UserCustom(Collection<? extends GrantedAuthority> authorities
                      ,LoginDto loginDto
   ) {
        super(loginDto.getId(), loginDto.getPassword(), authorities);  //User에선 id를 username이라고 한다.
        this.name=loginDto.getName();
        this.role=loginDto.getRole();
    }


}
