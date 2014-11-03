package edu.oregonstate.mist.tasx

class UserController {

    static defaultAction = ""

    Map register() {
        User newUser = new User((String)params.name, (String)params.email)

        newUser.setPassword((String)params.pass1, (String)params.pass2)

        if (newUser.validate()) {
            redirect(action: "account")
        }

        return [ user: newUser ]
    }
}
