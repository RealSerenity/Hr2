package com.rserenity.hrproject.security;

import com.rserenity.hrproject.business.services.UserServices;
import com.rserenity.hrproject.security.Session.JSessionDto;
import com.rserenity.hrproject.security.Session.JSessionServices;
import com.rserenity.hrproject.security.auth.ApplicationUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

public class SecurityHandler/* implements AuthenticationSuccessHandler*/ {

//    @Autowired
//    UserServices userServices;
//
//    @Autowired
//    JSessionServices jSessionServices;
//
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,Authentication authentication) throws IOException, ServletException {
//        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
//        if (roles.contains("ROLE_"+ApplicationUserRole.ADMIN.name())) {
//            response.sendRedirect("/adminPanel");
//            System.out.println("username -> " + request.getParameter("username"));
//            createSession(request.getSession().getId(), request.getParameter("username"));
//        } else if (roles.contains("ROLE_"+ ApplicationUserRole.EMPLOYEE.name())) {
//            response.sendRedirect("/offdays");
//            createSession(request.getSession().getId(), request.getParameter("username"));
//        }
//    }
//
//    private void createSession(String jsessionId, String username){
//        JSessionDto jSessionDto = jSessionServices.createJSession(JSessionDto.builder()
//                .jSessionId(jsessionId)
//                .userId(userServices.loadUserDtoByUsername(username).getId())
//                .build());
//        System.out.println("created session -> " + jSessionDto);
//
//    }

}
