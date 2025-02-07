package fr.eni.projet.projeteni.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

//    @Bean
//    UserDetailsManager userDetailsManager(DataSource dataSource) {
//
//        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
//        jdbcUserDetailsManager.setUsersByUsernameQuery("select email,mot_de_passe,1 from utilisateurs where email=?");
//        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("select email,role from UTILISATEURS_ROLES where email=?");
//
//        return jdbcUserDetailsManager;
//
//    }

    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource) {

        UserDetails user1 = User.
                withDefaultPasswordEncoder()
                .username("admin@admin.com")
                .password("admin")
                .roles("ADMIN")
                .build();


        return new InMemoryUserDetailsManager(user1);
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(customizer -> customizer.disable());
        http.cors(customizer -> customizer.disable());
        http.authorizeHttpRequests((auth ->
        {
            auth.requestMatchers("/").permitAll();
            auth.requestMatchers("/CSS/*").permitAll();
            auth.requestMatchers("/assets/*").permitAll();
            auth.requestMatchers("/encheres/inscription").permitAll();
            auth.requestMatchers("/encheres").permitAll();
            auth.anyRequest().authenticated();
        }
        ));
        http.formLogin(f -> f
                .loginPage("/encheres/connexion")
                .permitAll());
        http.httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

}
