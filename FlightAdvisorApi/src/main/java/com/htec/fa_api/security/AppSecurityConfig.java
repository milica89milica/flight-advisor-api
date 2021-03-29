package com.htec.fa_api.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final UserDetailsServiceImpl userDetailsServiceImpl;


    public AppSecurityConfig(PasswordEncoder passwordEncoder, UserDetailsServiceImpl userDetailsServiceImpl) {
        this.passwordEncoder = passwordEncoder;
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
    protected void configure(HttpSecurity http) throws Exception { //todo !!!
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET,"/api/v1/city/**").hasRole("REGULAR")
                .antMatchers("/api/v1/city/**").hasRole("ADMIN")
                .antMatchers("/api/v1/userGroup/**").hasRole("ADMIN")
                .antMatchers("/api/v1/user/**").hasRole("ADMIN")
                .antMatchers("/","index").permitAll()
                //swager ui
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic()
        ;
    }
}
