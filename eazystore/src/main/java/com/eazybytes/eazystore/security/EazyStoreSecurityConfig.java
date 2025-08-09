package com.eazybytes.eazystore.security;

import com.eazybytes.eazystore.filter.JWTTokenValidatorFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class EazyStoreSecurityConfig {
        private final List<String> publicPaths;

        @Bean
        SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http)
                throws Exception {
            return http.csrf(csrfConfig->csrfConfig.disable())
                    .cors(corsConfig -> corsConfig.configurationSource(corsConfigurationSource()))
                    .authorizeHttpRequests((requests) ->
            {
                publicPaths.forEach(path ->
                        requests.requestMatchers(path).permitAll());
                        requests.anyRequest().authenticated();
            }
                    )
                    .addFilterBefore(new JWTTokenValidatorFilter(publicPaths), BasicAuthenticationFilter.class)
                    .formLogin(withDefaults())
                    .httpBasic(withDefaults()).build();

        }



//        this method is to create multiple users and store them in the memory,because everyone should have diff credentials..and login with their own credentials,all crud operations are possible,builtin methods are there
//        @Bean
//        public UserDetailsService userDetailsService() {
//            var user1 = User.builder().username("Sadhika")
//                    .password("$2a$12$axJz3c5a8MJMRl53QaK0/OVMcmyPf9snmlVYSZ8TjwzwJ4tpDU0nq").roles("USER").build();
//            var user2 = User.builder().username("admin")
//                    .password("$2a$12$xeED/2cwstf2Rlcvg3tIU.8VokhD.7Ja5bwj73AlMkHUAOrFagGk6").roles("USER","ADMIN").build();
////            InMemoryUserDetailsManager to store all user details in a hash map in the backend
//            return new InMemoryUserDetailsManager(user1, user2);
//        }


        @Bean
        public AuthenticationManager authenticationManager(
                AuthenticationProvider authenticationProvider) {
            var providerManager = new ProviderManager(authenticationProvider);
            return providerManager;
        }


        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

    @Bean
    public CompromisedPasswordChecker compromisedPasswordChecker() {
        return new HaveIBeenPwnedRestApiPasswordChecker();
    } //to check if user sets a weak password..returns boolean value


        @Bean
            public CorsConfigurationSource corsConfigurationSource() {
                CorsConfiguration config = new CorsConfiguration();
                config.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
                config.setAllowedMethods(Collections.singletonList("*"));
                config.setAllowedHeaders(Collections.singletonList("*"));
                config.setAllowCredentials(true);
                config.setMaxAge(3600L);

                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", config);
                return source;
        }

}
