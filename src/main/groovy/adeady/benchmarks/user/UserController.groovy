package adeady.benchmarks.user

import adeady.benchmarks.User
import com.wordnik.swagger.annotations.Api
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping(value="/users")
@Api(value = "users")
class UserController {

    @RequestMapping(method=RequestMethod.POST)
    ResponseEntity get(@RequestBody UserCommand command) {

        User user = new User(nickname: command.name).save(failOnError: true)

        def representation = new UserRepresentation(name:user.nickname)

        def temp = ResponseEntity.status(HttpStatus.CREATED).body(representation)

        temp

    }

    @RequestMapping(method=RequestMethod.GET)
    ResponseEntity list() {

        def representation = User.findAll().collect { new UserRepresentation(user:it)}

        new ResponseEntity(representation, HttpStatus.CREATED)

    }

}
