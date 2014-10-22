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

    void "task is scheduled"() {
        given:
            final Date from = new Date()
            final Date to = from + 1
            final Interval anInterval = new Interval(from, to)
            Task theTask

        when: "task instantiated without setting schedule"
            theTask = new Task()
        then:
            theTask.isScheduled() == false
            theTask.isScheduled(anInterval) == false

        when: "task instantiated and schedule set"
            theTask = new Task()
            theTask.setSchedule(anInterval)
        then:
            theTask.isScheduled() == true
            theTask.isScheduled(anInterval) == true

        when: "task instantiated and schedule unset after being set"
            theTask = new Task()
            theTask.setSchedule(anInterval)
            theTask.setUnscheduled()
        then:
            theTask.isScheduled() == false
            theTask.isScheduled(anInterval) == false

        when: "task instantiated with null interval"
            theTask = new Task()
            theTask.setSchedule(new Interval())
        then:
            theTask.isScheduled() == false
            theTask.isScheduled(anInterval) == false
    }
}
