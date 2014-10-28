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
            final String DESCRIPTION = "description"
            Task mockTask = Mock()
            mockTask.getDescription() >> DESCRIPTION

        when:
            mockTask.setDescription(DESCRIPTION)
        then:
            mockTask.getDescription() == DESCRIPTION
    }

    void "set and get task schedule"() {
        given:
            final Date FROM = new Date()
            final Date TO = FROM + 1
            final Interval AN_INTERVAL = new Interval(FROM, TO)
            Task theTask = new Task()

        when:
            theTask.setSchedule(AN_INTERVAL)
        then:
            theTask.getSchedule() == AN_INTERVAL
    }

    void "task is scheduled"() {
        given:
            final Date FROM = new Date()
            final Date TO = FROM + 1
            final Interval AN_INTERVAL = new Interval(FROM, TO)
            Task theTask

        when: "task instantiated without setting schedule"
            theTask = new Task()
        then:
            theTask.isScheduled() == false
            theTask.isScheduled(AN_INTERVAL) == false

        when: "task instantiated and schedule set"
            theTask = new Task()
            theTask.setSchedule(AN_INTERVAL)
        then:
            theTask.isScheduled() == true
            theTask.isScheduled(AN_INTERVAL) == true

        when: "task instantiated and schedule unset after being set"
            theTask = new Task()
            theTask.setSchedule(AN_INTERVAL)
            theTask.setUnscheduled()
        then:
            theTask.isScheduled() == false
            theTask.isScheduled(AN_INTERVAL) == false

        when: "task instantiated with null interval"
            theTask = new Task()
            theTask.setSchedule(new Interval())
        then:
            theTask.isScheduled() == false
            theTask.isScheduled(AN_INTERVAL) == false
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

    void "set and get task status"() {
        given:
            Task theTask = new Task()

        when:
            theTask.setStatusIncomplete()
        then:
            theTask.isIncomplete() == true
            theTask.isComplete() == false
            theTask.isCancelled() == false
            theTask.isDeleted() == false

        when:
            theTask.setStatusCompleted()
        then:
            theTask.isIncomplete() == false
            theTask.isComplete() == true
            theTask.isCancelled() == false
            theTask.isDeleted() == false

        when:
            theTask.setStatusCancelled()
        then:
            theTask.isIncomplete() == false
            theTask.isComplete() == false
            theTask.isCancelled() == true
            theTask.isDeleted() == false

        when:
            theTask.setStatusDeleted()
        then:
            theTask.isIncomplete() == false
            theTask.isComplete() == false
            theTask.isCancelled() == false
            theTask.isDeleted() == true
    }
}
