package edu.oregonstate.mist

import java.security.MessageDigest

class User {

    static constraints = {
        // password has at least 10 characters and at least 1 digit
        passwordTemp minSize: 10, matches: ".*[0-9].*"

        // after validation, hash and store password and overwrite temporary value
        passwordHash = hash(passwordTemp)
        passwordTemp = null
    }

    static hasMany = [tasks: Task]

    private String userName
    String email
    String passwordTemp
    private String passwordHash

    public User(String name, String email, String password) {
        userName = name
        this.email = email
        this.setPassword(password)
    }

    public String getName() {
        return userName
    }

    public void setPassword(String password) {
        passwordTemp = password
    }

    public Boolean passwordEquals(String password) {
        return (hash(password) == passwordHash)
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
        for (int padding = SHA256SUM_LENGTH - digestString.length(); padding > 0; --padding) {
            digestString = "0" + digestString // prepend leading zeros to checksum
        }

        return digestString
    }

    private static String hash(String clearText) {
        return sha256sum(clearText)
    }
}