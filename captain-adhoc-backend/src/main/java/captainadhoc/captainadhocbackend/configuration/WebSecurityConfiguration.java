package captainadhoc.captainadhocbackend.configuration;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;

@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable();

        http.cors().configurationSource(request -> {
            CorsConfiguration config =  new CorsConfiguration().applyPermitDefaultValues();
            config.addExposedHeader("Autorization");
            config.addAllowedMethod(HttpMethod.POST);
            config.addAllowedMethod(HttpMethod.PUT);
            return config;
        });
    }
}
