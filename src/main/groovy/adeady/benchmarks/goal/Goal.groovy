package adeady.benchmarks.goal

import adeady.benchmarks.lift.Lift
import adeady.benchmarks.user.User
import adeady.benchmarks.user.UserController
import grails.persistence.Entity
import groovy.transform.ToString
import org.springframework.hateoas.Link
import org.springframework.hateoas.Resource

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo

@Entity
@ToString
class Goal {

    Integer weight
    Lift lift

    static belongsTo = [user: User]

    Resource<GoalRepresentation> getResource() {

        Link selfLink = linkTo(GoalController).slash(id).withSelfRel();
        Link userLink = linkTo(UserController).slash(user.id).withRel("user")

        def representation = new GoalRepresentation(username:user.nickname, lift: lift,
            weight: weight)

        new Resource<GoalRepresentation>(representation, selfLink, userLink)
    }
}
