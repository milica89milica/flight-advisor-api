package com.htec.fa_api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.SecureRandom;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;
    private final UserDetailsServiceImpl userDetailsServiceImpl;


    public AppSecurityConfig(UserDetailsServiceImpl userDetailsServiceImpl) {
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsServiceImpl);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET,"/route/**").hasAnyRole(UserRole.USER.toString(),UserRole.ADMIN.toString())
                .antMatchers(HttpMethod.GET,"/airline/**").hasAnyRole(UserRole.USER.toString(),UserRole.ADMIN.toString())
                .antMatchers(HttpMethod.GET,"/airport/**").hasAnyRole(UserRole.USER.toString(),UserRole.ADMIN.toString())
                .antMatchers("/user/about","/user/changePassword").hasAnyRole(UserRole.USER.toString(), UserRole.ADMIN.toString())
                .antMatchers("/userGroup/**").hasRole(UserRole.ADMIN.toString())
                .antMatchers("/user/comments**").hasAnyRole(UserRole.ADMIN.toString(),UserRole.USER.toString())
                .antMatchers("/comment/**").hasAnyRole(UserRole.USER.toString(), UserRole.ADMIN.toString())
                .antMatchers("/travel-estimate/**").hasAnyRole(UserRole.ADMIN.toString(), UserRole.USER.toString())
                .antMatchers("/route/**").hasRole(UserRole.ADMIN.toString())
                .antMatchers("/airline/**").hasRole(UserRole.ADMIN.toString())
                .antMatchers("/airport/**").hasRole(UserRole.ADMIN.toString())
                .antMatchers("/user/**").hasRole(UserRole.ADMIN.toString())
                .antMatchers("/", "index").permitAll()
                //.antMatchers("swagger").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic()
                .and()
                .logout().clearAuthentication(true)
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)

        ;
    }
}
