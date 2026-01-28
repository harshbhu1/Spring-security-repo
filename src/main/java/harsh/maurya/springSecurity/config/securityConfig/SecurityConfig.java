package harsh.maurya.springSecurity.config.securityConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;
import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private DataSource datasource;
    /**
     * 
     * @param takes http request 
     * @return cusotmize security filter on the basis of the cusome filter . 
     * it enable the form login. 
     * it enable the http basic loging like with tools postman 
     * @throws Exception
     */
    @Bean
    SecurityFilterChain defauSecurityFilterChain(HttpSecurity http) throws Exception{
        /**
         * it specify that all http requests should be autenticated.
         */
        http.authorizeHttpRequests((request)-> request.requestMatchers("/h2-console/**").permitAll()
        .anyRequest().authenticated());

       http.headers(headers -> headers
        .frameOptions(frame -> frame.sameOrigin()));
        http.csrf((csrf) -> csrf.disable());

        
        //crate the the request state less or rest 

        http.sessionManagement((session) -> 
        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); //this line create the apis state less
        //  it disables the creation of sessions 
        /**
         * Provide a default login page
            Accept credentials via HTML form
            Handle session-based authentication
         */
        // http.formLogin(withDefaults()); 
        /**
         * Accept credentials from HTTP headers
            Use Authorization: Basic ...
            Be stateless by nature (unless sessions are enabled)
         */
        http.httpBasic(withDefaults());
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(){

        UserDetails user1 = User.withUsername("user1")
                            .password(passwordEncoder().encode("password1"))
                            .roles("USER")
                            .build();
        UserDetails admin = User.withUsername("admin")                  
                            .password(passwordEncoder().encode("passwordadmin"))
                            .roles("ADMIN")
                            .build();
        
        JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(datasource);
        userDetailsManager.createUser(user1);
        userDetailsManager.createUser(admin);
        return userDetailsManager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
