package edu.oregonstate.mist

class Task {

    static constraints = {}

    static hasMany = [prereqs: Task]
    static belongsTo = [user: User]

    private static enum Status { TODO, DONE, CANCELLED, DELETED }

    private String description
    private Schedule schedule
    private Integer priority
    private Status status

    public Task(String description) {
        this.description = description
        schedule = null
        priority = 0
        status = Status.TODO
    }

    public void setDescription(String description) {
        this.description = description
    }

    public String getDescription() {
        return description
    }

    public void setPriority(Integer i) {
        priority = i
    }

    public Integer getPriority() {
        return priority
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
