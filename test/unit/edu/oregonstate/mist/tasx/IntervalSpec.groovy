package edu.oregonstate.mist.tasx

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Interval)
class IntervalSpec extends Specification {

    final Date BEFORE = new Date(),
               FROM   = BEFORE + 1,
               AT     = BEFORE + 2,
               TO     = BEFORE + 3,
               AFTER  = BEFORE + 4

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
            interval = new Interval(AT, AT)
        then:
            interval.contains(BEFORE) == false
            interval.contains(AT)     == true
            interval.contains(AFTER)  == false

        when: "open interval contains instant"
            interval = new Interval(null, TO)
        then:
            interval.contains(BEFORE) == true
            interval.contains(TO)     == true
            interval.contains(AFTER)  == false

        when: "open interval contains instant"
            interval = new Interval(FROM, null)
        then:
            interval.contains(BEFORE) == false
            interval.contains(FROM)   == true
            interval.contains(AFTER)  == true

        when: "closed interval contains instant"
            interval = new Interval(FROM, TO)
        then:
            interval.contains(BEFORE) == false
            interval.contains(FROM)   == true
            interval.contains(AT)     == true
            interval.contains(TO)     == true
            interval.contains(AFTER)  == false
    }

    void "null intervals do not overlap"() {
        given:
            Interval foo
            Interval bar

        when: "null interval does not overlap null interval"
            foo = new Interval()
            bar = new Interval()
        then:
            foo.overlaps(bar) == false

        when: "null interval does not overlap closed interval"
            foo = new Interval()
            bar = new Interval(FROM, TO)
        then:
            foo.overlaps(bar) == false

        when: "closed interval does not overlap null interval"
            foo = new Interval(FROM, TO)
            bar = new Interval()
        then:
            foo.overlaps(bar) == false

        when: "null interval does not overlap open interval"
            foo = new Interval()
            bar = new Interval(null, TO)
        then:
            foo.overlaps(bar) == false
    }

    void "empty intervals overlap only if they are equal"() {
        given:
            Interval foo
            Interval bar

        when: "empty intervals are equal"
            foo = new Interval(AT, AT)
            bar = new Interval(AT, AT)
        then:
            foo.overlaps(bar) == true

        when: "empty intervals are unequal"
            foo = new Interval(AT, AT)
            bar = new Interval(TO, TO)
        then:
            foo.overlaps(bar) == false
    }

    void "empty intervals overlap non-empty intervals if latter contains former"() {
        given:
            Interval foo
            Interval bar

        when: "closed interval overlaps empty interval"
            foo = new Interval(FROM, TO)
            bar = new Interval(AT, AT)
        then:
            foo.overlaps(bar) == true

        when: "closed interval does not overlap empty interval"
            foo = new Interval(FROM, TO)
            bar = new Interval(AFTER, AFTER)
        then:
            foo.overlaps(bar) == false

        when: "empty interval overlaps closed interval"
            foo = new Interval(AT, AT)
            bar = new Interval(FROM, TO)
        then:
            foo.overlaps(bar) == true

        when: "empty interval does not overlap closed interval"
            foo = new Interval(AT, AT)
            bar = new Interval(TO, AFTER)
        then:
            foo.overlaps(bar) == false

        when: "empty interval overlaps open interval"
            foo = new Interval(AT, AT)
            bar = new Interval(FROM, null)
        then:
            foo.overlaps(bar) == true
    }

    void "closed intervals overlap if one contains an endpoint of the other"() {
        given:
            Interval foo
            Interval bar

        when: "closed intervals do not overlap"
            foo = new Interval(BEFORE, FROM)
            bar = new Interval(TO, AFTER)
        then:
            foo.overlaps(bar) == false

        when: "closed intervals share an endpoint"
            foo = new Interval(BEFORE, AT)
            bar = new Interval(AT, AFTER)
        then:
            foo.overlaps(bar) == true

        when: "closed intervals overlap"
            foo = new Interval(BEFORE, TO)
            bar = new Interval(FROM, AFTER)
        then:
            foo.overlaps(bar) == true

        when: "closed interval contains another"
            foo = new Interval(BEFORE, AFTER)
            bar = new Interval(FROM, TO)
        then:
            foo.overlaps(bar) == true
    }

    void "open intervals overlap if one contains an endpoint of the other"() {
        given:
            Interval foo
            Interval bar

        when: "open intervals do not overlap"
            foo = new Interval(null, FROM)
            bar = new Interval(TO, null)
        then:
            foo.overlaps(bar) == false

        when: "open intervals share an endpoint"
            foo = new Interval(null, AT)
            bar = new Interval(AT, null)
        then:
            foo.overlaps(bar) == true

        when: "open intervals overlap"
            foo = new Interval(null, TO)
            bar = new Interval(FROM, null)
        then:
            foo.overlaps(bar) == true
    }
}
