package pl.jstk.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import pl.jstk.security.CustomAccessDeniedHandler;


@Configuration
@EnableWebSecurity
@EnableWebMvc
public class WebConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler(
                        "/webjars/**",
                        "/img/**",
                        "/css/**")
                .addResourceLocations(
                        "/webjars/",
                        "classpath:/static/img/",
                        "classpath:/static/css/");
    }

    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/webjars/**", "/img/**", "/css/**").permitAll()
                .antMatchers("/books/delete").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST,"/books/**").hasRole("WRITE")
                .antMatchers(HttpMethod.GET,"/books/**").hasRole("READ")
                .antMatchers("/**").hasAnyRole("ADMIN", "USER")
                .and()
                .csrf().disable()
                .formLogin().loginPage("/logon").permitAll()
                .and()
                .httpBasic()
                .and()
                .logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/logon");

        http.exceptionHandling().accessDeniedPage("/error403");
        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler());
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("ram").password("{noop}ram123").roles("ADMIN","READ");
        auth.inMemoryAuthentication().withUser("ravan").password("{noop}ravan123").roles("USER","READ");
        auth.inMemoryAuthentication().withUser("kans").password("{noop}kans123").roles("USER","WRITE");
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }
}
