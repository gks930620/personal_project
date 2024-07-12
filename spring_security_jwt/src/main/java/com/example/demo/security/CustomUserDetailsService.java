package com.example.demo.security;

import com.example.demo.entity.Member;
import com.example.demo.repo.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
 
    private final MemberRepository memberRepository;
    private  final  PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberRepository.findByMemberId(username)
                .map(this::createUserDetails) //값이 있으면 제공된 매핑 함수를 적용하고 결과가 null이 아니면 결과를 Optional자료형으로 리턴. 메소드 참조는 클래스이름(or객체) :: 메소드이름
                .orElseThrow(() -> new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다."));
    }


    // 해당하는 User 의 데이터가 존재한다면 UserDetails 객체로 만들어서 리턴
    private UserDetails createUserDetails(Member member) {
        System.out.println(member.getPassword());
        return User.builder()
                .username(member.getUsername())
               // .password(passwordEncoder.encode(member.getPassword()))
                .password(member.getPassword())
                .roles(member.getRoles().toArray(new String[0]))   //단순히 List를 배열형태로
                .build();
    }
}