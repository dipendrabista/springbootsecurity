package com.punojsoft.springbootsecurity.bootsecurity.config;

import com.punojsoft.springbootsecurity.bootsecurity.config.security.CustomAccessDeniedHandler;
import com.punojsoft.springbootsecurity.bootsecurity.config.security.CustomAuthenticationFailureHandler;
import com.punojsoft.springbootsecurity.bootsecurity.config.security.CustomAuthenticationProvider;
import com.punojsoft.springbootsecurity.bootsecurity.config.security.CustomAuthenticationSuccessHandler;
import com.punojsoft.springbootsecurity.bootsecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private CustomAccessDeniedHandler customAccessDeniedHandler;

    @Autowired
    private CustomAuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Autowired
    private UserService userService;
    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("admin").password("{noop}admin").roles("ADMIN", "USER")
//                .and()
//                .withUser("user").password("{noop}user").roles("USER");
        auth.authenticationProvider(customAuthenticationProvider).userDetailsService(userService);
    }

//    /**
//     * inmemory authentication example
//     */
//    @Bean
//    public UserDetailsService users() {
//        UserDetails user = User.builder()
//                .username("user")
//                .password(encoder().encode("password"))
//                .roles("USER")
//                .build();
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password(encoder().encode("password"))
//                .roles("USER", "ADMIN")
//                .build();
//        return new InMemoryUserDetailsManager(user, admin);
//    }

    @Bean
    BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /**
         * for h2-console access
         */
        http.headers().frameOptions().disable();
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/login", "/logout").permitAll()
                .antMatchers("/admin/registration").permitAll()
                .antMatchers("/h2-console/**").permitAll();
//                .anyRequest().authenticated();
        http.formLogin().successHandler(authenticationSuccessHandler).failureHandler(customAuthenticationFailureHandler)
                .and()
                .exceptionHandling().accessDeniedHandler(customAccessDeniedHandler)
                .and()
                .logout().deleteCookies("JSESSIONID")
                .and()
                //default token validity 2 weeks
                .rememberMe().key("uniqueAndSecret").tokenValiditySeconds(60 * 21 * 1000);
        /**
         * Session management related code
         */
        //allowing anly two session
        http.sessionManagement().maximumSessions(2);
        /**
         * session creation control
         */
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
        /**
         * session fixation attack
         */
        http.sessionManagement().sessionFixation().migrateSession();
        /**
         * prevent url from sending session information
         */
        http.sessionManagement().enableSessionUrlRewriting(false);
        /**
         * setting up session invalid and expired url
         */
        http.sessionManagement().invalidSessionUrl("/invalid-session")
                .maximumSessions(2).expiredUrl("/expired-session");
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    /**
     * embeded datasource H2 to store user credential with default schema
     * username:admin password:password
     * username:user pass:password
     *
     * @return
     */
//    @Bean
//    UserDetailsManager users() {
//        UserDetails user = User.builder()
//                .username("user")
//                .password("{bcrypt}$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW")
//                .roles("USER")
//                .build();
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password("{bcrypt}$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW")
//                .roles("USER", "ADMIN")
//                .build();
//        EmbeddedDatabaseBuilder databaseBuilder = new EmbeddedDatabaseBuilder().setType(H2)
//                .addScript("classpath:org/springframework/security/core/userdetails/jdbc/users.ddl");
//
//        JdbcUserDetailsManager users = new JdbcUserDetailsManager(databaseBuilder.build());
//        users.createUser(user);
//        users.createUser(admin);
//        return users;
//    }
}
