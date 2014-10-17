package edu.oregonstate.mist

class Interval {

    static constraints = {
    }

    private Calendar from
    private Calendar to

    public Interval(Calendar from, Calendar to) {
        this.setInterval(from, to)
    }

    public Interval() {
        this.setInterval(null, null)
    }

    public void setAt(Calendar at) { // time: -----|----->
        this.setInterval(at, at)
    }

    public void setUntil(Calendar to) { // time: =====|----->
        this.setInterval(null, to)
    }

    public void setAfter(Calendar from) { // time: -----|=====>
        this.setInterval(from, null)
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
        return this.getTo()
    }

    public Boolean isInterval() {
        return (this.getFrom() != this.getTo())
    }

    public Boolean isNull() {
        return ((this.getFrom() == null) && (this.getTo() == null))
    }

    public Boolean contains(Calendar instant) {
        if (instant == null) {
            return false
        } else if (!this.isInterval()) {
            return (instant == this.getAt())
        } else { // this.isInterval()
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
        } else if (!this.isInterval() && !that.isInterval()) {
            return ((this.getAt() == that.getAt()) && (this.getAt() != null))
        } else if (this.isInterval() && !that.isInterval()) {
            return this.contains(that.getAt())
        } else if (!this.isInterval() && that.isInterval()) {
            return that.contains(this.getAt())
        } else { // this.isInterval() && that.isInterval()
            return (this.contains(that.getFrom()) || this.contains(that.getTo()))
        }
    }
}
