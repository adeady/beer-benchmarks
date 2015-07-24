package adeady.benchmarks.user

import adeady.benchmarks.Application
import groovy.json.JsonSlurper
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.OK
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup

@Transactional
@ContextConfiguration(loader = SpringApplicationContextLoader, classes = Application)
class UserControllerTest extends Specification {

    UserController controller
    MockMvc mockMvc

    String baseUrl

    def setup() {
        controller = new UserController()
        mockMvc = standaloneSetup(controller).build()

        baseUrl = "/users"

    }

    def cleanup() {
    }

    def "post new user"() {
        def name = "name" + System.currentTimeMillis()

        when:
        def json = /{"name":"$name"}/
        def response = mockMvc.perform(post("/users")
                .content(json)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json"))
                .andReturn().response

        then:
        response.status == CREATED.value()

        when:
        def content = new JsonSlurper().parseText(response.contentAsString)

        then:
        content.name == name
        content.links

        cleanup:
        def user  = User.findByNickname(name)
        user.delete()
    }

    def "list all users"() {
        User user1 = new User(nickname: "Achilles").save(failOnError: true)
        User user2 = new User(nickname: "T-Rex").save(failOnError: true)

        when:
        def response = mockMvc.perform(get("/users")
                .header("Content-Type", "application/json")
                .header("Accept", "application/json"))
                .andReturn().response

        then:
        response.status == OK.value()

        when:
        def content = new JsonSlurper().parseText(response.contentAsString)

        then:
        content.size() >= 2
        content*.name.contains("Achilles")
        content*.name.contains("T-Rex")

        cleanup:
        User.deleteAll(user1, user2)
    }


    def "get a user"() {
        User user = new User(nickname: "Achilles").save(failOnError: true)

        when:
        def response = mockMvc.perform(get("/users/$user.id")
                .header("Content-Type", "application/json")
                .header("Accept", "application/json"))
                .andReturn().response

        then:
        response.status == OK.value()

        when:
        def content = new JsonSlurper().parseText(response.contentAsString)

        then:
        content
        content.name == "Achilles"
        content.links

        cleanup:
        User.deleteAll(user)
    }
}
