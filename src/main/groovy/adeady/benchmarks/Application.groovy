package adeady.benchmarks

import adeady.benchmarks.user.User
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
        User bootStrap = new User(nickname: "adeady")
        bootStrap.save()
    }
}
