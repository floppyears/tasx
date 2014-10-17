package edu.oregonstate.mist

class Task {

    static constraints = {}

    static hasMany = [prereqs: Task]
    static belongsTo = [user: User]

    private static enum Status { TODO, DONE, CANCELLED, DELETED }

    String description
    private Interval schedule
    Integer priority
    private Status status

    public Task(String description) {
        this.description = description
        schedule = null
        priority = 0
        status = Status.TODO
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
}
