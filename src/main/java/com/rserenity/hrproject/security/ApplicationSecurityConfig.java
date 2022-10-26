package com.rserenity.hrproject.security;

import com.rserenity.hrproject.business.services.OffDayServices;
import com.rserenity.hrproject.business.services.UserServices;
import com.rserenity.hrproject.security.Session.JSessionServices;
import com.rserenity.hrproject.ui.controller.TemplateController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static com.rserenity.hrproject.security.auth.ApplicationUserRole.*;

@EnableWebSecurity
@Configuration
// for @PreAuthorize()
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserServices userServices;

    @Autowired
    OffDayServices offDayServices;

    @Autowired
    JSessionServices jSessionServices;



    @Override
    protected void configure(HttpSecurity http) throws Exception {
            http
    //                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
    //                .and()
    //                .exceptionHandling().authenticationEntryPoint(new implementedclass)  // implement AuthenticationEntryPoint
                    .csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/","/index","/css/*","/js/*","/api/user/getAll", "/**",
                            "/api/offdays/getAll","/api/offdays/createoffday","/api/user/createUser", "/api/sessions/getAll").permitAll()
                    .antMatchers("/api/**").hasRole(EMPLOYEE.name())
    //                .antMatchers(HttpMethod.DELETE,"/management/api/**").hasAuthority(DAYOFF_WRITE.getPermission())
    //                .antMatchers(HttpMethod.POST,"/management/api/**").hasAuthority(DAYOFF_WRITE.getPermission())
    //                .antMatchers(HttpMethod.PUT,"/management/api/**").hasAuthority(DAYOFF_WRITE.getPermission())
    //                .antMatchers(HttpMethod.GET,"/management/api/**").hasAnyRole(ADMIN.name(), ADMINTRAINEE.name())
                    .anyRequest()
                    .fullyAuthenticated()
                    .and()
                    .formLogin()
                        .loginPage("/login").permitAll()
//                        .defaultSuccessUrl("/offdays", true)
                        .passwordParameter("password")
                        .usernameParameter("username")

                    .and()
                    .rememberMe()
                        .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21))
                        .key("somethingverysecured")
                        .rememberMeParameter("remember-me")

                    .and()
                    .logout()
                        .logoutUrl("/logout")
    //                  .logoutRequestMatcher(new AntPathRequestMatcher("/logout","GET"))
                        .clearAuthentication(true)
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID","remember-me")
                        .logoutSuccessUrl("/login");

                http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        }

//        private LogoutSuccessHandler logoutSuccessHandler(){
//        return  new LogoutSuccessHandler() {
//            @Override
//            public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//                System.out.println("logout success("+authentication.getName()+")");
//
//            }
//        };
//        }




    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userServices);
        return provider;
    }

}
