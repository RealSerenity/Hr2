package com.rserenity.hrproject.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


@NoArgsConstructor
@Log4j2
@Data
@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity{

    @Column(name = "username",nullable = false, unique = true)
    private String username;

    @Column(name = "password",nullable = false)
    private String password;

    @Column(name = "salary")
    private Long salary;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<OffDayEntity> offdays = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<OffDayRequestEntity> offDayRequests = new HashSet<>();

//    @JsonIgnore
//    @OneToMany(mappedBy = "user")
//    private Set<OffDayEntity> offDays = new HashSet<>();

    @ElementCollection
    @Column(name = "authorities")
    private Set<SimpleGrantedAuthority> grantedAuthorities;

    @Column(name = "isAccountNonExpired")
    private boolean isAccountNonExpired;

    @Column(name = "isAccountNonLocked")
    private boolean isAccountNonLocked;

    @Column(name = "isCredentialsNonExpired")
    private boolean isCredentialsNonExpired;

    @Column(name = "isEnabled")
    private boolean isEnabled;

}
