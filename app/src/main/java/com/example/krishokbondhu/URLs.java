package com.example.krishokbondhu;
public class URLs {
    private static final String ROOT_URL = "http://10.10.247.57/krishokbondhu/Api.php?apicall=";
    public static final String URL_PAYMENT = ROOT_URL + "payment";
    public static final String URL_REGISTER = ROOT_URL + "signup";
    public static final String URL_LOGIN= ROOT_URL + "login";
    public static final String URL_PROFILE = ROOT_URL + "updateprofile";
    public static final String URL_FIELD_ADD = ROOT_URL + "field_add";
    public static final String URL_FIELD_UPDATE = ROOT_URL + "field_update";
    public static final String URL_FIELD_DELETE = "http://10.10.247.57/krishokbondhu/field_delete_api.php?field_no=";
    public static final String URL_FIELD_LIST = "http://10.10.247.57/krishokbondhu/field_api.php?field_owner=";
    public static final String URL_PRODUCT_LIST = "http://10.10.247.57/krishokbondhu/product_api.php";
    public static final String URL_NOTIFICATION = "http://10.10.247.57/krishokbondhu/notification_api.php?field_owner=";
    public static final String URL_HISTORY = "http://10.10.247.57/krishokbondhu/history_api.php?field_owner=";
}
