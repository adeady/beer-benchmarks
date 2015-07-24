package adeady.benchmarks

import groovy.util.logging.Log
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.web.SpringBootServletInitializer

@SpringBootApplication
@Log
public class Application extends SpringBootServletInitializer {


    public static void main(String[] args) {
        log.info "starting Spring Boot app..."
        SpringApplication.run(Application.class, args)
    }
}
