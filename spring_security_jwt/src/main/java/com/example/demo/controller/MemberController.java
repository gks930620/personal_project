package com.example.demo.controller;

import com.example.demo.dto.MemberLoginRequestDto;
import com.example.demo.entity.Member;
import com.example.demo.repo.MemberRepository;
import com.example.demo.security.TokenInfo;
import com.example.demo.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;
    private  final MemberRepository memberRepository;
    private  final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init(){
        ArrayList<String> strings = new ArrayList<>();
        strings.add("USER");
        Member member=Member.builder().memberId("member_A").password(passwordEncoder.encode("1234")).roles(strings).build();
        memberRepository.save(member);

    }
 
    @PostMapping("/login")
    public TokenInfo login(@RequestBody MemberLoginRequestDto memberLoginRequestDto) {
        String memberId = memberLoginRequestDto.getMemberId();
        String password = memberLoginRequestDto.getPassword();
        TokenInfo tokenInfo = memberService.login(memberId, password);
        return tokenInfo;
    }

    @PostMapping("/test")
    public String test() {
        return "success";
    }

}