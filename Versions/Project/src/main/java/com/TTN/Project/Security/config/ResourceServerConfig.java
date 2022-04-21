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

        http.authorizeRequests()
                .mvcMatchers(HttpMethod.GET,"/","/user/**")
                .hasAnyRole("ADMIN","CUSTOMER","SELLER")
                .mvcMatchers("/customer/register","/seller/register")
                .permitAll()
                .mvcMatchers("/customer/**")
                .hasAnyRole("CUSTOMER")
                .mvcMatchers("/seller/**")
                .hasAnyRole("SELLER")
                .mvcMatchers("/admin/**")
                .hasAnyRole("ADMIN")
                .anyRequest()
                .permitAll()
                .and()
                .csrf()
                .disable();
    }



}
