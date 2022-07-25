package vertical.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import vertical.service.MemberService;
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {// 웹 시큐리티 설정 관련 상속클래스

    @Autowired
    private MemberService memberService;


    @Override // 재정의(WebSecurityConfigurerAdapter)
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .antMatchers("/**").permitAll()   // 모든 url 모든 접근 허용
                .and()
                .formLogin() // 로그인페이지 보안설정
                .loginPage("/member/login") // 이메일, 비밀번호 입력받을 페이지 url
                .loginProcessingUrl("/member/logincontroller")// 로그인 처리할 url
                .failureUrl("/member/login/error")
                .usernameParameter("memail") // 로그인시 이메일로 입력받을 변수명
                .passwordParameter("mpassword") // 로그인시 패스워드로 입력받을 변수명
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout")) // 로그아웃 처리할 url 정의
                .logoutSuccessUrl("/") // 로그아웃 성공시 이동할 url -> 최상위
                .invalidateHttpSession(true) // 로그아웃시 세션 초기화화
                .and()
                .csrf() //서버에게 요청할 수 있는 페이지 제한
                .ignoringAntMatchers("/board/save")
                .ignoringAntMatchers("/board/update")
                .ignoringAntMatchers("/board/delete")
                .ignoringAntMatchers("/member/signup")
                .ignoringAntMatchers("/member/update")
                .ignoringAntMatchers("/member/delete")
                .ignoringAntMatchers("/member/passwordcheck")
                .ignoringAntMatchers("/member/pwupdate") // ajax
                .ignoringAntMatchers("/reply/save")
                .ignoringAntMatchers("/reply/update")
                .ignoringAntMatchers("/reply/delete")
                .ignoringAntMatchers("/reply/reresave")
                .ignoringAntMatchers("/member/logincontroller")
                .ignoringAntMatchers("/review/**")
                .ignoringAntMatchers("/board/mainboardlist")
                .ignoringAntMatchers("/board/likesave")
                .and().csrf().disable();
    }

    @Override // 인증(로그인)관리 메소드 재정의
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(memberService).passwordEncoder(new BCryptPasswordEncoder() );
    }
}
