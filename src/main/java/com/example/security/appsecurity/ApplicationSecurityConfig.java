package com.example.security.appsecurity;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static com.example.security.appsecurity.ApplicationUserRole.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {


    private  final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "index", "/css/*", "/js/*")
                .permitAll()
                //ORDER OF ANTMATCHERS IS IMPORTANT
                .antMatchers("/api/**").hasRole(STUDENT.name() )
//                .antMatchers(HttpMethod.DELETE,"management/api/**").hasAuthority(ApplicationUserPermission.COURSE_WRITE.getPermission())
//                .antMatchers(HttpMethod.PUT, "management/api/**").hasAuthority(ApplicationUserPermission.COURSE_WRITE.getPermission())
//                .antMatchers(HttpMethod.POST, "management/api/**").hasAuthority(ApplicationUserPermission.COURSE_WRITE.getPermission())
//                .antMatchers(HttpMethod.GET,"managemnt/api/**").hasAnyRole(ADMIN.name(), NEWHIRE.name())

                .anyRequest()
                .authenticated()
                .and()
                .formLogin();

    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails samUser = User.builder()
                .username("Sam")
                .password(passwordEncoder.encode("pass123"))
                .authorities(STUDENT.getGrantedAuthorities())
                .build();

        UserDetails jhonUser = User.builder()
                .username("Jhon")
                .password(passwordEncoder.encode("qwerty"))
           //     .roles(ADMIN.name())
                .authorities(ADMIN.getGrantedAuthorities())
                .build();

        //Creating a new user for Role based auth.
        UserDetails jedUser = User.builder()
                .username("Jed")
                .password(passwordEncoder.encode("qwerty123"))
           //     .roles(NEWHIRE.name()) //ROLE_NEWHIRE
                .authorities(NEWHIRE.getGrantedAuthorities())
                .build();

        return new InMemoryUserDetailsManager(
                samUser,
                jhonUser,
                jedUser
        );
    }
}
