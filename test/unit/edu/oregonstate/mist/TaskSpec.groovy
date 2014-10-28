package edu.oregonstate.mist

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Task)
class TaskSpec extends Specification {

    final Date FROM = new Date()
    final Date TO = FROM + 1
    final Interval AN_INTERVAL = new Interval(FROM, TO)
    final String DESCRIPTION = "description"
    final Integer PRIORITY = 5
    Task theTask
    Task subTask

    def setup() {
    }

    def cleanup() {
    }

    void "initialize a task with a string and default values"() {
        when:
            theTask = new Task(DESCRIPTION)
        then:
            theTask.getDescription() == DESCRIPTION
            theTask.isScheduled() == false
            theTask.getPriority() == 0
            theTask.isIncomplete() == true
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
            theTask = new Task()

        when:
            theTask.setSchedule(AN_INTERVAL)
        then:
            theTask.getSchedule() == AN_INTERVAL
    }

    void "task is scheduled"() {
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

    void "task is scheduled if subTask is scheduled"() {
        given:
            theTask = new Task()
            subTask = new Task()
            subTask.setSchedule(AN_INTERVAL)

        when:
            theTask.addSubTask(subTask)
        then:
            theTask.isScheduled() == true
            theTask.isScheduled(AN_INTERVAL) == true
    }

    void "set and get task priority"() {
        given:
            theTask = new Task()

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
            theTask = new Task()

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

    void "add subtasks"() {
        given:
            theTask = new Task()
            subTask = new Task()

        when:
            theTask.addSubTask(subTask)
        then:
            theTask.hasSubTasks()
    }
}
