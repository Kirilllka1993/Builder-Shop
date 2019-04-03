package com.vironit.kazimirov.config;

import com.vironit.kazimirov.service.impl.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailServiceImpl userDetailsService;

    private static String REALM="MY_TEST_REALM";

    @Autowired
    public void registerGlobalAuthentication(AuthenticationManagerBuilder auth, PasswordEncoder passwordEncoder) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.
//                csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/resources/**", "/**").permitAll()
//                .anyRequest().permitAll()
//                .and();
//
//        http.formLogin()
//                .loginPage("/login")
//                .loginProcessingUrl("/j_spring_security_check")
//                .failureUrl("/tryLogin")
//                .usernameParameter("j_username")
//                .usernameParameter("j_password")
//                .permitAll();

//        http.logout()
//                .permitAll()
//                .logoutUrl("/login")
//                .logoutSuccessUrl("/login")
//                .invalidateHttpSession(true);
//        http.authorizeRequests().
//                antMatchers("/admin/**")
//                .access("hasRole('ADMIN')")
//                .and()
//                .formLogin()
//                .loginPage("/login")
//                .failureUrl("/tryLogin")
//                .usernameParameter("username")
//                .passwordParameter("password")
//                .and()
////                .logout()
////                .logoutSuccessUrl("/login?logout")
////                .and()
//                .csrf()
//                .and()
//                .exceptionHandling().accessDeniedPage("/403");

//        http.authorizeRequests()
//        .antMatchers("/admin/**")
//              .access("hasRole('ADMIN')")
//                .anyRequest().authenticated()
//                .and()
//                .httpBasic()
//                .and()
//                .csrf().and()
//            .exceptionHandling().accessDeniedPage("/403");

        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .and().httpBasic().realmName(REALM).authenticationEntryPoint( getBasicAuthEntryPoint())
                .and()
                .csrf().and()
          //.exceptionHandling().accessDeniedPage("/403").and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
    }

    @Bean
    public RestAuthenticationEntryPoint getBasicAuthEntryPoint(){
        return new RestAuthenticationEntryPoint();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }




    //https://www.baeldung.com/spring-security-two-login-pages

    //https://www.baeldung.com/spring-security-login
//https://docs.spring.io/spring-security/site/docs/current/reference/html/jc.html

    //https://www.baeldung.com/securing-a-restful-web-service-with-spring-security

}
