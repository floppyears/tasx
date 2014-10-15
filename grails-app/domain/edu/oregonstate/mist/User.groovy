package edu.oregonstate.mist;

import java.security.MessageDigest;

class User {

    static constraints = {
    }

    static hasMany = [tasks = Task];

    protected String userName;
    protected String emailAddress;
    protected String passwordHash;

    public User(String name, String email, String password) {
        userName = name;
        emailAddress = email;
        passwordHash = hash(password);
    }

    public String getName() {
        return userName;
    }

    public String getEmail() {
        return emailAddress;
    }

    public void setEmail(String email) {
        emailAddress = email;
    }

    public void setPassword(String password) {
        passwordHash = hash(password);
    }

    public Boolean passwordEquals(String password) {
        return (hash(password) == passwordHash);
    }

    private String hash(String clearText) {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(clearText.getBytes());
        return new BigInteger(1, md.digest()).toString(16); // convert byte[] to BigInteger to hex String
    }
}