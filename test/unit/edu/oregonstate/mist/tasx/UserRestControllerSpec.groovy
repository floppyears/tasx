package edu.oregonstate.mist.tasx

import grails.test.mixin.TestFor
import spock.lang.Specification
import grails.test.mixin.Mock

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(UserRestController)
@Mock([User])
class UserRestControllerSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "GET a list of users as JSON"() {
        given: "some users"
            initializeUsers()

        when: "invoke index action"
            controller.index()

        then: "get all users serializied as JSON"
            response.json.toString().equals('[{"id":1,"email":"john@doe.com","name":"John","tasks":null},{"id":2,"email":"jane@doe.com","name":"Jane","tasks":null}]')
    }

    void "GET a user as JSON"() {
        given: "some users"
            initializeUsers()

        when: "invoke index action"
             controller.show(User.findById(2))

        then: "get user serialized as JSON"
            response.json.toString().equals('{"id":2,"email":"jane@doe.com","name":"Jane","tasks":null}')
    }

    private initializeUsers() {
        User a = new User([name: "John", email: "john@doe.com"])
        User b = new User([name: "Jane", email: "jane@doe.com"])

        a.setPassword("password123", "password123")
        b.setPassword("password321", "password321")

        a.save()
        b.save()
    }
}
