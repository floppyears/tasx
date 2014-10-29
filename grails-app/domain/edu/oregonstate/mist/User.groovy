package edu.oregonstate.mist

import java.security.MessageDigest

class User {

    static constraints = {
        email(email:true)
        passwordTemp([minSize: 10,         // password has at least 10 characters
                      matches: ".*\\d.*"]) // and at least 1 digit
    }

    static hasMany = [tasks: Task]

    static transients = ["passwordTemp"]

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
    private String passwordHash

    public User(String name, String email, String password) {
        userName = name
        this.email = email
        this.setPassword(password)
    }

    public String getName() { // private userName can only be accessed, not modified
        return userName
    }

    public void setPassword(String password) {
        passwordHash = null
        passwordTemp = password
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