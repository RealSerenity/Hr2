package com.rserenity.hrproject.business.dto;

import com.rserenity.hrproject.data.entity.OffDayEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Log4j2
@Builder
public class UserDto implements UserDetails {

    private Long id;
    @NotEmpty(message = "Kullanıcı adı alanı boş bırakılamaz")
    @Size(min = 5,max = 50, message = "Kullanıcı adı uzunluğu '5' ile '50' arasında olmalıdır")
    private String username;
    @NotEmpty(message = "Şifre alanı boş bırakılamaz")
    @Size(min = 6,max = 20,message = "Şifre uzunluğu '6' ile 20 arasında olmalıdır")
    private String password;
    private Long salary;

    private Set<? extends GrantedAuthority> grantedAuthorities;
    private  boolean isAccountNonExpired;
    private boolean isAccountNonLocked;
    private  boolean isCredentialsNonExpired;
    private  boolean isEnabled;

    public UserDto(String username,
                   String password,
                   Long salary) {
        this.username = username;
        this.password = password;
        this.salary = salary;
        this.isAccountNonExpired = true;
        this.isAccountNonLocked = true;
        this.isCredentialsNonExpired = true;
        this.isEnabled = true;
    }

    public UserDto(Long id,
                   String username,
                   String password,
                   Long salary,
                   Set<? extends GrantedAuthority> grantedAuthorities,
                   boolean isAccountNonExpired,
                   boolean isAccountNonLocked,
                   boolean isCredentialsNonExpired,
                   boolean isEnabled) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.salary = salary;
        this.grantedAuthorities = grantedAuthorities;
        this.isAccountNonExpired = isAccountNonExpired;
        this.isAccountNonLocked = isAccountNonLocked;
        this.isCredentialsNonExpired = isCredentialsNonExpired;
        this.isEnabled = isEnabled;
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
