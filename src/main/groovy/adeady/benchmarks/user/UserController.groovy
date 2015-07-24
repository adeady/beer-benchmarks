package adeady.benchmarks.user

import adeady.benchmarks.User
import com.wordnik.swagger.annotations.Api
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

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

        def representation = User.findAll().collect { new UserRepresentation(name:it.nickname)}

        ResponseEntity.ok(representation)
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    ResponseEntity list(@PathVariable String id) {

        User user = User.get(id.toInteger())

        def representation =  new UserRepresentation(name:user.nickname)

        ResponseEntity.ok(representation)
    }

}
