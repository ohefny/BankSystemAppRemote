package b.b.b.bankaccount.newapi;


import b.b.b.bankaccount.newapi.models.Account;

/**
 * Created by ohefny on 5/1/18.
 */

public class User {
    private static Account sAccount;
    public static  Account getAccount(){
        return sAccount;
    }
    public static void setAccount(Account account){
        sAccount=account;
    }
}
