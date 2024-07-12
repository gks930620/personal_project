package jpabook.jpashop.config;

import jpabook.jpashop.service.login.CustomLoginSuccessHandler;
import jpabook.jpashop.service.login.LoginSuccessValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {


    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return new CustomLoginSuccessHandler("/");
    }

    @Autowired
    LoginSuccessValidator loginSuccessValidator;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/static/**");   //난 static에 다 놀거고 bootstrap 경로 하나하나 설정하기 귀찮을 뿐이고.
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/member/**").authenticated()
                .antMatchers("/idea/ideaForm").authenticated()
                .antMatchers("/idea/ideaInsert").authenticated()
                .antMatchers("/**").permitAll();

        http.formLogin()
                .loginPage("/login/login")
                .loginProcessingUrl("/login/loginCheck")    // loign.html에서  form  action=loginCheck가 되야됨  상대,절대 주의 .따로 컨트롤러만드는게 아니라.
                .defaultSuccessUrl("/")   // 파라미터에 ("", true) 로 하면  1.member 페이지 요청 2.권한걸림 => login 화면 이동  3. 로그인성공 4. "/"로 이동,  기본값은 false인데 false로 해야 로그인 후 member 페이지로 이동
                //defuatl를 했지만 security에서 제공하는 successHandler를 상속받은 후 커스텀하면  로그인 성공 시 특정 로직 등을 구현할 수 있다.  failHandler로 마찬가지.
                .usernameParameter("id")
                .passwordParameter("password")   //필드,DB다 password로 되어있으니까.
                .successHandler(successHandler())
                .permitAll();

        http.logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/login/logout"))   //기본값은 /logout   컨트롤러만드는게 아니라 로그아웃 url요청을 여기로 하면 logout처리해줌.
                .logoutSuccessUrl("/")   //logout하면 일단은 홈으로 가게 하자구( 로그아웃 처리 후 이동 url)
                .invalidateHttpSession(true);   //로그아웃시 세션제거, 근데 세션이 아니라 특성제거해야되는거 아냐?


        http.exceptionHandling()
                .accessDeniedPage("/denied");   // 로그인했는데 (인증)  권한이 없는(인가) 사용자 접근 시 보여줄 페이지

        http.csrf().disable();

    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception { //인증로직. 인데 직접 구현하는게 편함    JWT방식인가?
        auth.userDetailsService(loginSuccessValidator);     //loginSuccessValidator는  Service빈
    }

}