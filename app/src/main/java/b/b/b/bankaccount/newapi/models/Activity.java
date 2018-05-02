package b.b.b.bankaccount.newapi.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ohefny on 5/1/18.
 */

public class Activity {
    public String getActivityTypeString() {
        switch (activityType){
            case ACTIVITY_TYPE.WITHDRAW:
                return "Withdraw";
            case ACTIVITY_TYPE.DEPOSIT:
                return "Deposit";
            case ACTIVITY_TYPE.TRANSFER_TO:
                return "Transfer";
            case ACTIVITY_TYPE.TRANSFER_FROM:
                return "Recieve";
            case ACTIVITY_TYPE.INQUIRY:
                return "Inquiry";

        }
        return "UnKnown Activity";
    }

    public interface ACTIVITY_TYPE{
        int WITHDRAW=1,DEPOSIT=2,TRANSFER_TO=3,TRANSFER_FROM=4,INQUIRY=5;
    }
    private int activityType;
    private String accountId;
    private int balanceBefore;
    private int balanceAfter;
    @SerializedName("created_at")
    private String createdAt;
    public int getActivityType() {
        return activityType;
    }

    public void setActivityType(int activityType) {
        this.activityType = activityType;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public int getBalanceBefore() {
        return balanceBefore;
    }

    public void setBalanceBefore(int balanceBefore) {
        this.balanceBefore = balanceBefore;
    }

    public int getBalanceAfter() {
        return balanceAfter;
    }

    public void setBalanceAfter(int balanceAfter) {
        this.balanceAfter = balanceAfter;
    }
    /*
    "activityType":Number,
   "account":{type: mongoose.Schema.Types.ObjectId,ref:'Account2'},
   "balanceBefore":Number,
   "balanceAfter":Number,
   "targetAccount":{type: mongoose.Schema.Types.ObjectId,ref:'Account2'}
    */

    public String getCreatedAt() {
        if(createdAt!=null){
            return createdAt.replace('T',' ').substring(0,createdAt.length()-4);
        }
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
