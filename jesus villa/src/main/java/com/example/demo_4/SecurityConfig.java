package com.example.demo_4;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @SuppressWarnings("deprecation")
    @Bean
    UserDetailsService userDetailsService() {
        // Definir usuarios en memoria
        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("admin123")
                .roles("ADMIN")
                .build();

        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("user123")
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(admin, user);
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Deshabilita CSRF (solo para pruebas)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/public/**").permitAll() // Acceso libre a "/public/**"
                        .requestMatchers("/login").permitAll() // Permitir acceso a la página de login sin autenticación
                        .requestMatchers("/admin/**").hasRole("ADMIN") // Solo para ADMIN
                        .anyRequest().authenticated() // Todas las demás rutas requieren autenticación
                )
                .formLogin(form -> form
                        .loginPage("/login") // Página personalizada de login
                        .defaultSuccessUrl("/home", true) // Redirección tras login exitoso
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout") // URL para cerrar sesión
                        .logoutSuccessUrl("/login?logout") // Redirección tras cerrar sesión
                        .permitAll()
                )
                .httpBasic(withDefaults()); // Activar autenticación HTTP Basic

        return http.build();
    }
}
