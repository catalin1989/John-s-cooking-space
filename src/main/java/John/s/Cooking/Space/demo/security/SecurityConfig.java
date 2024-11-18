package John.s.Cooking.Space.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrfConfig -> csrfConfig.disable())
                .authorizeHttpRequests(authReq -> authReq.requestMatchers("/home", "/login", "/register", "/register/submit", "images/**", "/css/**", "/js/**").permitAll().anyRequest().authenticated())
                .formLogin(formLoginConfig -> formLoginConfig.loginPage("/login").defaultSuccessUrl("/home").
                        failureUrl("/login?error=true"))
                .oauth2Login(oauth2LoginConfig->oauth2LoginConfig.loginPage("/login")
                        .defaultSuccessUrl("/home",true)
                        .failureUrl("/login?error=true"))
                .logout(logoutConfig -> logoutConfig.logoutSuccessUrl("/home?logout=true").invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID"));
        http.httpBasic(withDefaults());
        return http.build();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /*@Bean
    ClientRegistrationRepository clientRegistrationRepository() {
        ClientRegistration github = gitHubClientRegistration();
        ClientRegistration facebook = facebookClientRegistration();
        return new InMemoryClientRegistrationRepository(github, facebook);
    }

    private ClientRegistration gitHubClientRegistration() {
        return CommonOAuth2Provider.GITHUB.getBuilder("gitHub")
                .clientId("Ov23lixVDe7k7Mt2ffqs")
                .clientSecret("201714f1f8e3e20a133cfc72ab17610449c2a2cd")
                .build();
    }

    private ClientRegistration facebookClientRegistration() {
        return CommonOAuth2Provider.FACEBOOK.getBuilder("facebook")
                .clientId("1772114800271721")
                .clientSecret("8fb9eafbb6b10552448c6643eb975c7a")
                .build();
    }*/
}
