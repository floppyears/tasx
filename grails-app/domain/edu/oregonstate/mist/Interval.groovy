package edu.oregonstate.mist

class Interval {

    static constraints = {
    }

    private Calendar from
    private Calendar to

    public Interval(Calendar from, Calendar to) {
        setInterval(from, to)
    }

    public Interval() {
        setInterval(null, null)
    }

    public void setAt(Calendar at) { // time: -----|----->
        setInterval(at, at)
    }

    public void setUntil(Calendar to) { // time: =====|----->
        setInterval(null, to)
    }

    public void setAfter(Calendar from) { // time: -----|=====>
        setInterval(from, null)
    }

    public void setInterval(Calendar from, Calendar to) { // time: ---|===|--->
        this.from = from
        this.to = to
    }

    public Calendar getFrom() {
        return from
    }

    public Calendar getTo() {
        return to
    }

    public Calendar getAt() {
        return to
    }

    public Boolean isInterval() {
        return from != to
    }

    public Boolean isNull() {
        return from == null && to == null
    }

    public Boolean contains(Calendar instant) {
        if (instant == null) {                     // if input is null,
            return false                           // output is false.
        } else if (!isInterval()) {                // if this is not an interval,
            return instant == getAt()              // does instant equal it?
        } else {                                   // this is an interval.
            if (from == null) {                        // if this interval is bounded above,
                return instant <= to                   // is instant less than or equal to upper bound?
            } else if (to == null) {                   // if this interval is bounded below,
                return from < instant                  // is this instant greater than lower bound?
            } else {                                   // if this interval is closed,
                return from < instant && instant <= to // is instant between lower and upper bounds?
            }
        }
    }

    public Boolean overlaps(Interval that) {
        Boolean thisIsInterval = this.isInterval()
        Boolean thatIsInterval = that.isInterval()

        if (this.isNull() || that.isNull()) {
            return false
        } else if (!thisIsInterval && !thatIsInterval) {
            return this.getAt() == that.getAt() && this.getAt() != null
        } else if (thisIsInterval && !thatIsInterval) {
            return this.contains(that.getAt())
        } else if (!thisIsInterval && thatIsInterval) {
            return that.contains(this.getAt())
        } else { // thisIsInterval && thatIsInterval
            return this.contains(that.getFrom()) || this.contains(that.getTo())
        }
    }
}
