package b.b.b.bankaccount.newapi.models;

/**
 * Created by ohefny on 5/1/18.
 */

public class Login {


    private int accountNumber;
    private String password;

    public Login(int accountNumber, String password) {
        this.accountNumber = accountNumber;
        this.password = password;
    }
    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
