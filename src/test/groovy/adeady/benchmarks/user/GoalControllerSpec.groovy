package adeady.benchmarks.user

import adeady.benchmarks.Application
import adeady.benchmarks.goal.Goal
import adeady.benchmarks.goal.GoalController
import adeady.benchmarks.lift.Lift
import groovy.json.JsonSlurper
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

import static org.springframework.http.HttpStatus.*
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup

@Transactional
@ContextConfiguration(loader = SpringApplicationContextLoader, classes = Application)
class GoalControllerSpec extends Specification {

    GoalController controller
    MockMvc mockMvc

    String baseUrl

    def setup() {
        controller = new GoalController()
        mockMvc = standaloneSetup(controller).build()

        baseUrl = "/users"

    }

    def "create a goal"() {
        def user = new User(nickname: System.currentTimeMillis()).save(failOnError: true)

        when:
        def json = /{"username":"$user.nickname", "lift":"$Lift.BENCH", "weight":"300"}/
        def response = mockMvc.perform(post("/goals")
                .content(json)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json"))
                .andReturn().response

        then:
        response.status == CREATED.value()

        when:
        def content = new JsonSlurper().parseText(response.contentAsString)

        then:
        content
        content.lift
        content.weight
        content.links

        cleanup:
        user.delete()
        Goal.findByUser(user).delete()
    }

    def "creating a goal with invalid user returns error code"() {
        when:
        def json = /{"username":"nickname", "lift":"$Lift.BENCH", "weight":"300"}/
        def response = mockMvc.perform(post("/goals")
                .content(json)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json"))
                .andReturn().response

        then:
        response.status == BAD_REQUEST.value()
    }

    def "list all goals"() {
        def user = new User(nickname: System.currentTimeMillis()).save(failOnError: true)
        def goal1 = new Goal(user: user, lift: Lift.PRESS, weight:150).save(failOnError: true)
        def goal2 = new Goal(user: user, lift: Lift.BENCH, weight:150).save(failOnError: true)

        when:
        def response = mockMvc.perform(get("/goals")
                .header("Content-Type", "application/json")
                .header("Accept", "application/json"))
                .andReturn().response

        then:
        response.status == OK.value()

        when:
        def content = new JsonSlurper().parseText(response.contentAsString)

        then:
        content.size() >= 2
        content*.lift.contains(Lift.PRESS.toString())
        content*.links

        when:
        def selfLink = content[0].links.find { it.rel == "self"}
        response = mockMvc.perform(get(selfLink.href)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json"))
                .andReturn().response

        then:
        response.status == OK.value()

        when:
        content = new JsonSlurper().parseText(response.contentAsString)

        then:
        content.lift

        cleanup:
        user.delete()
        Goal.deleteAll(goal1, goal2)
    }


}
