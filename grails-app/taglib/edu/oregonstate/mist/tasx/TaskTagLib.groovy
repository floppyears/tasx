package edu.oregonstate.mist.tasx

class TaskTagLib {
    static defaultEncodeAs = [taglib:'text']
    //static encodeAsForTags = [tagName: [taglib:'html'], otherTagName: [taglib:'none']]

    static final namespace = "tasx"

    def display = {
        Map attributes, Closure body ->
            out << "<a href='/tasx/task/details/"
            out << attributes.task.getId()
            out << "' class='task "
            out << statusToCSSClass(attributes.task.status)
            out << "'>"
            out << attributes.task.descriptionSummary()
            out << "</a>"
    }

    private static String statusToCSSClass(status) {
        switch(status) {
            case Task.Status.TODO:
                "todo"
                break
            case Task.Status.DONE:
                "done"
                break
            case Task.Status.CANCELLED:
                "canc"
                break
            case Task.Status.DELETED:
            default:
                "dele"
                break
        }
    }

    def status = {
        Map attributes, Closure body ->
            out << "<label for='status'>"
            out << g.message(code:"tasx.task.details.status")
            out << "</label>"
            out << "\n"
            out << "\t\t"
            out << "<select name='status' id='status'>"
            out << "\n"
            ["todo", "done", "cancelled", "deleted"].collect({
                status ->
                    out << "\t\t\t"
                    out << "<option value='"
                    out << status
                    out << "'"
                    out << selectStatus(attributes.task, status)
                    out << ">"
                    out << g.message(code:"tasx.task.details." + status)
                    out << "</option>"
                    out << "\n"
            })
            out << "\t\t"
            out << "</select>"
    }

    private static String selectStatus(task, status) {
        if ((status.equals("todo")      && task.isIncomplete()) ||
            (status.equals("done"))     && task.isComplete()    ||
            (status.equals("cancelled") && task.isCancelled())  ||
            (status.equals("deleted")   && task.isDeleted())) {
            return " selected=selected"
        }
    }
}
