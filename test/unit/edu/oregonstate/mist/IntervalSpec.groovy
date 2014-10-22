package edu.oregonstate.mist

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Interval)
class IntervalSpec extends Specification {

    Date before = new Date(),
         from   = before + 1,
         at     = before + 2,
         to     = before + 3,
         after  = before + 4

    def setup() {
    }

    def cleanup() {
    }

    void "interval contains date"() {
        given:
        Interval interval

        when: "interval does not contain null"
            interval = new Interval()
        then:
            interval.contains(null) == false

        when: "empty interval contains instant"
            interval = new Interval(at, at)
        then:
            interval.contains(before) == false
            interval.contains(at)     == true
            interval.contains(after)  == false

        when: "open interval contains instant"
            interval = new Interval(null, to)
        then:
            interval.contains(before) == true
            interval.contains(to)     == true
            interval.contains(after)  == false

        when: "open interval contains instant"
            interval = new Interval(from, null)
        then:
            interval.contains(before) == false
            interval.contains(from)   == true
            interval.contains(after)  == true

        when: "closed interval contains instant"
            interval = new Interval(from, to)
        then:
            interval.contains(before) == false
            interval.contains(from)   == true
            interval.contains(at)     == true
            interval.contains(to)     == true
            interval.contains(after)  == false
    }
    }
}
