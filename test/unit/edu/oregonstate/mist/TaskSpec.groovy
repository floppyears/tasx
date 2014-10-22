package edu.oregonstate.mist

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Task)
class TaskSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "initialize a task with a string and default values"() {
        given:
            String description = "this is a new task"

        when:
            Task t = new Task(description)
        then:
            t.getDescription() == description
            t.isScheduled() == false
            t.getPriority() == 0
            t.isIncomplete() == true
    }
    }
}
