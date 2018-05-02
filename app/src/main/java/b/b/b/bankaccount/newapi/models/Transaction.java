package b.b.b.bankaccount.newapi.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ohefny on 5/1/18.
 */

public class Transaction {
    @SerializedName("id")
    private String id;
    @SerializedName("value")
    private int value;
    public Transaction(String id,int value) {
        this.id = id;
        this.value = value;
    }
    public String getID() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }


    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
