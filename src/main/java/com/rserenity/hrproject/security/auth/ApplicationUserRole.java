package com.rserenity.hrproject.security.auth;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.rserenity.hrproject.security.auth.ApplicationUserPermission.*;

public enum ApplicationUserRole {

    EMPLOYEE(Sets.newHashSet()),
    ADMIN(Sets.newHashSet(EMPLOYEE_WRITE,EMPLOYEE_READ,DAYOFF_READ,DAYOFF_WRITE)),
    ADMINTRAINEE(Sets.newHashSet(EMPLOYEE_READ,DAYOFF_READ));

    private final Set<ApplicationUserPermission> permissions;

    ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<ApplicationUserPermission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities(){
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_"+this.name()));
        return permissions;
    }



    public static ApplicationUserRole getRoleByName(String roleName){
        if(roleName.equals(ADMIN.name())){
            return ADMIN;
        }
        else if (roleName.equals(ADMINTRAINEE.name())){
            return ADMINTRAINEE;
        }
        return EMPLOYEE;
    }
}
