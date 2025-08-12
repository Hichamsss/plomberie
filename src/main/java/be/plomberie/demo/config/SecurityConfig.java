package be.plomberie.demo.config;

import be.plomberie.demo.service.CompteUserDetailsService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
public class SecurityConfig {

    @Bean
    PasswordEncoder passwordEncoder() {
        // supporte {bcrypt}, {noop}, {argon2}, ...
        return org.springframework.security.crypto.factory.PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    DaoAuthenticationProvider adminAuthProvider(
            @Qualifier("adminUsers") UserDetailsService adminUsers,
            PasswordEncoder enc) {
        var p = new DaoAuthenticationProvider();
        p.setUserDetailsService(adminUsers);
        p.setPasswordEncoder(enc);
        return p;
    }

    @Bean
    DaoAuthenticationProvider dbAuthProvider(
            CompteUserDetailsService dbUds,
            PasswordEncoder enc) {
        var p = new DaoAuthenticationProvider();
        p.setUserDetailsService(dbUds);
        p.setPasswordEncoder(enc);
        return p;
    }

    @Bean
    AuthenticationSuccessHandler successHandler() {
        return (req, res, auth) -> {
            boolean admin = auth.getAuthorities().stream()
                    .anyMatch(a -> "ROLE_ADMIN".equals(a.getAuthority()));
            res.sendRedirect(admin ? "/admin" : "/compte");
        };
    }

    @Bean
    SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            AuthenticationSuccessHandler successHandler,
            DaoAuthenticationProvider adminAuthProvider,
            DaoAuthenticationProvider dbAuthProvider
    ) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/login", "/register", "/mot-de-passe/**",
                                 "/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()
                .requestMatchers("/compte/**").hasRole("USER")
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().permitAll()
            )
            .authenticationProvider(adminAuthProvider)
            .authenticationProvider(dbAuthProvider)
            .formLogin(f -> f.loginPage("/login").successHandler(successHandler)
                             .failureUrl("/login?error").permitAll())
            .logout(l -> l.logoutUrl("/logout").logoutSuccessUrl("/").permitAll());
        return http.build();
    }
}
