package com.example.vuongstore.configuration;

import com.example.vuongstore.enums.RolePlay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
@Configuration
@EnableMethodSecurity
public class SercurityConfig {

    private static final String api = "/api/v1";

    private final String[] publicEntrypoint = {
            api +"/auth/log-in",
            api +"/auth/introspect",
            api +"/auth/refresh",
            api +"/auth/logout"
    };


    CustomJwtDecoder jwtDecoder;

    private static final String userEntryPoint = "/user";
    private static final String categoryEntryPoint = "/categories";
    private static final String orderEntryPoint = "/orders";
    private static final String brandsEntryPoint = "/brands";
    private static final String productEntryPoint = "/products";
    private static final String paymentEntryPoint = "/payment";
    private static final String shippingEntryPoint = "/shipping";


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(request -> request
                        .requestMatchers(HttpMethod.POST, publicEntrypoint)
                        .permitAll()

                        //category
                        .requestMatchers(HttpMethod.GET, api + categoryEntryPoint)
                        .permitAll()
                        .requestMatchers(HttpMethod.GET, api + categoryEntryPoint + "{id}" )
                        .permitAll()

                        //brands
                        .requestMatchers(HttpMethod.GET, api + brandsEntryPoint)
                        .permitAll()
                        .requestMatchers(HttpMethod.GET, api + brandsEntryPoint + "{id}")
                        .permitAll()

                        //user
                        .requestMatchers(HttpMethod.POST, api + userEntryPoint +"/register")
                        .permitAll()
                        .requestMatchers(HttpMethod.GET, api + userEntryPoint)
                        .hasRole(RolePlay.ADMIN.name())
                        .requestMatchers(HttpMethod.GET, api + "/myInfo")
                        .hasRole(RolePlay.USER.name())

                        //product
                        .requestMatchers(HttpMethod.GET, api + productEntryPoint + "/**")
                        .permitAll()
                        .requestMatchers(HttpMethod.GET, api + productEntryPoint + "{id}")
                        .permitAll()

                        //payment
                        .requestMatchers(HttpMethod.GET, api + paymentEntryPoint + "/**")
                        .permitAll()

                        //shipping
                        .requestMatchers(HttpMethod.GET, api + shippingEntryPoint + "/**")
                        .permitAll()

                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwtConfigurer -> jwtConfigurer
                                .decoder(jwtDecoder)
                                .jwtAuthenticationConverter(jwtAuthenticationConverter())
                        )
                        .authenticationEntryPoint(new JwtAuthenticationEntryPoint())
                )
                .csrf(AbstractHttpConfigurer::disable);

        return httpSecurity.build();
    }


    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter(){
        JwtGrantedAuthoritiesConverter converter = new JwtGrantedAuthoritiesConverter();
        converter.setAuthorityPrefix("");
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(converter);
        return jwtAuthenticationConverter;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("http://localhost:3000");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }


    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10);
    }


}
