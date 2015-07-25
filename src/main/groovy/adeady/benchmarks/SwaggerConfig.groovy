package adeady.benchmarks

import com.google.common.base.Predicate
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.service.ApiInfo
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

import static com.google.common.base.Predicates.or
import static springfox.documentation.builders.PathSelectors.regex

//swagger borks testing because of the resources it imports, so no swagger in tests
@Profile(["!test"])
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket swaggerApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .paths(paths())
                .build()
    }

    private static Predicate<String> paths() {
        return or(
                regex("/users.*"),
                regex("")
        );    }

    private static ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Beer Benchmarks")
                .description("An app to record new Personal Records in weightlifting")
                .contact("Angela Deady")
                .version("1.0")
                .build();
    }
}
