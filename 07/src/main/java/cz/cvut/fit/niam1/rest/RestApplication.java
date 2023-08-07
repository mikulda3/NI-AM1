package cz.cvut.fit.niam1.rest;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;

@SpringBootApplication
public class RestApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestApplication.class, args);
    }


    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                                .title("Rest Application")
                                .version("1.0")
                                .description("Rest Application")
                                .termsOfService("http://swagger.io/terms/")
                                .license(new License().name("Apache 2.0").url("http://springdoc.org")));

    }

    @Bean
    public FilterRegistrationBean<ShallowEtagHeaderFilter> shallowEtagHeaderFilter() {
        FilterRegistrationBean<ShallowEtagHeaderFilter> filterRegistrationBean = new FilterRegistrationBean<>( new ShallowEtagHeaderFilter());
        filterRegistrationBean.addUrlPatterns("/tour/strong");
        filterRegistrationBean.setName("etagFilter");
        return filterRegistrationBean;
    }
}