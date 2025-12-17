package br.com.videxium.videxiumapi.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SegurancaConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .authorizeRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/").permitAll()
                        .anyRequest().authenticated());
        return httpSecurity.build();
    }

}
