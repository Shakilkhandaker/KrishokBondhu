package com.example.krishokbondhu;

public class Payment {
    private String info_history;
    private String payment_value;
    private String time_value;
    private String farmer_no;
    private String status;
    private String transaction_id;
    public Payment(String info_history, String payment_value, String time_value,String farmer_no,String status, String transaction_id) {
        this.info_history = info_history;
        this.payment_value = payment_value;
        this.time_value = time_value;
        this.farmer_no = farmer_no;
        this.status = status;
        this.transaction_id = transaction_id;

    }
    public String getInfo_history() {return info_history;}
    public String getPayment_value() {return payment_value;}
    public String getTime_value() {return time_value;}
    public String getFarmer_no() {return farmer_no;}
    public String getStatus() {return status;}
    public String getTransaction_id () {return transaction_id;}

}

