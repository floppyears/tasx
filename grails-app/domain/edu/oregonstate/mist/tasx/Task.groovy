package edu.oregonstate.mist.tasx

class Task {

    static constraints = {}

    static hasMany = [subTasks: Task]
    static belongsTo = [user: User]

    private static enum Status { TODO, DONE, CANCELLED, DELETED }

    String description
    Interval schedule
    Integer priority
    private Status status

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

    public void setStatusIncomplete() {
        status = Status.TODO
    }

    public void setStatusCompleted() {
        status = Status.DONE
    }

    public void setStatusCancelled() {
        status = Status.CANCELLED
    }

    public void setStatusDeleted() {
        status = Status.DELETED
    }

    public Boolean isIncomplete() {
        return status == Status.TODO
    }

    public Boolean isComplete() {
        return status == Status.DONE
    }

    public Boolean isCancelled() {
        return status == Status.CANCELLED
    }

    public Boolean isDeleted() {
        return status == Status.DELETED
    }

    public void setUnscheduled() {
        schedule.setNull()
    }

    public Boolean isScheduled() {
        Boolean thisTaskIsScheduled = !schedule.isNull()
        Boolean aSubTaskIsScheduled = subTasks.find({ it.isScheduled() })

        return thisTaskIsScheduled || aSubTaskIsScheduled
    }

    public Boolean isScheduled(Interval when) {
        Boolean    taskScheduleOverlaps = schedule.overlaps(when)
        Boolean subTaskScheduleOverlaps = subTasks.find({ it.getSchedule().overlaps(when) })

        return isScheduled() && (taskScheduleOverlaps || subTaskScheduleOverlaps)
    }

    public Boolean hasSubTasks() {
        return subTasks && !subTasks.isEmpty()
    }
}
