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

    void "set and get description using mock task"() {
        given:
            final String description = "description"
            Task mockTask = Mock()
            mockTask.getDescription() >> description

        when:
            mockTask.setDescription(description)
        then:
            mockTask.getDescription() == description
    }

    void "set and get task schedule"() {
        given:
            final Date from = new Date()
            final Date to = from + 1
            final Interval anInterval = new Interval(from, to)
            Task theTask = new Task()

        when:
            theTask.setSchedule(anInterval)
        then:
            theTask.getSchedule() == anInterval
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

    void "set and get task priority"() {
        given:
            final Integer PRIORITY = 5
            Task theTask = new Task()

        when:
            theTask.setPriority(PRIORITY)
        then:
            theTask.getPriority() == PRIORITY

        when:
            theTask.setPriority(PRIORITY)
            theTask.incrementPriority()
        then:
            theTask.getPriority() == PRIORITY + 1

        when:
            theTask.setPriority(PRIORITY)
            theTask.decrementPriority()
        then:
            theTask.getPriority() == PRIORITY - 1
    }
}
