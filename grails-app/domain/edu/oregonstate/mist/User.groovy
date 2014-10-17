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

    public String getName() {
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

        byte[]     digestByteArray = md.digest()
        BigInteger digestBigInteger = new BigInteger(1, digestByteArray)
        String     digestString = digestBigInteger.toString(16)

        return digestString
    }

    private static String hash(String clearText) {
        return sha256sum(clearText)
    }
}