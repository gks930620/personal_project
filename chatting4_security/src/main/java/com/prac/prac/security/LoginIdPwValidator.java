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
         user =option.get();
        }

        if (user == null) {
            return null;
        }
        String pw = user.getPw();
        String roles = user.getRole();
        return User.builder()
                .username(insertedId)
                .password(pw)
                .roles(roles)
                .build();
    }
}