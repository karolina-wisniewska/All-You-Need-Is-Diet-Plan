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

  @Bean
  protected SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

    httpSecurity.
            authorizeHttpRequests(authorization -> authorization
                    .requestMatchers("/", "/login", "/registration").permitAll()
                    .requestMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()
                    .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")
                    .requestMatchers("/admin/**").hasRole("ADMIN")
                    .anyRequest()
                    .authenticated()
            )
            .formLogin(form -> form
                    .loginPage("/login").failureUrl("/login?error=true")
                    .defaultSuccessUrl("/user/home")
                    .usernameParameter("user_name")
                    .passwordParameter("password")
            )
            .logout(logout -> logout
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/login"))
            .exceptionHandling(exception -> exception
            .accessDeniedPage("/access-denied"));

    return httpSecurity.build();
  }

}
