package com.prac.prac.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

//인증 로직 클래스
@Service
public class LoginIdPwValidator implements UserDetailsService {
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Override
    public UserDetails loadUserByUsername(String insertedId) throws UsernameNotFoundException {
        Optional<UserInfo> option = userInfoRepository.findById(insertedId);
        UserInfo user=null;
        if(option.isPresent()){
         user =userInfoRepository.findById(insertedId).get();
        }

        if (user == null) {
            return null;
        }
        String pw = user.getPw(); //  인코딩된 값이다....   db에 저장하는 곳에서는 무조건 인코딩해서 저장해야된다.
        String roles = user.getRole();
        return User.builder()
                .username(insertedId)
                .password(pw)
                .roles(roles)
                .build();
    }
}