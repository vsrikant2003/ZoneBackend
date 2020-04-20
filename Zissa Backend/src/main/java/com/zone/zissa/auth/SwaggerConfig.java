package com.zone.zissa.auth;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicate;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.BasicAuth;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket productApi() {
        List<SecurityScheme> schemeList = new ArrayList<>();
        schemeList.add(new BasicAuth("basicAuth"));
        return new Docket(DocumentationType.SWAGGER_2).useDefaultResponseMessages(false).select()
                .apis(RequestHandlerSelectors.basePackage("com.zone.zissa.controller")).paths(postPaths()).build()
                .apiInfo(metaData()).securitySchemes(schemeList)
                .tags(new Tag("Allocation-mgmt-controller",
                        "Operations pertaining to allocation of resources in zissa"),
                        new Tag("Attribute-mgmt-controller", "Operations pertaining to attributes in zissa"),
                        new Tag("Category-mgmt-controller", "Operations pertaining to categories in zissa"),
                        new Tag("Resource-mgmt-controller", "Operations pertaining to resource management in zissa"),
                        new Tag("Role-mgmt-controller", "Operations pertaining to roles in zissa"),
                        new Tag("User-mgmt-controller", "Operations pertaining to users in zissa")

                );
    }

    private Predicate<String> postPaths() {
        return or(regex("/v1/users.*"), regex("/v1/roles.*"), regex("/v1/resources.*"), regex("/v1/categories.*"),
                regex("/v1/attributes.*"), regex("/v1/allocations.*"));
    }

    private ApiInfo metaData() {
        return new ApiInfoBuilder().title("ZISSA REST API")
                .description("This is the ZISSA Service API Documentation. You can use this web interface "
                        + "to explore the ZISSA Service API and send sample requests to check the output of the"
                        + " desired API request. You may need to enter valid credentials to explore the API")
                .version("1.0.0").license("All rights reserved.").licenseUrl("").build();
    }
}
