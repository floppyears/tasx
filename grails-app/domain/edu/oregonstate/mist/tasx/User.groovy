package edu.oregonstate.mist.tasx

import java.security.MessageDigest

class User {

    static constraints = {
        email(email:true)
        passwordTemp([minSize: 10,         // password has at least 10 characters
                      matches: ".*\\d.*",  // and at least 1 digit
                      blank: false])
        passwordConf([validator: { c, o ->
                                   c.equals(o.passwordTemp) ?
                                   true :
                                   'tasx.user.register.error.password-mismatch' } ])
    }

    static hasMany = [tasks: Task]

    static transients = ["passwordTemp", "passwordConf"]

    void beforeInsert() {
        beforeUpdate()
    }

    void beforeUpdate() {
        passwordHash = hash(passwordTemp)
        passwordTemp = null
    }

    private String userName
    String email
    String passwordTemp
    String passwordConf
    private String passwordHash

    public User(String name, String email) {
        setName(name)
        setEmail(email)
    }

    public User(String name, String email, String password) {
        setName(name)
        setEmail(email)
        setPassword(password)
    }

    public void setPassword(String password) {
        setPassword(password, password)
    }

    public void setPassword(String password, String confirmPassword) {
        passwordHash = null
        passwordTemp = password
        passwordConf = confirmPassword
    }

    public Boolean passwordEquals(String password) {
        if (passwordTemp == null) {
            return (passwordHash == hash(password))
        } else {
            return (passwordTemp == password)
        }
    }

    public static String sha256sum(String message) {
        MessageDigest md = MessageDigest.getInstance("SHA-256")
        md.update(message.getBytes())

        final int POSITIVE = 1
        final int HEX_RADIX = 16
        byte[]     digestByteArray = md.digest()
        BigInteger digestBigInteger = new BigInteger(POSITIVE, digestByteArray)
        String     digestString = digestBigInteger.toString(HEX_RADIX)

        final int SHA256SUM_LENGTH = 64
        digestString = String.format("%"+SHA256SUM_LENGTH+"s", digestString).replace(' ', '0') // prepend leading zeros

        return digestString
    }

    private static String hash(String clearText) {
        return sha256sum(clearText)
    }
}