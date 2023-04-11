package pl.coderslab.allyouneedisdietplan.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
//  private final MyUserDetailsService userDetailsService;

  @Bean
  protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    http.
            authorizeHttpRequests()
            .requestMatchers("/").permitAll()
            .requestMatchers("/login").permitAll()
            .requestMatchers("/registration").permitAll()
            .requestMatchers("/resources/**").permitAll()
            .requestMatchers("/static/**").permitAll()
            .requestMatchers("/css/**").permitAll()
            .requestMatchers("/js/**").permitAll()
            .requestMatchers("/images/**").permitAll()
            .requestMatchers("/user/**").hasAuthority("ROLE_USER").anyRequest()
            .authenticated().and().csrf().disable().formLogin()
            .loginPage("/login").failureUrl("/login?error=true")
            .defaultSuccessUrl("/user/home")
            .usernameParameter("user_name")
            .passwordParameter("password")
            .and().logout()
            .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
            .logoutSuccessUrl("/login").and().exceptionHandling()
            .accessDeniedPage("/access-denied");

    return http.build();
  }

//  @Bean
//  public WebSecurityCustomizer webSecurityCustomizer() {
//    return (web) -> web
//            .ignoring()
//            .requestMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
//  }

}
