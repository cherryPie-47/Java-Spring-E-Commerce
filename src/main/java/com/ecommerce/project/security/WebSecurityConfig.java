package com.ecommerce.project.security;

import com.ecommerce.project.model.AppRole;
import com.ecommerce.project.model.Role;
import com.ecommerce.project.model.User;
import com.ecommerce.project.repositories.RoleRepository;
import com.ecommerce.project.repositories.UserRepository;
import com.ecommerce.project.security.jwt.AuthEntryPoint;
import com.ecommerce.project.security.jwt.AuthTokenFilter;
import com.ecommerce.project.security.jwt.JwtUtils;
import com.ecommerce.project.services.UserDetailsServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;
import java.util.Optional;
import java.util.Set;

@Configuration
// @EnableMethodSecurity
@EnableWebSecurity
public class WebSecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;

    private final DataSource dataSource;

    // Exception handler can be injected normally, only filter need to be registered as a bean
    private final AuthEntryPoint unauthorizedExceptionHandler;

    public WebSecurityConfig(DataSource dataSource, AuthEntryPoint unauthorizedExceptionHandler, UserDetailsServiceImpl userDetailsService) {
        this.dataSource = dataSource;
        this.unauthorizedExceptionHandler = unauthorizedExceptionHandler;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain SecurityChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((requests)
                -> requests
                .requestMatchers("/h2-console/**").permitAll()
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/v3/api-docs/**").permitAll()
                .requestMatchers("/swagger-ui/**").permitAll()
//                .requestMatchers("/api/public/**").permitAll()
//                .requestMatchers("/api/admin/**").permitAll()
                .requestMatchers("/api/test/**").permitAll()
                .requestMatchers("/images/**").permitAll()
                .anyRequest().authenticated());

        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.csrf(csrf -> csrf.disable());
        http.headers(headers -> headers.frameOptions(frameOptionsConfig -> frameOptionsConfig.sameOrigin()));

        http.exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedExceptionHandler));
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter(jwtUtils(), userDetailsService);
    }

    @Bean
    public JwtUtils jwtUtils() {
        return new JwtUtils();
    }

    @Bean
    public AuthEntryPoint unauthorizedExceptionHandler() {
        return new AuthEntryPoint();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
       DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(userDetailsService);
       authenticationProvider.setPasswordEncoder(passwordEncoder());
       return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    // This WebSecurityCustomizer is for the global setting
    // So endpoint we ignore here will bypass Spring Security completely
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web -> web.ignoring()
                .requestMatchers("/v2/api-docs",
                        "/configuration/ui",
                        "/swagger-resources/**",
                        "configuration/security",
                        "swagger-ui.html",
                        "webjars/**")
        );
    }

    @Bean
    public CommandLineRunner initData(RoleRepository roleRepository,
                                      UserRepository userRepository,
                                      PasswordEncoder passwordEncoder) {
        return args -> {
            Optional<Role> userRoleOptional = roleRepository.findByRoleName(AppRole.ROLE_USER);
            if (userRoleOptional.isEmpty()) {
                Role newUserRole = new Role(AppRole.ROLE_USER);
                roleRepository.save(newUserRole);
                userRoleOptional = roleRepository.findByRoleName(AppRole.ROLE_USER);
            }
            Optional<Role> sellerRoleOptional = roleRepository.findByRoleName(AppRole.ROLE_SELLER);
            if (sellerRoleOptional.isEmpty()) {
                Role newSellerRole = new Role(AppRole.ROLE_SELLER);
                roleRepository.save(newSellerRole);
                sellerRoleOptional = roleRepository.findByRoleName(AppRole.ROLE_SELLER);
            }
            Optional<Role> adminRoleOptional = roleRepository.findByRoleName(AppRole.ROLE_ADMIN);
            if (adminRoleOptional.isEmpty()) {
                Role newAdminRole = new Role(AppRole.ROLE_ADMIN);
                roleRepository.save(newAdminRole);
                adminRoleOptional = roleRepository.findByRoleName(AppRole.ROLE_ADMIN);
            }

            Set<Role> userRoles = Set.of(userRoleOptional.get());
            Set<Role> sellerRoles = Set.of(sellerRoleOptional.get());
            Set<Role> adminRoles = Set.of(adminRoleOptional.get());

            if (!userRepository.existsByUsername("user1")) {
                User user1 = new User("user1", "user1@gmail.com", passwordEncoder.encode("user1"));
                // user1.setRoles(userRoles);
                userRepository.save(user1);
            }
            if (!userRepository.existsByUsername("seller1")) {
                User seller1 = new User("seller1", "seller1@gmail.com", passwordEncoder.encode("seller1"));
                // seller1.setRoles(sellerRoles);
                userRepository.save(seller1);
            }
            if (!userRepository.existsByUsername("admin")) {
                User admin = new User("admin", "admin@gmail.com", passwordEncoder.encode("admin"));
                // user1.setRoles(adminRoles);
                userRepository.save(admin);
            }
            if (!userRepository.existsByUsername("cheesecake")) {
                User cheesecake = new User("cheesecake", "cheesecake@gmail.com", passwordEncoder.encode("cheesecake"));
                // cheesecake.setRoles(adminRoles);
                userRepository.save(cheesecake);
            }
            userRepository.findByUsername("user1").ifPresent(user -> {
                user.setRoles(userRoles);
                userRepository.save(user);
            });
            userRepository.findByUsername("seller1").ifPresent(user -> {
                user.setRoles(sellerRoles);
                userRepository.save(user);
            });
            userRepository.findByUsername("admin").ifPresent(user -> {
                user.setRoles(adminRoles);
                userRepository.save(user);
            });
            userRepository.findByUsername("cheesecake").ifPresent(user -> {
                user.setRoles(adminRoles);
                userRepository.save(user);
            });

        };
    }
}
