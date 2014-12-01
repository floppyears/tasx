package edu.oregonstate.mist.tasx

class TaskTagLib {
    static defaultEncodeAs = [taglib:'text']
    //static encodeAsForTags = [tagName: [taglib:'html'], otherTagName: [taglib:'none']]

    static final namespace = "tasx"

    def status = {
        attributes, body ->
            switch(attributes.status) {
                case Task.Status.TODO:
                    out << "&#x2610;"
                    break
                case Task.Status.DONE:
                    out << "&#x2611;"
                    break
                case Task.Status.CANCELLED:
                    out << "&#x2612;"
                    break
                case Task.Status.DELETED:
                default:
                    out << "*"
                    break
            }
    }
}
