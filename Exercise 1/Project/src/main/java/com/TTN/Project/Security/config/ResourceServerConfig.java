package com.TTN.Project.Security.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    private static final String RESOURCE_ID = "bootcamp";

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId(RESOURCE_ID);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        /*http.authorizeRequests()
                .mvcMatchers("/user/**","/login")
                .hasAnyRole("ADMIN")
                .anyRequest()
                .permitAll()
                .and()
                .csrf()
                .disable();*/


        http.authorizeRequests()
                .mvcMatchers(HttpMethod.GET,"/","/user/**","/current/user")
                .hasAnyRole("USER","ADMIN","CUSTOMER")
                .mvcMatchers(HttpMethod.POST,"/registerUser","/customer/register","/seller/register")
                .hasAnyRole("USER","ADMIN")
                .mvcMatchers(HttpMethod.GET,"/customer/profile")
                .hasAnyRole("CUSTOMER")
                .anyRequest()
                .denyAll()
                .and()
                .csrf()
                .disable();








//        http.authorizeRequests().mvcMatchers(HttpMethod.GET, "/couponapi/coupons/{code:^[A-Z]*$}")
//                .hasAnyRole("USER", "ADMIN").
//                mvcMatchers(HttpMethod.POST, "/couponapi/coupons").
//                hasRole("ADMIN")
//                .anyRequest().denyAll().and().csrf().disable();

    }



}
