package com.dianawu.blogApp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSpringSecurity {

    private UserDetailsService userDetailsService;
    @Autowired
    public WebSpringSecurity(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    // Password Encoder
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf((csrf) -> csrf.disable()) //CSRF stands for Cross-Site Request Forgery. By default, Spring Security enables protection against CSRF attacks.
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(new AntPathRequestMatcher("/images/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/page/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/login/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/register/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/admin/**"))
                        .hasAnyRole("ADMIN","GUEST")
                        .requestMatchers(new AntPathRequestMatcher("/")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/post/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/**/comments")).permitAll()
                        .anyRequest().authenticated())

                //.authorizeHttpRequests((authorize) -> authorize.anyRequest()
                        //.authenticated()) //any request to the application must be authenticated. In other words, the user needs to be logged in to access anything.
                        .formLogin(form -> form.loginPage("/login") //The login page is located at /login.
                                .defaultSuccessUrl("/admin/posts") // After successfully logging in, users are redirected to /admin/posts.
                                .loginProcessingUrl("/login") //where the login form submits its data.
                                .permitAll() // allows anyone to access the login page. Without it, you'd have a chicken and egg problem where you need to be logged in to access the login page
                        ).logout(logout -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .permitAll());

        return http.build();
    }

    // other methods
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }


}
