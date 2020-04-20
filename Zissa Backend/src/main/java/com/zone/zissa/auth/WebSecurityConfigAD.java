package com.zone.zissa.auth;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * The WebSecurityConfigAD Class.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfigAD extends WebSecurityConfigurerAdapter {

    @Value("${ldap-domain}")
    private String ldapDomain;

    @Value("${ldap-baseurl}")
    private String ldapUrl;

    private static String realm = "MY_TEST_REALM";

    @Autowired
    private UserDetailService userDetailService;

    /**
     * The propertyConfigInDev method.
     * 
     * @return PropertySourcesPlaceholderConfigurer
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder authManagerBuilder) throws Exception {
        authManagerBuilder.authenticationProvider(activeDirectoryLdapAuthenticationProvider(userDetailService));
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(Arrays.asList(activeDirectoryLdapAuthenticationProvider(userDetailService)));
    }

    /**
     * The activeDirectoryLdapAuthenticationProvider method.
     * 
     * @return AuthenticationProvider
     */
    @Bean
    public AuthenticationProvider activeDirectoryLdapAuthenticationProvider(UserDetailService userDetailService) {
        ActiveDirectoryLdapAuthenticationProvider provider = new ActiveDirectoryLdapAuthenticationProvider(ldapDomain,
                ldapUrl);
        provider.setConvertSubErrorCodesToExceptions(true);
        provider.setUseAuthenticationRequestCredentials(true);
        provider.setUserDetailsContextMapper(userDetailService);
        return provider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().authorizeRequests()
                .antMatchers("/v1/attributes/**", "/v1/categories/**", "/v1/roles/**", "/v1/users/**")
                .hasAuthority("ROLE_ADMIN").anyRequest().authenticated().and().httpBasic().realmName(realm)
                .authenticationEntryPoint(getBasicAuthEntryPoint()).and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

                .addFilterAfter(new CORSFilter(), BasicAuthenticationFilter.class).csrf().disable();

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources", "/configuration/security",
                "/webjars/**", "/swagger-resources/configuration/ui", "/swagger-ui.html",
                "/swagger-resources/configuration/security", "/v1/categories/{categoryID}/attributes",
                "/v1/allocations/projects");

    }

    @Bean
    public CustomBasicAuthenticationEntryPoint getBasicAuthEntryPoint() {
        return new CustomBasicAuthenticationEntryPoint();
    }
}
