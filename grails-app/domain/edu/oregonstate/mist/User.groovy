package edu.oregonstate.mist;

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

    /* TODO: use a real hash function */
    private String hash(String clearText) {
        return clearText;
    }
}