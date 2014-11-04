package edu.oregonstate.mist.tasx

class UserController {

    static defaultAction = ""

    Map register() {
        User newUser = new User(params.name, params.email)

        newUser.setPassword(params.pass1, params.pass2)

        if (newUser.validate()) {
            redirect(action: "account")
        }

        return [ user: newUser ]
    }
}
