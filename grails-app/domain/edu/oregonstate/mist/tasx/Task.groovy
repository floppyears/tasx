package edu.oregonstate.mist.tasx

class Task {

    static constraints = {}

    static hasMany = [subTasks: Task]
    static belongsTo = [user: User]

    static mapping = {
        table "TasxTask"
    }

    static enum Status { TODO, DONE, CANCELLED, DELETED }

    String description
    Interval schedule
    Integer priority
    Status status

    public Task() {
        this("")
    }

    public Task(String description) {
        this.description = description
        this.schedule = new Interval()
        priority = 0
        this.setStatusIncomplete()
    }

    public void incrementPriority() {
        ++priority
    }

    public void decrementPriority() {
        --priority
    }

    public void setStatusIncomplete() { status = Status.TODO }
    public void setStatusCompleted()  { status = Status.DONE }
    public void setStatusCancelled()  { status = Status.CANCELLED }
    public void setStatusDeleted()    { status = Status.DELETED }

    public Boolean isIncomplete() { return status == Status.TODO }
    public Boolean isComplete()   { return status == Status.DONE }
    public Boolean isCancelled()  { return status == Status.CANCELLED }
    public Boolean isDeleted()    { return status == Status.DELETED }

    public static List statusStrings = [ "todo", "done", "cancelled", "deleted" ]

    public String statusString() {
        if (isIncomplete()) {
            return statusStrings[0]
        } else if (isComplete()) {
            return statusStrings[1]
        } else if (isCancelled()) {
            return statusStrings[2]
        } else if (isDeleted()) {
            return statusStrings[3]
        }
    }

    public void setUnscheduled() {
        schedule.setNull()
    }

    /**
     * Test whether the task is scheduled.
     *
     * @return true if the task is scheduled
     */
    public Boolean isScheduled() {
        Boolean thisTaskIsScheduled = !schedule.isNull()
        Boolean aSubTaskIsScheduled = subTasks.find({ it.isScheduled() })

        return thisTaskIsScheduled || aSubTaskIsScheduled
    }

    /**
     * Test whether the task is scheduled during the input Interval.
     *
     * @param when an interval
     * @return     true if the task is scheduled during input Interval
     */
    public Boolean isScheduled(Interval when) {
        Boolean    taskScheduleOverlaps = schedule.overlaps(when)
        Boolean subTaskScheduleOverlaps = subTasks.find({ it.getSchedule().overlaps(when) })

        return isScheduled() && (taskScheduleOverlaps || subTaskScheduleOverlaps)
    }

    /**
     * Test whether this Task has subTasks.
     *
     * @return true if the set of subTasks is null or empty
     */
    public Boolean hasSubTasks() {
        return subTasks && !subTasks.isEmpty()
    }

    public String descriptionSummary() {
        String firstLine = readUntilNewline(description)

        Integer length = firstLine.length()
        Integer max = 100
        Integer index = (length < max) ? length : max
        String summary = firstLine.substring(0, index)

        return summary
    }

    private static String readUntilNewline(String string) {
        return string.split("\n")[0]
    }
}
