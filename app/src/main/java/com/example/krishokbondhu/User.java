package com.example.krishokbondhu;
public class User {
    private int id;
    private String phone, password,name;
    public User(int id,String phone,String name,String password) {
        this.id = id;
        this.phone = phone;
        this.name = name;
        this.password = password;
    }
    public int getId() {
        return id;
    }
    public String getPhone() {
        return phone;
    }
    public String getPassword() {
        return password;
    }
    public String getName() {
        return name;
    }

}
