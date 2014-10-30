package edu.oregonstate.mist.tasx

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

    /**
     * Get the userName (which cannot be set after instantiation).
     *
     * @return userName
     */
    public String getName() {
        return userName
    }

    /**
     * Set the password.
     *
     * The plaintext password will be stored in a temporary variable until the
     * User is inserted or updated, at which point it will be hashed and saved.
     *
     * @param password
     */
    public void setPassword(String password) {
        passwordHash = null
        passwordTemp = password
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

    private static String hash(String clearText) {
        return sha256sum(clearText)
    }
}