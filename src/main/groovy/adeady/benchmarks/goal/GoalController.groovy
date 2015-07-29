package adeady.benchmarks.goal

import adeady.benchmarks.user.User
import adeady.benchmarks.user.UserRepresentation
import com.wordnik.swagger.annotations.Api
import org.springframework.hateoas.Resource
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value="/api/goals")
@Api(value = "goals")
class GoalController {

    @RequestMapping(method=RequestMethod.POST)
    ResponseEntity<Resource<GoalRepresentation>> get(@RequestBody GoalCommand command) {

        User user = User.findByNickname(command.username)

        if(!user) {
            return ResponseEntity.badRequest().build()
        }

        Goal goal = new Goal(user: user, weight: command.weight, lift: command.lift).save(failOnError: true)

        ResponseEntity.status(HttpStatus.CREATED).body(goal.resource)

    }

    @RequestMapping(method=RequestMethod.GET)
    ResponseEntity<List<Resource<UserRepresentation>>> list() {

        List reps = Goal.findAll()*.resource

        ResponseEntity.ok(reps)
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    ResponseEntity<Resource<GoalRepresentation>> list(@PathVariable String id) {

        Goal user = Goal.get(id.toInteger())

        ResponseEntity.ok(user.resource)
    }


}
