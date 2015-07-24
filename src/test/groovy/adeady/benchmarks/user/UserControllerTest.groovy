package adeady.benchmarks.user

import adeady.benchmarks.Application
import adeady.benchmarks.user.UserController
import groovy.json.JsonSlurper
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

import static org.springframework.http.HttpStatus.CREATED
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

//        cleanup:
//        def user  = User.findByNickname(name)
//        user.delete()
    }

    def "canary"() {
        expect:
        true
    }
}
