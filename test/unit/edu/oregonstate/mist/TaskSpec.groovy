package edu.oregonstate.mist

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Task)
class TaskSpec extends Specification {

    final String DESCRIPTION = "description"
    final Date FROM = new Date()
    final Date TO = FROM + 1
    final Interval AN_INTERVAL = new Interval(FROM, TO)
    final Integer PRIORITY = 5
    Task testTask
    Task testSubTask

    def setup() {
    }

    def cleanup() {
    }

    void "initialize a task with a string and default values"() {
        when:
            testTask = new Task(DESCRIPTION)
        then:
            testTask.getDescription() == DESCRIPTION
            testTask.isScheduled() == false
            testTask.getPriority() == 0
            testTask.isIncomplete() == true
    }

    void "set and get description using mock task"() {
        given:
            Task mockTask = Mock()
            mockTask.getDescription() >> DESCRIPTION

        when:
            mockTask.setDescription(DESCRIPTION)
        then:
            mockTask.getDescription() == DESCRIPTION
    }

    void "set and get task schedule"() {
        given:
            testTask = new Task()

        when:
            testTask.setSchedule(AN_INTERVAL)
        then:
            testTask.getSchedule() == AN_INTERVAL
    }

    void "task is scheduled"() {
        when: "task instantiated without setting schedule"
            testTask = new Task()
        then:
            testTask.isScheduled() == false
            testTask.isScheduled(AN_INTERVAL) == false

        when: "task instantiated and schedule set"
            testTask = new Task()
            testTask.setSchedule(AN_INTERVAL)
        then:
            testTask.isScheduled() == true
            testTask.isScheduled(AN_INTERVAL) == true

        when: "task instantiated and schedule unset after being set"
            testTask = new Task()
            testTask.setSchedule(AN_INTERVAL)
            testTask.setUnscheduled()
        then:
            testTask.isScheduled() == false
            testTask.isScheduled(AN_INTERVAL) == false

        when: "task instantiated with null interval"
            testTask = new Task()
            testTask.setSchedule(new Interval())
        then:
            testTask.isScheduled() == false
            testTask.isScheduled(AN_INTERVAL) == false
    }

    void "task is scheduled if subTask is scheduled"() {
        given:
            testTask = new Task()
            testSubTask = new Task()
            testSubTask.setSchedule(AN_INTERVAL)

        when:
            testTask.addToSubTasks(testSubTask)
        then:
            testTask.isScheduled() == true
            testTask.isScheduled(AN_INTERVAL) == true
    }

    void "set and get task priority"() {
        given:
            testTask = new Task()

        when:
            testTask.setPriority(PRIORITY)
        then:
            testTask.getPriority() == PRIORITY

        when:
            testTask.setPriority(PRIORITY)
            testTask.incrementPriority()
        then:
            testTask.getPriority() == PRIORITY + 1

        when:
            testTask.setPriority(PRIORITY)
            testTask.decrementPriority()
        then:
            testTask.getPriority() == PRIORITY - 1
    }

    void "set and get task status"() {
        given:
            testTask = new Task()

        when:
            testTask.setStatusIncomplete()
        then:
            testTask.isIncomplete() == true
            testTask.isComplete() == false
            testTask.isCancelled() == false
            testTask.isDeleted() == false

        when:
            testTask.setStatusCompleted()
        then:
            testTask.isIncomplete() == false
            testTask.isComplete() == true
            testTask.isCancelled() == false
            testTask.isDeleted() == false

        when:
            testTask.setStatusCancelled()
        then:
            testTask.isIncomplete() == false
            testTask.isComplete() == false
            testTask.isCancelled() == true
            testTask.isDeleted() == false

        when:
            testTask.setStatusDeleted()
        then:
            testTask.isIncomplete() == false
            testTask.isComplete() == false
            testTask.isCancelled() == false
            testTask.isDeleted() == true
    }

    void "add subtasks"() {
        given:
            testTask = new Task()
            testSubTask = new Task()

        when:
            testTask.addToSubTasks(testSubTask)
        then:
            testTask.hasSubTasks()
    }
}
