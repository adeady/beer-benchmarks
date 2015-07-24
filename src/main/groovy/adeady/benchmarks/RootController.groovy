package adeady.benchmarks

import adeady.benchmarks.user.User
import com.wordnik.swagger.annotations.Api
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping(value="")
@Api(value = "root")
class RootController {

    @RequestMapping(method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity get(HttpServletRequest request) {

        List<User> all = User.findAll()

        println all

        ResponseEntity.ok(all.collect{[name:it.nickname]})
    }

}
