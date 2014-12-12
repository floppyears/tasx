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
            out << "<nav class='navbar navbar-default' role='navigation'>\n"
            out << "\t<div class='container-fluid'>\n"

            out << "\t\t<div class='navbar-header'>"
            out << "<a href='/tasx/' class='navbar-brand'>"
            out << g.message(code:"tasx.index.title")
            out << "</a>"
            out << "</div>\n"

            out << "\t\t<ul class='nav navbar-nav'>\n"

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

            out << "\t\t</ul>\n"
            out << "\t</div>\n"
            out << "</nav>"
    }

    private outputLinks(List urlsAndMessages) {
        urlsAndMessages.collect(outputLink)
    }

    private Closure outputLink = {
        Map link ->
            out << "\t\t\t<li role='presentation'>"
            out << "<a href='"
            out << link.url
            out << "'>"
            out << g.message(code:link.message)
            out << "</a>"
            out << "</li>\n"
    }
}
