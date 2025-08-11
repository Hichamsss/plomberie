// SecurityConfig.java
package be.plomberie.demo.config;

import be.plomberie.demo.service.CompteUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http,
                                            AuthenticationSuccessHandler successHandler,
                                            AuthenticationManager authManager) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/login", "/register", "/mot-de-passe/**",
                                 "/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()
                .requestMatchers("/compte/**").hasRole("USER")
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().permitAll()
            )
            .authenticationManager(authManager)
            .formLogin(form -> form
                .loginPage("/login")
                .successHandler(successHandler)
                .permitAll()
            )
            .logout(l -> l.logoutUrl("/logout").logoutSuccessUrl("/").permitAll());
        return http.build();
    }

    @Bean
    AuthenticationSuccessHandler successHandler() {
        return (req, res, auth) -> {
            boolean admin = auth.getAuthorities().stream().anyMatch(a -> "ROLE_ADMIN".equals(a.getAuthority()));
            res.sendRedirect(admin ? "/admin" : "/compte");
        };
    }

    @Bean PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }

    // ➜ AuthManager avec 2 providers : in-memory (admin) puis DB (comptes client)
    @Bean
    AuthenticationManager authenticationManager(CompteUserDetailsService dbUds,
                                                UserDetailsService users, // vient de SecurityUserConfig (InMemoryUserDetailsManager)
                                                PasswordEncoder enc) {
        DaoAuthenticationProvider inMemory = new DaoAuthenticationProvider();
        inMemory.setUserDetailsService(users);
        inMemory.setPasswordEncoder(enc);

        DaoAuthenticationProvider database = new DaoAuthenticationProvider();
        database.setUserDetailsService(dbUds);
        database.setPasswordEncoder(enc);

        return new ProviderManager(List.of(inMemory, database));
    }
}
