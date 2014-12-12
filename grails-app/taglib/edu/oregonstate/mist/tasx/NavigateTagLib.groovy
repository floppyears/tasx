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
     * @param attributes    [[authenticated: boolean],
     *                       [active:        action]]
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
                outputLinks(
                        activeLink(
                                attributes.active,
                                [[ url:     "/tasx/user",
                                   action:  "user.account" ],
                                 [ url:     "/tasx/user/logout",
                                   action:  "user.logout" ],
                                 [ url:     "/tasx/task/list",
                                   action:  "task.list" ],
                                 [ url:     "/tasx/task/details",
                                   action:  "task.details" ]]))
            } else {
                outputLinks(
                        activeLink(
                                attributes.active,
                                [[ url:     "/tasx/user/login",
                                   action:  "user.login" ],
                                 [ url:     "/tasx/user/register",
                                   action:  "user.register" ]]))
            }

            out << "\t\t</ul>\n"
            out << "\t</div>\n"
            out << "</nav>"
    }

    private static List activeLink(String action, List links) {
        links.collect({
            link ->
                if (action == link.action) {
                    link.active = true
                }

                return link
        })
    }

    private outputLinks(List links) {
        links.collect(outputLink)
    }

    private Closure outputLink = {
        Map link ->
            out << "\t\t\t<li role='presentation'"
            if (link.active) {
                out << " class='active'"
            }
            out << ">"
            out << "<a href='"
            out << link.url
            out << "'>"
            out << g.message(code: "tasx." + link.action + ".title")
            out << "</a>"
            out << "</li>\n"
    }
}
