package adeady.benchmarks.user

import grails.persistence.Entity
import groovy.transform.EqualsAndHashCode
import org.springframework.hateoas.Link
import org.springframework.hateoas.Resource

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo

@Entity
@EqualsAndHashCode
class User {

    String nickname

    Resource<UserRepresentation> getResource() {
        Link selfLink = linkTo(UserController).slash(id).withSelfRel();

        def representation = new UserRepresentation(name:nickname)

        new Resource<UserRepresentation>(representation, selfLink)
    }
}