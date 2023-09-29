package com.example.krishokbondhu;

public class HistoryDetails {
    private String date_history;
    private String info_history;
    private String payment_value;
    private String time_value;
    private String status;

    public HistoryDetails(String date_history, String info_history,String payment_value,String time_value,String status) {
        this.date_history = date_history;
        this.info_history = info_history;
        this.payment_value = payment_value;
        this.time_value = time_value;
        this.status = status;
    }
    public String getDate_history() {
        return date_history;
    }
    public String getInfo_history() {return info_history;}
    public String getPayment_value() {
        return payment_value;
    }
    public String getTime_value() {return time_value;}
    public String getStatus() {return status;}
}
