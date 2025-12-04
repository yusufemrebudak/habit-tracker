package com.yusufbudak.habittracker.Security;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final HabitUserDetailsService habitUserDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // CSRF'yi lambda ile disable ediyoruz (Security 6 syntax)
                .csrf(csrf -> csrf.disable())

                // Session'ı STATELESS yapıyoruz (JWT ile çalıştığımız için)
                .sessionManagement(sess -> sess
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // Endpoint yetkilendirmeleri
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()  // register & login serbest
                        .anyRequest().authenticated()                 // diğer her şey token ister
                )

                // Hangi UserDetailsService'i kullanacağını söylüyoruz
                .userDetailsService(habitUserDetailsService)

                // JWT filter'ımızı UsernamePasswordAuthenticationFilter'dan önce ekliyoruz
                // burada jwtAuthenticationFilter bunu filtreye ekliyoruz ve istek atıldığında bunun içindeki doFilterExternal çalışıyor.
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}