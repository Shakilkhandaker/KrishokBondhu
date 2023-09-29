package com.example.krishokbondhu;

public class ProductDetails {
    private String product_id;
    private String product_name;
    private String product_price;
    private String product_quantity;
    private String product_totalprice;
    private String time_period;
    public ProductDetails(String product_id, String product_name, String product_price, String product_quantity, String product_totalprice, String time_period) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.product_price = product_price;
        this.product_quantity = product_quantity;
        this.product_totalprice = product_totalprice;
        this.time_period = time_period;
    }
    public String getProduct_id() {
        return product_id;
    }
    public String getProduct_name() {return product_name;}
    public String getProduct_price() {
        return product_price;
    }
    public String getProduct_quantity() {return product_quantity;}
    public String getProduct_totalprice() {
        return product_totalprice;
    }
    public String getTime_period(){return time_period;}
}
