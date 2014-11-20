package edu.oregonstate.mist.tasx

class Interval {

    static constraints = {
    }

    private Date from
    private Date to

    static mapping = {
        table "TasxInterval"
    }

    public Interval(Date from, Date to) {
        setInterval(from, to)
    }

    /**
     * Set the Interval to the instant defined by the input argument.
     *
     * Time: -----|----->
     *
     * @param at any Date
     */
    public void setAt(Date at) {
        setInterval(at, at)
    }

    /**
     * Set the Interval to the open interval bounded above by the input argument.
     *
     * Time: =====|----->
     *
     * @param to the upper bound
     */
    public void setUntil(Date to) {
        setInterval(null, to)
    }

    /**
     * Set the Interval to the open interval bounded below by the input argument.
     *
     * Time: -----|=====>
     *
     * @param from the lower bound
     */
    public void setAfter(Date from) {
        setInterval(from, null)
    }

    /**
     * Set the Interval to the closed interval bounded above and below by the input arguments.
     *
     * Time: ---|===|--->
     *
     * @param from the lower bound
     * @param to   the upper bound
     */
    public void setInterval(Date from, Date to) {
        this.from = from
        this.to = to
    }

    public Date getFrom() {
        return from
    }

    public Date getTo() {
        return to
    }

    /**
     * Return the date of the instant defined by setAt(Date).
     *
     * @return either the lower bound or the upper bound
     */
    public Date getAt() {
        return to
    }

    /**
     * Is this an open or closed Interval?
     *
     * @return true if endpoints are not equal
     */
    public Boolean isInterval() {
        return from != to
    }

    public void setNull() {
        setInterval(null, null)
    }

    /**
     * Is this a null Interval?
     *
     * @return true if both endpoints equal null
     */
    public Boolean isNull() {
        return from == null && to == null
    }

    /**
     * Does this Interval contain the input Date?
     *
     * @param instant the input date
     * @return        true if date is an endpoint or is between endpoints
     */
    public Boolean contains(Date instant) {
        if (instant == null) {                     // if input is null,
            return false                           // output is false.
        } else if (!isInterval()) {                // if this is not an interval,
            return instant == getAt()              // does instant equal it?
        } else {                                   // this is an interval.
            if (from == null) {                         // if this interval is bounded above,
                return instant <= to                    // is instant less than or equal to upper bound?
            } else if (to == null) {                    // if this interval is bounded below,
                return from <= instant                  // is this instant greater than or equal to lower bound?
            } else {                                    // if this interval is closed,
                return from <= instant && instant <= to // is instant between lower and upper bounds?
            }
        }
    }

    /**
     * Does this Interval overlap the input Interval?
     *
     * @param that the Interval this is being compared to
     * @return     true if this Interval contains any date that Interval contains
     */
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
