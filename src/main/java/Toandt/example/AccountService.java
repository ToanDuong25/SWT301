package Toandt.example;

public class AccountService {

    public boolean registerAccount(String username, String password, String email) {
        Account acc = new Account(username, password, email);
        return acc.registerAccount();
    }

    public boolean isValidEmail(String email) {
        // dùng Account để kiểm tra (đúng tinh thần đề)
        Account acc = new Account("tmp", "Tmp@123", email);
        return acc.isValidEmail(email);
    }
}