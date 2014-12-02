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
}
