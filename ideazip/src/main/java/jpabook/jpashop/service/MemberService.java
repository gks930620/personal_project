package jpabook.jpashop.service;

import jpabook.jpashop.dto.*;
import jpabook.jpashop.entity.Member;
import jpabook.jpashop.exception.NewPasswordNotMatchedException;
import jpabook.jpashop.exception.PasswordWrongException;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;


    private boolean isIdPresent(String id) {
        return memberRepository.findById(id).isPresent();
    }
    public void insertMember(MemberFormDto memberFormDto) throws Exception {
        boolean present = isIdPresent(memberFormDto.getId());
        if (present) throw new Exception("아이디가 이미 있습니다.  아이디 확인 후 가입하는 사이 누군가 새로 가입하였습니다.");
        Member member = new Member(memberFormDto.getId(), passwordEncoder.encode(memberFormDto.getPassword())
                , memberFormDto.getUsername(), memberFormDto.getEmail(), memberFormDto.getHp(), memberFormDto.getBirthday());
        memberRepository.save(member);
    }

    public boolean idCheck(String id) {
        return isIdPresent(id);
    }

    // id, 이름, 이메일, 전화번호, 생일, 가입일, 최근로그인
    public MemberViewDto getMember(String id) {
        MemberViewDto memberViewDto = memberRepository.findMemberViewDto(id);
        return memberViewDto;
    }


    public void passwordUpdate(String curPassword, String newPassword, String newPasswordCheck, String id) throws Exception {
        Optional<Member> optional = memberRepository.findById(id);
        if (optional.isPresent()) {
            Member member = optional.get();
            if (!passwordEncoder.matches(curPassword, member.getPassword())) throw new PasswordWrongException();
            if (!newPassword.equals(newPasswordCheck)) throw new NewPasswordNotMatchedException();
            member.setPassword( passwordEncoder.encode(newPassword ));
            memberRepository.save(member);
        } else {
            throw new Exception("해당 유저 정보를 찾을 수 없습니다.");
        }
    }


    public void passwordCheck(String curPassword, String id) throws  Exception{
        Optional<Member> optional = memberRepository.findById(id);
        if(optional.isPresent()){
            Member member = optional.get();
            if (!passwordEncoder.matches(curPassword, member.getPassword())) throw new PasswordWrongException();
        }else{
            throw new Exception("해당 유저 정보를 찾을 수 없습니다.");
        }
    }

    public void updateMember(MemberUpdateDto memberUpdateDto)  throws  Exception{
        Optional<Member> optional = memberRepository.findById(memberUpdateDto.getId());
        if(optional.isPresent()){
            Member member = optional.get();
            member.setUsername(memberUpdateDto.getUsername());
            member.setEmail(memberUpdateDto.getEmail());
            member.setHp(memberUpdateDto.getHp());
            member.setBirthday(memberUpdateDto.getBirthday());
            memberRepository.save(member);
        }else{
            throw new Exception("해당 유저 정보를 찾을 수 없습니다.");
        }

    }

    //entity가 복잡해서 삭제 안되니까 delete_yn Y로 하는걸로
    public void deleteMember(String id) throws  Exception{
        Optional<Member> optional = memberRepository.findById(id);
        if(optional.isPresent()){
            Member member = optional.get();
            member.setMemberDelYn("Y");
            memberRepository.save(member);
        }else{
            throw new Exception("해당 유저 정보를 찾을 수 없습니다.");
        }
    }










    public  Page<IdeaListDto> memberIdeaListDto(Pageable pageable,String memberId){
        Page<IdeaListDto> pageResults = memberRepository.memberIdeaList(pageable,memberId);
        return pageResults;
    }

    public Page<ReplyMemberListDto> memberReplyListDto(Pageable pageable, String memberId){
        Page<ReplyMemberListDto> pageResults= memberRepository.memberReplyList(pageable,memberId);
        return pageResults;


    }


    public Page<ThumbsUpMemberListDto> memberThumbsUpListDto(Pageable pageable, String memberId) {
        Page<ThumbsUpMemberListDto> pageResults= memberRepository.memberThumbsUpList(pageable,memberId);
        return pageResults;
    }
}
