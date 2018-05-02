package b.b.b.bankaccount.newapi.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ohefny on 5/1/18.
 */

public class Transfer {
    @SerializedName("from")
    private String fromID;
    @SerializedName("to")
    private int toID;
    @SerializedName("value")
    private int value;

    public Transfer(String fromID, int toID, int value) {
        this.fromID = fromID;
        this.toID = toID;
        this.value = value;
    }

    public String getFromID() {
        return fromID;
    }

    public void setFromID(String fromID) {
        this.fromID = fromID;
    }

    public int getToID() {
        return toID;
    }

    public void setToID(int toID) {
        this.toID = toID;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
