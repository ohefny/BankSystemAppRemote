package b.b.b.bankaccount.newapi.models;

/**
 * Created by ohefny on 5/1/18.
 */

public class Register {

    private int accountNumber;
    private String password;
    private String userName;
    private String email;
    private String phone;

    public Register(int accountNumber, String password, String userName,String email,String phone) {
        this.accountNumber = accountNumber;
        this.password = password;
        this.userName = userName;
        this.email=email;
        this.phone=phone;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
