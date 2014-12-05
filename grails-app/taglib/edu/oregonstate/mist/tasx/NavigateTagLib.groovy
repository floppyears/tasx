package edu.oregonstate.mist.tasx

class NavigateTagLib {
    static defaultEncodeAs = [taglib:'text']
    //static encodeAsForTags = [tagName: [taglib:'html'], otherTagName: [taglib:'none']]

    static final namespace = "tasx"

    def navigate = {
        Map attributes, Closure body ->
            out << "<nav>"
            out << "\n"
            if (attributes.authenticated) {
                [[ url:     "/tasx/user",
                   message: "tasx.user.account.title" ],
                 [ url:     "/tasx/user/logout",
                   message: "tasx.user.logout.title" ],
                 [ url:     "/tasx/task/list",
                   message: "tasx.task.list.title" ],
                 [ url:     "/tasx/task/details",
                   message: "tasx.task.list.new" ]].collect(outputLink)
            } else {
                [[ url:     "/tasx/user/login",
                   message: "tasx.user.login.title" ],
                 [ url:     "/tasx/user/register",
                   message: "tasx.user.register.title" ]].collect(outputLink)
            }
            out << "</nav>"
    }

    private Closure outputLink = {
        Map link ->
            out << "\t"
            out << "<a href='"
            out << link.url
            out << "'>"
            out << g.message(code:link.message)
            out << "</a>"
            out << "\n"
    }
}
