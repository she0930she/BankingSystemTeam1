package com.learning;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import com.learning.filter.JwtFilter;



import javax.sql.DataSource;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private JwtFilter jwtFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
//                .antMatchers(HttpMethod.POST, "/api/customer/register").permitAll()
//                .antMatchers("/api/customer/*").hasAuthority("CUSTOMER")
//                .antMatchers("/api/customer/{username}/account/{accountNumber}").hasAuthority("CUSTOMER")
//                .antMatchers("/api/staff/*").hasAuthority("STAFF")
//                .antMatchers(HttpMethod.POST, "/api/admin/register").permitAll()
//                .antMatchers(HttpMethod.POST, "/api/authenticate/*").permitAll()
//                .antMatchers("/api/admin/staff").hasAuthority("SUPER_ADMIN")
//                .antMatchers(HttpMethod.GET, "/api/util/*").permitAll() // For testing purpose
//                .anyRequest().authenticated()
                .antMatchers(HttpMethod.POST, "/api/customer/register").permitAll()
                .antMatchers("/api/customer/{username}/*").hasAuthority("CUSTOMER")
                .antMatchers(HttpMethod.PUT, "/api/customer/transfer").hasAuthority("CUSTOMER")
                .antMatchers("/api/staff/*").hasAuthority("STAFF")
                .antMatchers(HttpMethod.POST, "/api/admin/register").permitAll()
                .antMatchers(HttpMethod.POST, "/api/authenticate/*").permitAll()
                .antMatchers("/api/admin/staff").hasAuthority("SUPER_ADMIN")
                .antMatchers(HttpMethod.GET, "/api/util/*").permitAll() // For testing purpose
                .antMatchers("/api/util/customer/{username}").permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf()
                .disable();
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        
        http.cors(); 
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	
    	
        auth.jdbcAuthentication().dataSource(dataSource)
                .passwordEncoder(passwordEncoder())
                .usersByUsernameQuery("SELECT username, password, enabled FROM user WHERE username = ?")
                .authoritiesByUsernameQuery("SELECT username, authority FROM authority WHERE username = ?");
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    

	
}
