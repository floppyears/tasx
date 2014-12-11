package edu.oregonstate.mist.tasx

class NavigateTagLib {
    static defaultEncodeAs = [taglib:'text']
    //static encodeAsForTags = [tagName: [taglib:'html'], otherTagName: [taglib:'none']]

    static final namespace = "tasx"

    /**
     * Generate navigation links depending on whether user is authenticated.
     *
     * Usage:
     * <tasx:navigate authenticated="${(Boolean)session["user"]}" />
     *
     * @param attributes    [[authenticated: trueOrFalse]]
     * @param body          empty tag body
     *
     * @return              formatted list of navigation links
     */
    def navigate = {
        Map attributes, Closure body ->
            out << "<nav>"
            out << "\n"

            outputLink([ url:     "/tasx/",
                         message: "tasx.index.title" ])

            if (attributes.authenticated) {
                outputLinks([[ url:     "/tasx/user",
                               message: "tasx.user.account.title" ],
                             [ url:     "/tasx/user/logout",
                               message: "tasx.user.logout.title" ],
                             [ url:     "/tasx/task/list",
                               message: "tasx.task.list.title" ],
                             [ url:     "/tasx/task/details",
                               message: "tasx.task.list.new" ]])
            } else {
                outputLinks([[ url:     "/tasx/user/login",
                               message: "tasx.user.login.title" ],
                             [ url:     "/tasx/user/register",
                               message: "tasx.user.register.title" ]])
            }

            out << "</nav>"
    }

    private outputLinks(List urlsAndMessages) {
        urlsAndMessages.collect(outputLink)
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
