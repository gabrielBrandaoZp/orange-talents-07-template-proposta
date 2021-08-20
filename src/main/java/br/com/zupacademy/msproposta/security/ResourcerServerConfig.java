package br.com.zupacademy.msproposta.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

@Configuration
@EnableWebSecurity
public class ResourcerServerConfig extends WebSecurityConfigurerAdapter {

    private static final String[] V1_ENDPOINTS = {"/api/v1/**"};
    private static final String[] V1_ENDPOINTS_OBSERVABILITY = {"/actuator/**"};

    private static final String USER_ROLE_NAME = "ROLE_USER";
    private static final String ADMIN_ROLE_NAME = "ROLE_ADMIN";

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, V1_ENDPOINTS)
                    .hasAnyAuthority(USER_ROLE_NAME, ADMIN_ROLE_NAME)
                .antMatchers(V1_ENDPOINTS)
                    .hasAuthority(ADMIN_ROLE_NAME)
                .antMatchers(V1_ENDPOINTS_OBSERVABILITY)
                    .permitAll()
                .anyRequest()
                    .authenticated()
                .and()
                .oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(getJwtAuthenticationConverter());
    }

    /**
     * Método utilizado para definir o claimsName default para "authorities" e definir o prefixo como vazio
     * @return JwtAuthenticationConverter retorna um jwt authentication converter
     */
    private JwtAuthenticationConverter getJwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter  converter = new JwtGrantedAuthoritiesConverter();
        converter.setAuthoritiesClaimName("authorities");
        converter.setAuthorityPrefix("");

        JwtAuthenticationConverter authenticationConverter = new JwtAuthenticationConverter();
        authenticationConverter.setJwtGrantedAuthoritiesConverter(converter);
        return authenticationConverter;
    }
}
