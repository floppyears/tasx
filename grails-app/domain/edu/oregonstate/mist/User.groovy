package edu.oregonstate.mist

import java.security.MessageDigest

class User {

    static constraints = {
    }

    static hasMany = [tasks: Task]

    private String userName
    String email
    private String passwordHash

    public User(String name, String email, String password) {
        userName = name
        this.email = email
        passwordHash = hash(password)
    }

    public String getName() { // private userName can only be accessed, not modified
        return userName
    }

    public void setPassword(String password) {
        passwordHash = hash(password)
    }

    public Boolean passwordEquals(String password) {
        return (hash(password) == passwordHash)
    }

    public static String sha256sum(String message) {
        MessageDigest md = MessageDigest.getInstance("SHA-256")
        md.update(message.getBytes())

        final int HEX_RADIX = 16
        byte[]     digestByteArray = md.digest()
        BigInteger digestBigInteger = new BigInteger(1, digestByteArray)
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