package edu.oregonstate.mist

class Interval {

    static constraints = {
    }

    private Calendar fromInstant
    private Calendar toInstant

    public Interval(Calendar from, Calendar to) {
        fromInstant = from
        toInstant = to
    }

    public Interval() {
        this.Interval(null, null)
    }

    public void setAt(Calendar at) { // time: -----|----->
        fromInstant = at
        toInstant = at
    }

    public void setUntil(Calendar to) { // time: =====|----->
        fromInstant = null
        toInstant = to
    }

    public void setAfter(Calendar from) { // time: -----|=====>
        fromInstant = from
        toInstant = null
    }

    public void setBetween(Calendar from, Calendar to) { // time: ---|===|--->
        fromInstant = from
        toInstant = to
    }

    public Calendar getFrom() {
        return fromInstant
    }

    public Calendar getTo() {
        return toInstant
    }

    public Calendar getAt() {
        return this.getTo()
    }

    public Boolean isRange() {
        return (this.getFrom() != this.getTo())
    }

    public Boolean isNull() {
        return ((this.getFrom() == null) && (this.getTo() == null))
    }

    public Boolean contains(Calendar instant) {
        if (instant == null) {
            return false
        } else if (!this.isRange()) {
            return (instant == this.getAt())
        } else { // this.isRange()
            if (this.getFrom() == null) {
                return (this.getTo().compareTo(instant) >= 0)
            } else if (this.getTo() == null) {
                return (this.getFrom().compareTo(instant) < 0)
            } else {
                return ((this.getFrom().compareTo(instant) < 0) && (this.getTo().compareTo(instant) >= 0))
            }
        }
    }

    public Boolean overlaps(Interval that) {
        if (this.isNull() || that.isNull()) {
            return false
        } else if (!this.isRange() && !that.isRange()) {
            return ((this.getAt() == that.getAt()) && (this.getAt() != null))
        } else if (this.isRange() && !that.isRange()) {
            return this.contains(that.getAt())
        } else if (!this.isRange() && that.isRange()) {
            return that.contains(this.getAt())
        } else { // this.isRage() && that.isRange()
            return (this.contains(that.getFrom()) || this.contains(that.getTo()))
        }
    }
}
