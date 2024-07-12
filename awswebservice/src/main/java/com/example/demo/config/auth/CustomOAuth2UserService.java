package com.example.demo.config.auth;

import com.example.demo.config.auth.dto.OAuthAttributes;
import com.example.demo.config.auth.dto.SessionUser;
import com.example.demo.domain.user.User;
import com.example.demo.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);  //약간 load한다는게 구글의 로그인유저정보를 가지고 온다.. 이 느낌인가보다.

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
        //구글, naver에서 로그인정보가져오는 로직은 여기..

        User user = saveOrUpdate(attributes); // 가져온 로그인정보가지고 우리가 관리하는 user로 만드는 거.
        httpSession.setAttribute("user", new SessionUser(user));  // entity인 user대신 SessionUser를 session에 저장

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }


    private User saveOrUpdate(OAuthAttributes attributes) { //구글에서 가지고온 유저정보가지고 DB에 회원저장함.
        User user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());  //여기서 기본적으로 ROLE.GUEST를 주도록 되어있음.
        // DB에 있다면 있는 정보 가지고 그대로 user가 되는거고, 없다면 새로 user 만듬
        return userRepository.save(user); //그대로 저장.    있는 경우에는 있는 거 그대로 저장 (update), 없으면 새로만들어진거 저장. (save)
    }
}
