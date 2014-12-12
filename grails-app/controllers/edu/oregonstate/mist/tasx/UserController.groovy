package edu.oregonstate.mist.tasx

class UserController {

    static defaultAction = "account"

    static scaffold = false

    Map register() {
        User user = null

        if (params.submitting) {
            user = new User([name: params.name, email: params.email])
            user.setPassword(params.pass1, params.pass2)

            if (user.save()) {
                session["user"] = user
                redirect(action: "account")
            }
        }

        params.submitting = false

        return [ user: user, params: params ]
    }

    Map login() {
        User user = null

        if (params.name && params.password) {
            user = User.findByName(params.name)

            if (user && user.passwordEquals(params.password)) {
                session["user"] = user
                redirect(action: "account")
            }
        }

        return [user: user]
    }

    Map logout() {
        session.invalidate()
        redirect([action:"login"])
    }

    Map account() {
        if (!session["user"]) {
            redirect(action: "login")
        }

        return [user: session["user"]]
    }
}
