package edu.oregonstate.mist.tasx

class UserController {

    static defaultAction = "index"

    static scaffold = true

    Map register() {
        User newUser = new User([name: params.name, email: params.email])

        newUser.setPassword(params.pass1, params.pass2)

        if (newUser.validate()) {
            newUser.save()
            // TODO: authenticate, then
            redirect(action: "account", params: [id: newUser.id])
        }

        return [ user: newUser ]
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
