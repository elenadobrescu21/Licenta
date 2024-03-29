package com.aii.platform.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.aii.platform.security.filter.AuthenticationTokenFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableTransactionManagement
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private AuthenticationEntryPoint authenticationEntryPoint;
	
//	@Autowired
//	public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder)
//	throws Exception {
//		authenticationManagerBuilder
//		.userDetailsService(userDetailsService)
//		.passwordEncoder(new BCryptPasswordEncoder());
//	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth
		.userDetailsService(userDetailsService)
		.passwordEncoder(new BCryptPasswordEncoder());
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	 @Bean
	    public AuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
	        AuthenticationTokenFilter authenticationTokenFilter = new AuthenticationTokenFilter();
	        authenticationTokenFilter.setAuthenticationManager(super.authenticationManagerBean());
	        return authenticationTokenFilter;
	   }


	    @Override
	    protected void configure(HttpSecurity httpSecurity) throws Exception {
	        httpSecurity
	                .csrf()
	                .disable()
	                .exceptionHandling()
	                .authenticationEntryPoint(authenticationEntryPoint)
	                .and()
	                .sessionManagement()
	                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	                .and()
	                .authorizeRequests()
	                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
	                .antMatchers(HttpMethod.POST, "/testUpdateCarteCompleta/**").permitAll()
	                .antMatchers("/auth/**").permitAll()
	                .antMatchers("/user/**").permitAll()
	                .antMatchers(HttpMethod.DELETE, "/article/**").permitAll()
	                .antMatchers(
	                        HttpMethod.GET,
	                        "/",
	                        "/**/",
	                        "/**/*.pdf",
	                        "/*.html",
	                        "/favicon.ico",
	                        "/**/*.html",
	                        "/**/*.css",
	                        "/**/*.js",
	                        "/**/**/*.css",
	                        "/**/**/*.js",
	                        "/**/**/*.html",
	                        "/**/**/**/*.css",
	                        "/**/**/**/*.js",
	                        "/**/**/**/*.html",
	                        "/**/**/**/**/*.css",
	                        "/**/**/**/**/*.js"          
	                ).permitAll()
	                .anyRequest().authenticated();

	        // Custom JWT based authentication
	        httpSecurity
	                .addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
	        
	        httpSecurity.headers().frameOptions().disable();
	    }
	
	

}
