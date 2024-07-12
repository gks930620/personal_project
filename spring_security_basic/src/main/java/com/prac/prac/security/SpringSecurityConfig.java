package com.prac.prac.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
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
        // antMatchers()는 패턴입력,   그 후에 어떻게 처리할지
        // anyRequest는 앞에서말한 패턴외에 전부.  그 후에 어떻게 처리할지.
        http
                .authorizeRequests()
                .antMatchers("/manage").hasAuthority("ROLE_ADMIN")  // manage는 ADMIN이라는 권한까지 체크해야되고.   DB에는 "ADMIN" 만 있어야 됨.   "ROLE_"은 자동으로 붙음
                .antMatchers("/hch").authenticated()   //hch는 인증필요하고
                .antMatchers("/**").permitAll()  //그 외는 그냥 접근가능
                .and()
                    .formLogin()
                    .loginPage("/login")//로그인페이지 설정
                    .loginProcessingUrl("/loginProc")     //controller에 만들필요는 없고 login페이지에서 form태그의 action값을 의미
                    .usernameParameter("id") // form태그의 파라미터 이름   <input type="text" name="id">
                    .passwordParameter("pw") // <input type="password" name="pw">
                    .defaultSuccessUrl("/", false)  //성공했을 때 이동 url.    hch갈려다가 필터에걸려서 로그인 한 경우에는 그대로 hch가고, 그냥 login페이지 요청해서 성공했을 때는 "/"로 가고
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