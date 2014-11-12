package edu.oregonstate.mist.tasx

class UserController {

    static defaultAction = "index"

    static scaffold = true

    Map register() {
        User user = null

        if (params.submitting) {
            user = new User([name: params.name, email: params.email])
            user.setPassword(params.pass1, params.pass2)
            if (user.save()) {
                // TODO: authenticate, then
                redirect(action: "account", params: [id: user.id])
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
                // TODO: authenticate, then
                redirect(action: "account", params: [id: user.id])
            }
        }

        return [user: user]
    }

    Map account() {
        // TODO: if not authenticated, redirect to login or register
        return [user: User.get(params.id)]
    }
}
