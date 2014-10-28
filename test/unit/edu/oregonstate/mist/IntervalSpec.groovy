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

    void "initialize a new Interval"() {
        given:
            Interval interval

        when:
            interval = new Interval()
        then:
            interval.getFrom() == null
            interval.getTo() == null

        when:
            interval = new Interval(from, to)
        then:
            interval.getFrom() == from
            interval.getTo() == to
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
            bar = new Interval(from, to)
        then:
            foo.overlaps(bar) == false

        when: "closed interval does not overlap null interval"
            foo = new Interval(from, to)
            bar = new Interval()
        then:
            foo.overlaps(bar) == false

        when: "null interval does not overlap open interval"
            foo = new Interval()
            bar = new Interval(null, to)
        then:
            foo.overlaps(bar) == false
    }

    void "empty intervals overlap only if they are equal"() {
        given:
            Interval foo
            Interval bar

        when: "empty intervals are equal"
            foo = new Interval(at, at)
            bar = new Interval(at, at)
        then:
            foo.overlaps(bar) == true

        when: "empty intervals are unequal"
            foo = new Interval(at, at)
            bar = new Interval(to, to)
        then:
            foo.overlaps(bar) == false
    }

    void "empty intervals overlap non-empty intervals if latter contains former"() {
        given:
            Interval foo
            Interval bar

        when: "closed interval overlaps empty interval"
            foo = new Interval(from, to)
            bar = new Interval(at, at)
        then:
            foo.overlaps(bar) == true

        when: "closed interval does not overlap empty interval"
            foo = new Interval(from, to)
            bar = new Interval(after, after)
        then:
            foo.overlaps(bar) == false

        when: "empty interval overlaps closed interval"
            foo = new Interval(at, at)
            bar = new Interval(from, to)
        then:
            foo.overlaps(bar) == true

        when: "empty interval does not overlap closed interval"
            foo = new Interval(at, at)
            bar = new Interval(to, after)
        then:
            foo.overlaps(bar) == false

        when: "empty interval overlaps open interval"
            foo = new Interval(at, at)
            bar = new Interval(from, null)
        then:
            foo.overlaps(bar) == true
    }

    void "closed intervals overlap if one contains an endpoint of the other"() {
        given:
            Interval foo
            Interval bar

        when: "closed intervals do not overlap"
            foo = new Interval(before, from)
            bar = new Interval(to, after)
        then:
            foo.overlaps(bar) == false

        when: "closed intervals share an endpoint"
            foo = new Interval(before, at)
            bar = new Interval(at, after)
        then:
            foo.overlaps(bar) == true

        when: "closed intervals overlap"
            foo = new Interval(before, to)
            bar = new Interval(from, after)
        then:
            foo.overlaps(bar) == true

        when: "closed interval contains another"
            foo = new Interval(before, after)
            bar = new Interval(from, to)
        then:
            foo.overlaps(bar) == true
    }

    void "open intervals overlap if one contains an endpoint of the other"() {
        given:
            Interval foo
            Interval bar

        when: "open intervals do not overlap"
            foo = new Interval(null, from)
            bar = new Interval(to, null)
        then:
            foo.overlaps(bar) == false

        when: "open intervals share an endpoint"
            foo = new Interval(null, at)
            bar = new Interval(at, null)
        then:
            foo.overlaps(bar) == true

        when: "open intervals overlap"
            foo = new Interval(null, to)
            bar = new Interval(from, null)
        then:
            foo.overlaps(bar) == true
    }
}
