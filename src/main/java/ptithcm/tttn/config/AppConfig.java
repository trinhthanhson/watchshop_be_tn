package ptithcm.tttn.config;


import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import ptithcm.tttn.function.RoleName;
import ptithcm.tttn.service.impl.UserDetailsServiceImpl;

import javax.servlet.http.HttpServletResponse;


@Configuration
@EnableWebSecurity
public class AppConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceImpl userDetailsService;

    public AppConfig(UserDetailsServiceImpl customerUserDetails) {
        this.userDetailsService = customerUserDetails;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // Allow sessions for OAuth2
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authorizeRequests()
                .antMatchers("/api/user/product/all/product").permitAll()
                .antMatchers("/api/auth/**").permitAll()
                .antMatchers("/api/user/**").permitAll()  // Require authentication
                .antMatchers("/api/staff/**").hasAnyAuthority(RoleName.STAFF.getRoleName(), RoleName.MANAGER.getRoleName(), RoleName.SHIPPER.getRoleName(), RoleName.BUSINESS_STAFF.getRoleName(), RoleName.DIRECTOR.getRoleName(), RoleName.SALESPERSON.getRoleName())
                .antMatchers("/api/director/**").hasAnyAuthority(RoleName.DIRECTOR.getRoleName())
                .antMatchers("/api/shipper/**").hasAnyAuthority(RoleName.DELIVERY_STAFF.getRoleName())
                .antMatchers("/api/inventory/request/all/**").hasAnyAuthority(RoleName.STAFF.getRoleName(), RoleName.MANAGER.getRoleName(),
                        RoleName.WAREHOUSE_STAFF.getRoleName(), RoleName.WAREHOUSE_MANAGER.getRoleName(), RoleName.WAREHOUSE_KEEPER.getRoleName(), RoleName.BUSINESS_STAFF.getRoleName(), RoleName.DIRECTOR.getRoleName(), RoleName.SALESPERSON.getRoleName())
                .antMatchers("/api/manager/order-status/all").hasAnyAuthority(RoleName.MANAGER.getRoleName(), RoleName.STAFF.getRoleName(), RoleName.BUSINESS_STAFF.getRoleName(), RoleName.DIRECTOR.getRoleName(), RoleName.BUSINESS_STAFF.getRoleName(), RoleName.DIRECTOR.getRoleName(), RoleName.SALESPERSON.getRoleName(),RoleName.DELIVERY_STAFF.getRoleName())
                .antMatchers("/api/shipper/**").hasAnyAuthority(RoleName.DELIVERY_STAFF.getRoleName())
                .antMatchers("/api/manager/**").hasAnyAuthority(RoleName.MANAGER.getRoleName(), RoleName.STAFF.getRoleName(), RoleName.BUSINESS_STAFF.getRoleName(), RoleName.BUSINESS_STAFF.getRoleName(), RoleName.DIRECTOR.getRoleName(), RoleName.SALESPERSON.getRoleName())
                .antMatchers("/api/inventory/**").hasAnyAuthority(RoleName.WAREHOUSE_STAFF.getRoleName(), RoleName.WAREHOUSE_MANAGER.getRoleName(),
                        RoleName.WAREHOUSE_KEEPER.getRoleName(), RoleName.DIRECTOR.getRoleName())
                .antMatchers("/api/manager/inventory/**").hasAnyAuthority(RoleName.WAREHOUSE_STAFF.getRoleName(), RoleName.WAREHOUSE_MANAGER.getRoleName(),
                        RoleName.WAREHOUSE_KEEPER.getRoleName(), RoleName.DIRECTOR.getRoleName())

                .and()
                .addFilterBefore(new JwtTokenValidator(), UsernamePasswordAuthenticationFilter.class)
                .csrf().disable()
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .httpBasic()
                .and()
                .formLogin();  // Optional: Allows form-based login if needed
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    //.oauth2Login(oauth2login -> {
//        oauth2login
//                .loginPage("/login")
//                .successHandler((request, response, authentication) -> {
//                    // Log or handle authentication success
//                    OAuth2AuthenticationToken auth = (OAuth2AuthenticationToken) authentication;
//                    if (auth != null) {
//                        System.out.println("Authenticated user: " + auth.getPrincipal().getAttributes());
//                        response.sendRedirect("/user");
//                    } else {
//                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication failed");
//                    }
//                });
//    })
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(

                "http://localhost:3000", // React
                "http://localhost:5173",
                "http://localhost:4200",
                "http://localhost:5174",// Angular
                "https://wachshop-react.onrender.com",
                "https://www.sandbox.paypal.com/cgi-bin/webscr"
                // Add the deployed GitHub Pages URL

        ));
        configuration.setAllowedMethods(Collections.singletonList("*"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        configuration.setExposedHeaders(Arrays.asList("Authorization"));
        configuration.setMaxAge(3600L);

        return request -> configuration;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
