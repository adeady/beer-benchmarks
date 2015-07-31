package adeady.benchmarks.user

import adeady.benchmarks.goal.Goal
import grails.persistence.Entity
import groovy.transform.EqualsAndHashCode
import org.springframework.hateoas.Link
import org.springframework.hateoas.Resource

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo

@Entity
@EqualsAndHashCode
class User {

    String nickname

    static hasMany = [
            goals: Goal
    ]
    Resource<UserRepresentation> getResource() {
        Link selfLink = linkTo(UserController).slash(id).withSelfRel();

        def representation = new UserRepresentation(name:nickname, id: id)

        new Resource<UserRepresentation>(representation, selfLink)
    }
}