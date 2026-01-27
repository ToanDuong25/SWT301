package Toandt.example;

import java.util.regex.Pattern;

public class Account {
    private String username;
    private String email;
    private String password;

    public Account(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    // getters/setters (nếu cô yêu cầu có)
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    // ===== Business rules =====
    public boolean registerAccount() {
        return isValidUsername(username)
                && isValidPassword(password)
                && isValidEmail(email);
    }

    public boolean isValidEmail(String email) {
        if (email == null) return false;
        String e = email.trim();
        if (e.isEmpty()) return false;

        // Regex email basic đủ cho lab
        String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return Pattern.matches(regex, e);
    }

    private boolean isValidUsername(String username) {
        if (username == null) return false;
        return username.trim().length() >= 3;
    }

    private boolean isValidPassword(String password) {
        if (password == null) return false;
        if (password.length() <= 6) return false;

        boolean hasUpper = false, hasLower = false, hasSpecial = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasUpper = true;
            else if (Character.isLowerCase(c)) hasLower = true;
            else if (!Character.isLetterOrDigit(c)) hasSpecial = true;
        }

        return hasUpper && hasLower && hasSpecial;
    }
}