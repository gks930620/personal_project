package jpabook.jpashop.service.login;


import jpabook.jpashop.dto.LoginDto;
import jpabook.jpashop.dto.UserCustom;
import jpabook.jpashop.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 *  로그인 성공여부를 판단하는 service,  나중에 api 로그인 썻을 때 수정해야 할 듯
 */
@Service
public class LoginSuccessValidator  implements UserDetailsService {

    @Autowired
    MemberRepository memberRepository;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    //비밀번호 인코딩.  security는 이게 안 되어있으면 알아서 에러일으켜서 꼭 해줘야함.
    // 그럼 무조건 insert하는 부분에서도 encoding 되서 넣어야 함.


    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        LoginDto loginDto = memberRepository.findLoginDto(id);  //DB조회해서 얻은게 LoginDto인데.. 
        if (loginDto == null) {  //이 메솓드 설명에는 return에 never null 이라 되어있던데..
            return null;
        }
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        authorities.add(new SimpleGrantedAuthority("USER")); //일단 권한 유저,  나중에는 이거 유저권한리스트하면되는데 권한별로 할 일이 있을라나...
        UserCustom userCustom=new UserCustom(
                authorities,loginDto
        );
        return userCustom;
    }






}
