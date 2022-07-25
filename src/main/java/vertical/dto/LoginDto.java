package vertical.dto;

import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
@Builder
public class LoginDto implements UserDetails {

    private String memail;
    private String mpassword;
    private Collection<? extends GrantedAuthority> authorities;
        // Set, List 사용시 final 써줘야함


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }
    // 패스워드 반환
    @Override
    public String getPassword() {
        return this.mpassword;
    }
    // 아이디 반환
    @Override
    public String getUsername() {
        return this.memail;
    }

    // 계정 인증 만료 여부 확인[true : 만료되지 않음]
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정 잠겨 있는지 확인[true : 잠겨있지 않음]
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 계정 패스워드가 만료 여부 확인[true : 만료되지 않음
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정 사용 가능한 여부 확인[true : 사용가능]
    @Override
    public boolean isEnabled() {
        return true;
    }
}
