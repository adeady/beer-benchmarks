package adeady.benchmarks.user

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

        ResponseEntity.status(HttpStatus.CREATED).body(user.resource)

    }

    @RequestMapping(method=RequestMethod.GET)
    ResponseEntity list() {

        List reps = User.findAll()*.resource

        ResponseEntity.ok(reps)
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    ResponseEntity list(@PathVariable String id) {

        User user = User.get(id.toInteger())

        ResponseEntity.ok(user.resource)
    }


}
