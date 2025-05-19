package bg.sofia.uni.fmi.mjt.uno.database.account;

public record Account(String username, String password) {

    public Account(String username, String password) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Account username cannot be null or blank!");
        }
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Account password cannot be null or blank!");
        }

        this.username = username;
        this.password = password;
    }

}
