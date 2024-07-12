package com.prac.prac.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration  //spring에서 설정파일임을 명시하는 annotation
@EnableWebSecurity // security 설정파일임을 명시하는 annotation
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {//security에서 제공하는 여러 기능과 설정을 담당한다.

    //비밀번호 인코딩.  security는 이게 안 되어있으면 알아서 에러일으켜서 꼭 해줘야함.
    // 그럼 무조건 insert하는 부분에서도 encoding 되서 넣어야 함.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Autowired
    LoginIdPwValidator loginIdPwValidator;  //로그인 과정을 직접 구현. UserDetailService를 상속받았다. 우리가 만들거다.


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/chat/**").authenticated()
                .antMatchers("/**").permitAll()
                .and()
                    .formLogin()
                    .defaultSuccessUrl("/", false)  //성공했을 때 이동 url.
                    .permitAll()
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))   //로그아웃 요청 url
                .logoutSuccessUrl("/");   // 로그아웃 후에 이동할 url

    }
    // 정적파일들은 인증필요없도록 (위에서 했던 antMatchers()만 설정하면 정적파일들도 인증 및 권한을 확인하게 된다. 그래서 정적파일들은 인증필요없도록 따로 설정해준다)
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/static/js/**", "/static/css/**", "/static/img/**", "/static/frontend/**");
    }

    //여기에 내가 만든 loginIdPwValidator를 넣어줌.
    // loginPage에서 id,pw입력  "로그인버튼"을 누르면 loginIdPwValidator의 로그인과정이 진행됨.
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(loginIdPwValidator);
    }
}