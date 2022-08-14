package com.sda.studysystem.configurations;

import com.sda.studysystem.services.implementations.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static com.sda.studysystem.utils.Constants.Security.*;

/**
 * Configuration for security
 *
 * @author Vinod John
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) {
        authenticationManagerBuilder.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String role = "ROLE_";
        String admin = AUTHORITY_ADMIN.replace(role, "");
        String teacher = AUTHORITY_TEACHER.replace(role, "");
        String student = AUTHORITY_STUDENT.replace(role, "");


        http.authorizeRequests()
                .antMatchers("/", "/signup")
                .permitAll()
                .antMatchers("/user/**")
                .hasRole(admin)
                .antMatchers("/school/**")
                .hasAnyRole(teacher, student, admin)
                .antMatchers("/teacher/**")
                .hasAnyRole(teacher, admin)
                .and()
                .httpBasic()
                .and()
                .formLogin()
                .and()
                .logout().permitAll(false).logoutSuccessUrl("/")
                .and()
                .csrf().disable();
    }
}
