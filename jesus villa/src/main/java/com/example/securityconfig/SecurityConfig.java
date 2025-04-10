package com.example.securityconfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.security.repository.UsuarioRepository;
import com.example.security.service.CustomUserDetailsService;
@Configuration
public class SecurityConfig {
    @Bean
    UserDetailsService userDetailsService(UsuarioRepository
            usuarioRepository) {
 return new CustomUserDetailsService(usuarioRepository);
}

    @Bean
    PasswordEncoder passwordEncoder() {
 return new BCryptPasswordEncoder();
 }

//DaoAuthenticationProvider es el encargado de autenticar usuarios.
//Usa el UserDetailsService para cargar los usuarios desde la base de datos.
// Compara la contraseña ingresada con la encriptada usando BCrypt.
    @Bean
    DaoAuthenticationProvider authenticationProvider(UserDetailsService
            userDetailsService, PasswordEncoder passwordEncoder) {
 DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
 authProvider.setUserDetailsService(userDetailsService);
 authProvider.setPasswordEncoder(passwordEncoder);
 return authProvider;
 }

// Crea el AuthenticationManager, que maneja la autenticación.
// Usa el proveedor de autenticación (DaoAuthenticationProvider) para verificar usuarios.
    @Bean
    AuthenticationManager authenticationManager(HttpSecurity http,
                                             UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) throws
            Exception {
 return http.getSharedObject(AuthenticationManagerBuilder.class)
		 .authenticationProvider(authenticationProvider(userDetailsService,
				 passwordEncoder))
				  .build();
				  }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
				  http
				  .csrf(csrf -> csrf.disable()) // Desactivar solo para pruebas
				  .authorizeHttpRequests(auth -> auth
				  .requestMatchers("/login", "/home", "/favicon.ico").permitAll()
				  .anyRequest().authenticated()
				  )
				  .formLogin(form -> form
				  .loginPage("/login")
				  .defaultSuccessUrl("/home", true)
				  .permitAll()
				  );
				  return http.build();
				  }
				 }