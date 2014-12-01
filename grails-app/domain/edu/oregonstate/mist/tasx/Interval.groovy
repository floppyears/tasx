package edu.oregonstate.mist.tasx

class Interval {

    static constraints = {
    }

    static mapping = {
        table "TasxInterval"
    }

    Date fromDate
    Date toDate

    public Interval(Date from, Date to) {
        setInterval(from, to)
    }

    /**
     * Set the Interval to the instant defined by the input argument.
     *
     * Time: -----|----->
     *
     * This method is not named "setAt" for compatibility with GORM.
     *
     * @param at any Date
     */
    public void doSetAt(Date at) {
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
        fromDate = from
        toDate = to
    }

    /**
     * Return the date of the instant defined by doSetAt(Date).
     *
     * This method is not named "getAt" for compatibility with GORM.
     *
     * @return either the lower bound or the upper bound
     */
    public Date doGetAt() {
        return toDate
    }

    /**
     * Is this an open or closed Interval?
     *
     * @return true if endpoints are not equal
     */
    public Boolean isInterval() {
        return fromDate != toDate
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
        return !fromDate && !toDate
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
            return instant == doGetAt()              // does instant equal it?
        } else {                                   // this is an interval.
            if (fromDate == null) {                         // if this interval is bounded above,
                return instant <= toDate                    // is instant less than or equal to upper bound?
            } else if (toDate == null) {                    // if this interval is bounded below,
                return fromDate <= instant                  // is this instant greater than or equal to lower bound?
            } else {                                    // if this interval is closed,
                return fromDate <= instant && instant <= toDate // is instant between lower and upper bounds?
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
            return this.doGetAt() == that.doGetAt() && this.doGetAt() != null
        } else if (thisIsInterval && !thatIsInterval) {
            return this.contains(that.doGetAt())
        } else if (!thisIsInterval && thatIsInterval) {
            return that.contains(this.doGetAt())
        } else { // thisIsInterval && thatIsInterval
            return this.contains(that.fromDate) || this.contains(that.toDate)
        }
    }
}
