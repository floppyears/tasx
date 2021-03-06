package edu.oregonstate.mist.tasx

import java.security.MessageDigest

class User {

    static constraints = {
        name([blank: false,
              unique: true])
        email([email: true,
               blank: false])
        passwordTemp([minSize: 10,         // password has at least 10 characters
                      matches: ".*\\d.*",  // and at least 1 digit
                      blank: false])
        passwordConf([validator: { c, o ->
                                   c.equals(o.passwordTemp) ?
                                   true :
                                   'tasx.user.register.error.password-mismatch' } ])
        passwordHash([nullable: true])
    }

    static mapping = {
        table "TasxUser"
    }

    static hasMany = [tasks: Task]

    static transients = ["passwordTemp", "passwordConf"]

    void beforeInsert() {
        beforeUpdate()
    }

    void beforeUpdate() {
        passwordHash = hash(passwordTemp)
        passwordTemp = null
        passwordConf = null
    }

    String name
    String email
    String passwordTemp
    String passwordConf
    String passwordHash

    public void setPassword(String password, String confirmPassword) {
        passwordHash = null
        passwordTemp = password
        passwordConf = confirmPassword
    }

    /**
     * Test whether the input String equals the stored password.
     *
     * @param password any String
     * @return         true if input String is the password
     */
    public Boolean passwordEquals(String password) {
        if (passwordTemp == null) {
            return (passwordHash == hash(password))
        } else {
            return (passwordTemp == password)
        }
    }

    /**
     * Given a message String, compute SHA256 digest String.
     *
     * @param message any String
     * @return        the digest String
     */
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

    public Map serialize() {
        return [ id:    id,
                 name:  name,
                 email: email,
                 tasks: tasks ]
    }

    private static String hash(String clearText) {
        return sha256sum(clearText)
    }
}